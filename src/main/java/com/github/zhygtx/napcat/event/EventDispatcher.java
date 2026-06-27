package com.github.zhygtx.napcat.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.github.zhygtx.napcat.config.NapCatProperties;
import com.github.zhygtx.napcat.event.message.*;
import com.github.zhygtx.napcat.event.meta.HeartbeatMetaEvent;
import com.github.zhygtx.napcat.util.EventLogger;
import com.github.zhygtx.napcat.event.meta.LifecycleConnectMetaEvent;
import com.github.zhygtx.napcat.event.meta.LifecycleMetaEvent;
import com.github.zhygtx.napcat.event.notice.*;
import com.github.zhygtx.napcat.event.request.FriendRequestEvent;
import com.github.zhygtx.napcat.event.request.GroupAddRequestEvent;
import com.github.zhygtx.napcat.event.request.GroupInviteRequestEvent;
import com.github.zhygtx.napcat.event.request.GroupRequestEvent;
import com.github.zhygtx.napcat.session.Bot;
import com.github.zhygtx.napcat.session.BotSessionRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 事件分发器。
 * <p>
 * 核心职责：
 * <ol>
 *   <li>接收原始 JSON 字符串</li>
 *   <li>根据 {@code post_type} 及二级字段解析事件类型</li>
 *   <li>反序列化为具体的事件 Java 对象</li>
 *   <li>分发给 {@link OneBotEventListener} 的实现 Bean</li>
 *   <li>分发给标注了 {@link OnEvent} 注解的方法</li>
 * </ol>
 * <p>
 * 三级分发 Key 机制：
 * <pre>
 *   先尝试 exactKey（post_type:detail_type:sub_type）
 *   匹配失败则回退 parentKey（post_type:detail_type）
 * </pre>
 */
@Component
public class EventDispatcher {

    private static final Logger log = LoggerFactory.getLogger(EventDispatcher.class);

    /** 用于解析 JSON 的 ObjectMapper（使用 snake_case 策略） */
    private final ObjectMapper mapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    private final ApplicationContext context;
    private final EventExecutor eventExecutor;
    private final NapCatProperties properties;
    private final EventLogger eventLogger;
    private final BotSessionRegistry botRegistry;

    /** 已注册的 OneBotEventListener Bean 列表 */
    private List<OneBotEventListener> listeners;

    /** 事件过滤器链，按 Spring 注册顺序执行，全部通过才放行 */
    private final List<EventFilter> filters;

    /** {@code dispatchKey → 事件解析器} 映射表 */
    private final Map<String, Function<JsonNode, BaseEvent>> resolverMap = new HashMap<>();

    /** {@code 事件Class → @OnEvent 方法列表} 映射表 */
    private final Map<Class<?>, List<OnEventHandler>> onEventHandlers = new HashMap<>();

    /** 心跳限流追踪：botQQ → 上次心跳通过的时间戳（毫秒） */
    private final ConcurrentHashMap<Long, Long> heartbeatPassedMap = new ConcurrentHashMap<>();

    /** {@code @OnEvent} 注解方法是否已扫描完成 */
    private volatile boolean onEventScanned = false;

    public EventDispatcher(ApplicationContext context, EventExecutor eventExecutor,
                           NapCatProperties properties, EventLogger eventLogger,
                           List<EventFilter> filters, BotSessionRegistry botRegistry) {
        this.context = context;
        this.eventExecutor = eventExecutor;
        this.properties = properties;
        this.eventLogger = eventLogger;
        this.filters = filters;
        this.botRegistry = botRegistry;
    }

    /**
     * 初始化：构建解析器映射、扫描监听器。
     */
    @PostConstruct
    public void init() {
        buildResolvers();
        scanListeners();
        log.info("EventDispatcher 初始化完成，已注册 {} 个事件解析器, {} 个监听器, {} 个过滤器",
                resolverMap.size(), listeners.size(), filters.size());
    }

    // ==================== 公开方法 ====================

    /**
     * 分发一条事件（JsonNode 入口）。
     * <p>
     * 在 WebSocket I/O 线程上被调用，执行心跳限流检测，
     * 然后将日志输出 + 事件处理逻辑提交到 {@link EventExecutor} 线程池异步执行，
     * 避免 I/O 线程被用户 Handler 阻塞。
     *
     * @param botQQ   Bot QQ 号
     * @param sessionId WebSocket 会话 ID（用于日志）
     * @param json    已解析的 JsonNode
     */
    public void dispatch(Long botQQ, String sessionId, JsonNode json) {
        ensureOnEventScanned();

        // 构建三级和二级 Key
        String exactKey = buildExactKey(json);
        String parentKey = buildParentKey(json);
        if (exactKey == null && parentKey == null) {
            log.warn("无法识别的事件类型");
            return;
        }

        // 先尝试 exactKey，再回退 parentKey
        BaseEvent event = resolve(json, exactKey);
        if (event == null && parentKey != null && !parentKey.equals(exactKey)) {
            event = resolve(json, parentKey);
        }
        if (event == null) {
            log.warn("事件反序列化失败, exactKey: {}, parentKey: {}", exactKey, parentKey);
            return;
        }

        // 心跳限流：丢弃 < minHeartbeatInterval 的高频心跳
        if (event instanceof HeartbeatMetaEvent) {
            if (botQQ != null && !shouldPassHeartbeat(botQQ)) {
                log.trace("心跳限流: bot [{}] 心跳被丢弃", botQQ);
                return;
            }
        }

        // 过滤器链：任意 Filter 返回 false 则丢弃事件
        if (!passFilterChain(botQQ, event)) {
            log.debug("事件被过滤器拦截, bot: {}, type: {}", botQQ, event.getClass().getSimpleName());
            return;
        }

        // ===== 账号离线/恢复检测（在提交线程池之前同步执行） =====
        // 触发点 1：账号离线事件 → 标记离线
        if (event instanceof BotOfflineNoticeEvent && botQQ != null) {
            botRegistry.markAccountOffline(botQQ);
        }

        // 触发点 2：心跳在线状态转变检测
        if (event instanceof HeartbeatMetaEvent hb && botQQ != null) {
            Bot bot = botRegistry.getBot(botQQ);
            if (bot != null) {
                if (hb.isOnline() && !bot.isAccountOnline()) {
                    // false → true：账号恢复在线
                    botRegistry.markAccountOnline(botQQ);
                } else if (!hb.isOnline() && bot.isAccountOnline()) {
                    // true → false：账号变为离线
                    botRegistry.markAccountOffline(botQQ);
                }
            }
        }

        // 提交到线程池异步执行：日志 + 事件分发
        BaseEvent finalEvent = event;
        eventExecutor.submit(() -> {
            eventLogger.logEvent(sessionId, json);
            dispatchToOneBotEventListener(botQQ, finalEvent);
            dispatchToOnEventHandlers(botQQ, finalEvent);
        });
    }

    /**
     * 心跳限流检测。
     * <p>
     * 如果该 botQQ 上一次心跳通过的间隔小于配置的 {@code heartbeatMinInterval}，
     * 则返回 false 丢弃本次心跳，防止高频心跳洪水。
     *
     * @param botQQ Bot QQ 号
     * @return true 表示放行，false 表示丢弃
     */
    private boolean shouldPassHeartbeat(Long botQQ) {
        long now = System.currentTimeMillis();
        long minIntervalMs = properties.getWs().getHeartbeatMinInterval() * 1000L;
        Long last = heartbeatPassedMap.get(botQQ);
        if (last != null && (now - last) < minIntervalMs) {
            return false;
        }
        heartbeatPassedMap.put(botQQ, now);
        return true;
    }

    /**
     * 执行过滤器链，全部通过才返回 true（AND 逻辑）。
     *
     * @param botQQ Bot QQ 号
     * @param event 待过滤的事件
     * @return true 放行，false 拦截
     */
    private boolean passFilterChain(Long botQQ, BaseEvent event) {
        if (filters.isEmpty()) return true;
        for (EventFilter filter : filters) {
            try {
                if (!filter.filter(botQQ, event)) {
                    return false;
                }
            } catch (Exception e) {
                log.error("EventFilter 执行异常, filter: {}", filter.getClass().getSimpleName(), e);
            }
        }
        return true;
    }

    /**
     * 确保 {@code @OnEvent} 注解方法已被扫描。
     * <p>
     * 懒加载策略：不在 {@code @PostConstruct} 中执行（避免循环依赖），
     */
    private void ensureOnEventScanned() {
        if (onEventScanned) return;
        synchronized (this) {
            if (onEventScanned) return;
            scanOnEventMethods();
            onEventScanned = true;
            log.info("@OnEvent 扫描完成，共 {} 个处理器",
                    onEventHandlers.values().stream().mapToInt(List::size).sum());
        }
    }

    // ==================== 构建 Key ====================

    /**
     * 构建精确 Key（三级）：{@code post_type:detail_type:sub_type}。
     * <p>
     * 示例：message:private:friend, notice:group_ban:ban
     */
    private String buildExactKey(JsonNode json) {
        String base = buildParentKey(json);
        if (base == null) return null;
        if (json.has("sub_type")) {
            return base + ":" + json.get("sub_type").asText();
        }
        return base;
    }

    /**
     * 构建父级 Key（二级）：{@code post_type:detail_type}。
     * <p>
     * 示例：message:private, notice:group_ban
     */
    private String buildParentKey(JsonNode json) {
        if (!json.has("post_type")) return null;
        String postType = json.get("post_type").asText();

        switch (postType) {
            case "message":
                if (!json.has("message_type")) return null;
                return "message:" + json.get("message_type").asText();
            case "message_sent":
                if (json.has("message_type")) {
                    return "message_sent:" + json.get("message_type").asText();
                }
                return "message_sent";
            case "notice": {
                if (!json.has("notice_type")) return null;
                String noticeType = json.get("notice_type").asText();
                if ("notify".equals(noticeType) && json.has("sub_type")) {
                    return "notice:notify:" + json.get("sub_type").asText();
                }
                return "notice:" + noticeType;
            }
            case "request":
                if (!json.has("request_type")) return null;
                return "request:" + json.get("request_type").asText();
            case "meta_event":
                if (!json.has("meta_event_type")) return null;
                return "meta_event:" + json.get("meta_event_type").asText();
            default:
                return null;
        }
    }

    // ==================== 解析器构建 ====================

    private <T extends BaseEvent> void register(String key, Class<T> eventClass) {
        resolverMap.put(key, json -> {
            try {
                return mapper.treeToValue(json, eventClass);
            } catch (Exception e) {
                log.warn("事件反序列化失败, class: {}, key: {}", eventClass.getSimpleName(), key);
                return null;
            }
        });
    }

    private BaseEvent resolve(JsonNode json, String key) {
        Function<JsonNode, BaseEvent> resolver = resolverMap.get(key);
        if (resolver == null) return null;
        return resolver.apply(json);
    }

    private void buildResolvers() {
        // ---- 元事件 ----
        register("meta_event:lifecycle", LifecycleMetaEvent.class);
        register("meta_event:lifecycle:connect", LifecycleConnectMetaEvent.class);
        register("meta_event:heartbeat", HeartbeatMetaEvent.class);

        // ---- 消息事件 ----
        register("message:private", PrivateMessageEvent.class);
        register("message:private:friend", PrivateFriendMessageEvent.class);
        register("message:private:group", PrivateGroupMessageEvent.class);
        register("message:group", GroupMessageEvent.class);
        register("message:group:normal", GroupNormalMessageEvent.class);

        // ---- 消息发送事件 ----
        register("message_sent", MessageSentEvent.class);
        register("message_sent:private", PrivateMessageSentEvent.class);
        register("message_sent:private:friend", PrivateFriendMessageSentEvent.class);
        register("message_sent:private:group", PrivateGroupMessageSentEvent.class);
        register("message_sent:group", GroupMessageSentEvent.class);
        register("message_sent:group:normal", GroupNormalMessageSentEvent.class);

        // ---- 通知事件 - 好友 ----
        register("notice:friend_add", FriendAddNoticeEvent.class);
        register("notice:friend_recall", FriendRecallNoticeEvent.class);

        // ---- 通知事件 - 群管理员 ----
        register("notice:group_admin", GroupAdminNoticeEvent.class);
        register("notice:group_admin:set", GroupAdminSetNoticeEvent.class);
        register("notice:group_admin:unset", GroupAdminUnsetNoticeEvent.class);

        // ---- 通知事件 - 群禁言 ----
        register("notice:group_ban", GroupBanNoticeEvent.class);
        register("notice:group_ban:ban", GroupBanBanNoticeEvent.class);
        register("notice:group_ban:lift_ban", GroupBanLiftBanNoticeEvent.class);

        // ---- 通知事件 - 群成员减少 ----
        register("notice:group_decrease", GroupDecreaseNoticeEvent.class);
        register("notice:group_decrease:leave", GroupDecreaseLeaveNoticeEvent.class);
        register("notice:group_decrease:kick", GroupDecreaseKickNoticeEvent.class);
        register("notice:group_decrease:kick_me", GroupDecreaseKickMeNoticeEvent.class);

        // ---- 通知事件 - 群成员增加 ----
        register("notice:group_increase", GroupIncreaseNoticeEvent.class);
        register("notice:group_increase:approve", GroupIncreaseApproveNoticeEvent.class);
        register("notice:group_increase:invite", GroupIncreaseInviteNoticeEvent.class);

        // ---- 通知事件 - 其他群聊 ----
        register("notice:group_recall", GroupRecallNoticeEvent.class);
        register("notice:group_upload", GroupUploadNoticeEvent.class);
        register("notice:group_card", GroupCardNoticeEvent.class);
        register("notice:essence", GroupEssenceNoticeEvent.class);
        register("notice:essence:add", GroupEssenceAddNoticeEvent.class);
        register("notice:group_msg_emoji_like", GroupMsgEmojiLikeNoticeEvent.class);
        register("notice:notify:title", TitleNoticeEvent.class);

        // ---- 通知事件 - 其他 ----
        register("notice:notify:poke", PokeNoticeEvent.class);
        register("notice:notify:input_status", InputStatusNoticeEvent.class);
        register("notice:notify:profile_like", ProfileLikeNoticeEvent.class);
        register("notice:bot_offline", BotOfflineNoticeEvent.class);

        // ---- 请求事件 ----
        register("request:friend", FriendRequestEvent.class);
        register("request:group", GroupRequestEvent.class);
        register("request:group:add", GroupAddRequestEvent.class);
        register("request:group:invite", GroupInviteRequestEvent.class);
    }

    // ==================== OneBotEventListener 分发 ====================

    /**
     * 事件处理器，负责将特定类型的事件分发给监听器。
     */
    @FunctionalInterface
    private interface EventHandler {
        void handle(OneBotEventListener listener, Long botQQ, BaseEvent event);
    }

    /**
     * 事件类型 → 处理器注册表。
     * 通过类层级向上查找（从具体子类开始），保证继承语义：
     * 子类事件同时触发对应父类回调。
     */
    private static final Map<Class<? extends BaseEvent>, EventHandler> EVENT_HANDLERS = new HashMap<>();

    static {
        // ===== 元事件 =====
        register(LifecycleConnectMetaEvent.class, (l, qq, e) -> {
            LifecycleConnectMetaEvent ev = (LifecycleConnectMetaEvent) e;
            l.onLifecycleConnect(qq, ev);
            l.onLifecycle(qq, ev);
        });
        register(LifecycleMetaEvent.class, (l, qq, e) ->
                l.onLifecycle(qq, (LifecycleMetaEvent) e));
        register(HeartbeatMetaEvent.class, (l, qq, e) ->
                l.onHeartbeat(qq, (HeartbeatMetaEvent) e));

        // ===== 消息事件 =====
        register(PrivateFriendMessageEvent.class, (l, qq, e) -> {
            PrivateFriendMessageEvent ev = (PrivateFriendMessageEvent) e;
            l.onPrivateFriendMessage(qq, ev);
            l.onPrivateMessage(qq, ev);
        });
        register(PrivateGroupMessageEvent.class, (l, qq, e) -> {
            PrivateGroupMessageEvent ev = (PrivateGroupMessageEvent) e;
            l.onPrivateGroupMessage(qq, ev);
            l.onPrivateMessage(qq, ev);
        });
        register(PrivateMessageEvent.class, (l, qq, e) ->
                l.onPrivateMessage(qq, (PrivateMessageEvent) e));
        register(GroupNormalMessageEvent.class, (l, qq, e) -> {
            GroupNormalMessageEvent ev = (GroupNormalMessageEvent) e;
            l.onGroupNormalMessage(qq, ev);
            l.onGroupMessage(qq, ev);
        });
        register(GroupMessageEvent.class, (l, qq, e) ->
                l.onGroupMessage(qq, (GroupMessageEvent) e));

        // ===== 消息发送事件 =====
        register(PrivateFriendMessageSentEvent.class, (l, qq, e) -> {
            PrivateFriendMessageSentEvent ev = (PrivateFriendMessageSentEvent) e;
            l.onPrivateFriendMessageSent(qq, ev);
            l.onPrivateMessageSent(qq, ev);
            l.onMessageSent(qq, ev);
        });
        register(PrivateGroupMessageSentEvent.class, (l, qq, e) -> {
            PrivateGroupMessageSentEvent ev = (PrivateGroupMessageSentEvent) e;
            l.onPrivateGroupMessageSent(qq, ev);
            l.onPrivateMessageSent(qq, ev);
            l.onMessageSent(qq, ev);
        });
        register(PrivateMessageSentEvent.class, (l, qq, e) -> {
            PrivateMessageSentEvent ev = (PrivateMessageSentEvent) e;
            l.onPrivateMessageSent(qq, ev);
            l.onMessageSent(qq, ev);
        });
        register(GroupNormalMessageSentEvent.class, (l, qq, e) -> {
            GroupNormalMessageSentEvent ev = (GroupNormalMessageSentEvent) e;
            l.onGroupNormalMessageSent(qq, ev);
            l.onGroupMessageSent(qq, ev);
            l.onMessageSent(qq, ev);
        });
        register(GroupMessageSentEvent.class, (l, qq, e) -> {
            GroupMessageSentEvent ev = (GroupMessageSentEvent) e;
            l.onGroupMessageSent(qq, ev);
            l.onMessageSent(qq, ev);
        });
        register(MessageSentEvent.class, (l, qq, e) ->
                l.onMessageSent(qq, (MessageSentEvent) e));

        // ===== 通知事件 - 好友 =====
        register(FriendAddNoticeEvent.class, (l, qq, e) ->
                l.onFriendAdd(qq, (FriendAddNoticeEvent) e));
        register(FriendRecallNoticeEvent.class, (l, qq, e) ->
                l.onFriendRecall(qq, (FriendRecallNoticeEvent) e));

        // ===== 通知事件 - 群管理员 =====
        register(GroupAdminSetNoticeEvent.class, (l, qq, e) -> {
            GroupAdminSetNoticeEvent ev = (GroupAdminSetNoticeEvent) e;
            l.onGroupAdminSet(qq, ev);
            l.onGroupAdmin(qq, ev);
        });
        register(GroupAdminUnsetNoticeEvent.class, (l, qq, e) -> {
            GroupAdminUnsetNoticeEvent ev = (GroupAdminUnsetNoticeEvent) e;
            l.onGroupAdminUnset(qq, ev);
            l.onGroupAdmin(qq, ev);
        });
        register(GroupAdminNoticeEvent.class, (l, qq, e) ->
                l.onGroupAdmin(qq, (GroupAdminNoticeEvent) e));

        // ===== 通知事件 - 群禁言 =====
        register(GroupBanBanNoticeEvent.class, (l, qq, e) -> {
            GroupBanBanNoticeEvent ev = (GroupBanBanNoticeEvent) e;
            l.onGroupBanBan(qq, ev);
            l.onGroupBan(qq, ev);
        });
        register(GroupBanLiftBanNoticeEvent.class, (l, qq, e) -> {
            GroupBanLiftBanNoticeEvent ev = (GroupBanLiftBanNoticeEvent) e;
            l.onGroupBanLiftBan(qq, ev);
            l.onGroupBan(qq, ev);
        });
        register(GroupBanNoticeEvent.class, (l, qq, e) ->
                l.onGroupBan(qq, (GroupBanNoticeEvent) e));

        // ===== 通知事件 - 群成员减少 =====
        register(GroupDecreaseKickMeNoticeEvent.class, (l, qq, e) -> {
            GroupDecreaseKickMeNoticeEvent ev = (GroupDecreaseKickMeNoticeEvent) e;
            l.onGroupDecreaseKickMe(qq, ev);
            l.onGroupDecrease(qq, ev);
        });
        register(GroupDecreaseKickNoticeEvent.class, (l, qq, e) -> {
            GroupDecreaseKickNoticeEvent ev = (GroupDecreaseKickNoticeEvent) e;
            l.onGroupDecreaseKick(qq, ev);
            l.onGroupDecrease(qq, ev);
        });
        register(GroupDecreaseLeaveNoticeEvent.class, (l, qq, e) -> {
            GroupDecreaseLeaveNoticeEvent ev = (GroupDecreaseLeaveNoticeEvent) e;
            l.onGroupDecreaseLeave(qq, ev);
            l.onGroupDecrease(qq, ev);
        });
        register(GroupDecreaseNoticeEvent.class, (l, qq, e) ->
                l.onGroupDecrease(qq, (GroupDecreaseNoticeEvent) e));

        // ===== 通知事件 - 群成员增加 =====
        register(GroupIncreaseApproveNoticeEvent.class, (l, qq, e) -> {
            GroupIncreaseApproveNoticeEvent ev = (GroupIncreaseApproveNoticeEvent) e;
            l.onGroupIncreaseApprove(qq, ev);
            l.onGroupIncrease(qq, ev);
        });
        register(GroupIncreaseInviteNoticeEvent.class, (l, qq, e) -> {
            GroupIncreaseInviteNoticeEvent ev = (GroupIncreaseInviteNoticeEvent) e;
            l.onGroupIncreaseInvite(qq, ev);
            l.onGroupIncrease(qq, ev);
        });
        register(GroupIncreaseNoticeEvent.class, (l, qq, e) ->
                l.onGroupIncrease(qq, (GroupIncreaseNoticeEvent) e));

        // ===== 通知事件 - 其他群聊 =====
        register(GroupEssenceAddNoticeEvent.class, (l, qq, e) -> {
            GroupEssenceAddNoticeEvent ev = (GroupEssenceAddNoticeEvent) e;
            l.onGroupEssenceAdd(qq, ev);
            l.onGroupEssence(qq, ev);
        });
        register(GroupEssenceNoticeEvent.class, (l, qq, e) ->
                l.onGroupEssence(qq, (GroupEssenceNoticeEvent) e));
        register(GroupRecallNoticeEvent.class, (l, qq, e) ->
                l.onGroupRecall(qq, (GroupRecallNoticeEvent) e));
        register(GroupUploadNoticeEvent.class, (l, qq, e) ->
                l.onGroupUpload(qq, (GroupUploadNoticeEvent) e));
        register(GroupCardNoticeEvent.class, (l, qq, e) ->
                l.onGroupCard(qq, (GroupCardNoticeEvent) e));
        register(GroupMsgEmojiLikeNoticeEvent.class, (l, qq, e) ->
                l.onGroupMsgEmojiLike(qq, (GroupMsgEmojiLikeNoticeEvent) e));
        register(TitleNoticeEvent.class, (l, qq, e) ->
                l.onGroupTitle(qq, (TitleNoticeEvent) e));

        // ===== 通知事件 - 其他 =====
        register(PokeNoticeEvent.class, (l, qq, e) ->
                l.onPoke(qq, (PokeNoticeEvent) e));
        register(InputStatusNoticeEvent.class, (l, qq, e) ->
                l.onInputStatus(qq, (InputStatusNoticeEvent) e));
        register(ProfileLikeNoticeEvent.class, (l, qq, e) ->
                l.onProfileLike(qq, (ProfileLikeNoticeEvent) e));
        register(BotOfflineNoticeEvent.class, (l, qq, e) ->
                l.onBotOffline(qq, (BotOfflineNoticeEvent) e));

        // ===== 请求事件 =====
        register(GroupAddRequestEvent.class, (l, qq, e) -> {
            GroupAddRequestEvent ev = (GroupAddRequestEvent) e;
            l.onGroupAddRequest(qq, ev);
            l.onGroupRequest(qq, ev);
        });
        register(GroupInviteRequestEvent.class, (l, qq, e) -> {
            GroupInviteRequestEvent ev = (GroupInviteRequestEvent) e;
            l.onGroupInviteRequest(qq, ev);
            l.onGroupRequest(qq, ev);
        });
        register(GroupRequestEvent.class, (l, qq, e) ->
                l.onGroupRequest(qq, (GroupRequestEvent) e));
        register(FriendRequestEvent.class, (l, qq, e) ->
                l.onFriendRequest(qq, (FriendRequestEvent) e));
    }

    private static <T extends BaseEvent> void register(Class<T> type, EventHandler handler) {
        EVENT_HANDLERS.put(type, handler);
    }

    private void scanListeners() {
        Map<String, OneBotEventListener> beans = context.getBeansOfType(OneBotEventListener.class);
        listeners = new ArrayList<>(beans.values());
    }

    /**
     * 将事件分发给所有 OneBotEventListener Bean。
     * <p>
     * 通过注册表 + 类层级查找替代长 instanceof 链，
     * 自动匹配最具体的事件处理器，保证继承层级语义
     * （如 onGroupAdminSet 触发时也会调用 onGroupAdmin）。
     */
    private void dispatchToOneBotEventListener(Long botQQ, BaseEvent event) {
        if (listeners.isEmpty()) return;

        EventHandler handler = findEventHandler(event.getClass());
        if (handler == null) {
            log.warn("未注册的事件类型: {}", event.getClass().getSimpleName());
            // 即使没有特定处理器，仍然触发 onAnyEvent
            for (OneBotEventListener listener : listeners) {
                invokeOnAnyEventSafely(listener, botQQ, event);
            }
            return;
        }

        for (OneBotEventListener listener : listeners) {
            try {
                handler.handle(listener, botQQ, event);
            } catch (Exception e) {
                log.error("OneBotEventListener 分发异常, listener: {}, event: {}",
                        listener.getClass().getSimpleName(), event.getClass().getSimpleName(), e);
            }
            invokeOnAnyEventSafely(listener, botQQ, event);
        }
    }

    /**
     * 安全调用 {@link OneBotEventListener#onAnyEvent}，异常不影响后续监听器。
     * <p>
     * 心跳事件直接跳过，避免调试日志被高频心跳淹没。
     */
    private void invokeOnAnyEventSafely(OneBotEventListener listener, Long botQQ, BaseEvent event) {
        if (event instanceof HeartbeatMetaEvent) {
            return;
        }
        try {
            listener.onAnyEvent(botQQ, event);
        } catch (Exception e) {
            log.error("OneBotEventListener.onAnyEvent 异常, listener: {}, event: {}",
                    listener.getClass().getSimpleName(), event.getClass().getSimpleName(), e);
        }
    }

    /**
     * 沿类层级向上查找最匹配的事件处理器。
     * 从具体事件类开始逐级遍历父类，找到注册的处理器即返回。
     */
    @SuppressWarnings("unchecked")
    private EventHandler findEventHandler(Class<? extends BaseEvent> eventType) {
        Class<?> clazz = eventType;
        while (clazz != null) {
            EventHandler handler = EVENT_HANDLERS.get(clazz);
            if (handler != null) return handler;
            if (clazz == BaseEvent.class) break;
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    // ==================== @OnEvent 注解扫描与分发 ====================

    /**
     * 封装一个 {@code @OnEvent} 注解方法的调用。
     */
    private record OnEventHandler(Object bean, Method method) {

        void invoke(Long botQQ, BaseEvent event) {
            try {
                method.invoke(bean, botQQ, event);
            } catch (Exception e) {
                log.error("@OnEvent 方法调用异常, bean: {}, method: {}",
                        bean.getClass().getSimpleName(), method.getName(), e);
            }
        }
    }

    private void scanOnEventMethods() {
        String[] beanNames = context.getBeanDefinitionNames();
        for (String name : beanNames) {
            Object bean = context.getBean(name);
            Class<?> beanClass = ClassUtils.getUserClass(bean);
            collectOnEventMethods(bean, beanClass);
        }
    }

    /**
     * 递归收集指定类及其父类/接口中的 {@code @OnEvent} 方法。
     */
    private void collectOnEventMethods(Object bean, Class<?> clazz) {
        if (clazz == null || clazz == Object.class) return;

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(OnEvent.class)) {
                Class<?>[] paramTypes = method.getParameterTypes();
                if (paramTypes.length == 2
                        && paramTypes[0] == Long.class
                        && BaseEvent.class.isAssignableFrom(paramTypes[1])) {
                    method.setAccessible(true);
                    onEventHandlers
                            .computeIfAbsent(paramTypes[1], k -> new ArrayList<>())
                            .add(new OnEventHandler(bean, method));
                } else {
                    log.warn("@OnEvent 方法签名错误: {}.{}, 需要 void method(Long botQQ, XxxEvent event)",
                            clazz.getSimpleName(), method.getName());
                }
            }
        }

        // 递归扫描父类和接口
        collectOnEventMethods(bean, clazz.getSuperclass());
        for (Class<?> face : clazz.getInterfaces()) {
            collectOnEventMethods(bean, face);
        }
    }

    private void dispatchToOnEventHandlers(Long botQQ, BaseEvent event) {
        List<OnEventHandler> handlers = onEventHandlers.get(event.getClass());
        if (handlers == null || handlers.isEmpty()) return;

        for (OnEventHandler handler : handlers) {
            handler.invoke(botQQ, event);
        }
    }
}
