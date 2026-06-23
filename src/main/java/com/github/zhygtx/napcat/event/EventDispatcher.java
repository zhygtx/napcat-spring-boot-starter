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

    private void scanListeners() {
        Map<String, OneBotEventListener> beans = context.getBeansOfType(OneBotEventListener.class);
        listeners = new ArrayList<>(beans.values());
    }

    /**
     * 将事件分发给所有 OneBotEventListener Bean。
     * <p>
     * 使用 instanceof 链按继承层级分发，最具体的子类优先匹配，保证接口中
     * 既会调用父级方法（如 onGroupAdmin），也会调用子级方法（如 onGroupAdminSet）。
     */
    private void dispatchToOneBotEventListener(Long botQQ, BaseEvent event) {
        if (listeners.isEmpty()) return;

        for (OneBotEventListener listener : listeners) {
            try {
                // ===== 元事件 =====
                if (event instanceof LifecycleConnectMetaEvent) {
                    listener.onLifecycleConnect(botQQ, (LifecycleConnectMetaEvent) event);
                    listener.onLifecycle(botQQ, (LifecycleMetaEvent) event);
                } else if (event instanceof LifecycleMetaEvent) {
                    listener.onLifecycle(botQQ, (LifecycleMetaEvent) event);
                } else if (event instanceof HeartbeatMetaEvent) {
                    listener.onHeartbeat(botQQ, (HeartbeatMetaEvent) event);
                }

                // ===== 消息事件 =====
                else if (event instanceof PrivateFriendMessageEvent) {
                    listener.onPrivateFriendMessage(botQQ, (PrivateFriendMessageEvent) event);
                    listener.onPrivateMessage(botQQ, (PrivateMessageEvent) event);
                } else if (event instanceof PrivateGroupMessageEvent) {
                    listener.onPrivateGroupMessage(botQQ, (PrivateGroupMessageEvent) event);
                    listener.onPrivateMessage(botQQ, (PrivateMessageEvent) event);
                } else if (event instanceof PrivateMessageEvent) {
                    listener.onPrivateMessage(botQQ, (PrivateMessageEvent) event);
                } else if (event instanceof GroupNormalMessageEvent) {
                    listener.onGroupNormalMessage(botQQ, (GroupNormalMessageEvent) event);
                    listener.onGroupMessage(botQQ, (GroupMessageEvent) event);
                } else if (event instanceof GroupMessageEvent) {
                    listener.onGroupMessage(botQQ, (GroupMessageEvent) event);
                }

                // ===== 消息发送事件 =====
                else if (event instanceof PrivateFriendMessageSentEvent) {
                    listener.onPrivateFriendMessageSent(botQQ, (PrivateFriendMessageSentEvent) event);
                    listener.onPrivateMessageSent(botQQ, (PrivateMessageSentEvent) event);
                    listener.onMessageSent(botQQ, (MessageSentEvent) event);
                } else if (event instanceof PrivateGroupMessageSentEvent) {
                    listener.onPrivateGroupMessageSent(botQQ, (PrivateGroupMessageSentEvent) event);
                    listener.onPrivateMessageSent(botQQ, (PrivateMessageSentEvent) event);
                    listener.onMessageSent(botQQ, (MessageSentEvent) event);
                } else if (event instanceof PrivateMessageSentEvent) {
                    listener.onPrivateMessageSent(botQQ, (PrivateMessageSentEvent) event);
                    listener.onMessageSent(botQQ, (MessageSentEvent) event);
                } else if (event instanceof GroupNormalMessageSentEvent) {
                    listener.onGroupNormalMessageSent(botQQ, (GroupNormalMessageSentEvent) event);
                    listener.onGroupMessageSent(botQQ, (GroupMessageSentEvent) event);
                    listener.onMessageSent(botQQ, (MessageSentEvent) event);
                } else if (event instanceof GroupMessageSentEvent) {
                    listener.onGroupMessageSent(botQQ, (GroupMessageSentEvent) event);
                    listener.onMessageSent(botQQ, (MessageSentEvent) event);
                } else if (event instanceof MessageSentEvent) {
                    listener.onMessageSent(botQQ, (MessageSentEvent) event);
                }

                // ===== 通知事件 - 好友 =====
                else if (event instanceof FriendAddNoticeEvent) {
                    listener.onFriendAdd(botQQ, (FriendAddNoticeEvent) event);
                } else if (event instanceof FriendRecallNoticeEvent) {
                    listener.onFriendRecall(botQQ, (FriendRecallNoticeEvent) event);
                }

                // ===== 通知事件 - 群管理员 =====
                else if (event instanceof GroupAdminSetNoticeEvent) {
                    listener.onGroupAdminSet(botQQ, (GroupAdminSetNoticeEvent) event);
                    listener.onGroupAdmin(botQQ, (GroupAdminNoticeEvent) event);
                } else if (event instanceof GroupAdminUnsetNoticeEvent) {
                    listener.onGroupAdminUnset(botQQ, (GroupAdminUnsetNoticeEvent) event);
                    listener.onGroupAdmin(botQQ, (GroupAdminNoticeEvent) event);
                } else if (event instanceof GroupAdminNoticeEvent) {
                    listener.onGroupAdmin(botQQ, (GroupAdminNoticeEvent) event);
                }

                // ===== 通知事件 - 群禁言 =====
                else if (event instanceof GroupBanBanNoticeEvent) {
                    listener.onGroupBanBan(botQQ, (GroupBanBanNoticeEvent) event);
                    listener.onGroupBan(botQQ, (GroupBanNoticeEvent) event);
                } else if (event instanceof GroupBanLiftBanNoticeEvent) {
                    listener.onGroupBanLiftBan(botQQ, (GroupBanLiftBanNoticeEvent) event);
                    listener.onGroupBan(botQQ, (GroupBanNoticeEvent) event);
                } else if (event instanceof GroupBanNoticeEvent) {
                    listener.onGroupBan(botQQ, (GroupBanNoticeEvent) event);
                }

                // ===== 通知事件 - 群成员减少 =====
                else if (event instanceof GroupDecreaseKickMeNoticeEvent) {
                    listener.onGroupDecreaseKickMe(botQQ, (GroupDecreaseKickMeNoticeEvent) event);
                    listener.onGroupDecrease(botQQ, (GroupDecreaseNoticeEvent) event);
                } else if (event instanceof GroupDecreaseKickNoticeEvent) {
                    listener.onGroupDecreaseKick(botQQ, (GroupDecreaseKickNoticeEvent) event);
                    listener.onGroupDecrease(botQQ, (GroupDecreaseNoticeEvent) event);
                } else if (event instanceof GroupDecreaseLeaveNoticeEvent) {
                    listener.onGroupDecreaseLeave(botQQ, (GroupDecreaseLeaveNoticeEvent) event);
                    listener.onGroupDecrease(botQQ, (GroupDecreaseNoticeEvent) event);
                } else if (event instanceof GroupDecreaseNoticeEvent) {
                    listener.onGroupDecrease(botQQ, (GroupDecreaseNoticeEvent) event);
                }

                // ===== 通知事件 - 群成员增加 =====
                else if (event instanceof GroupIncreaseApproveNoticeEvent) {
                    listener.onGroupIncreaseApprove(botQQ, (GroupIncreaseApproveNoticeEvent) event);
                    listener.onGroupIncrease(botQQ, (GroupIncreaseNoticeEvent) event);
                } else if (event instanceof GroupIncreaseInviteNoticeEvent) {
                    listener.onGroupIncreaseInvite(botQQ, (GroupIncreaseInviteNoticeEvent) event);
                    listener.onGroupIncrease(botQQ, (GroupIncreaseNoticeEvent) event);
                } else if (event instanceof GroupIncreaseNoticeEvent) {
                    listener.onGroupIncrease(botQQ, (GroupIncreaseNoticeEvent) event);
                }

                // ===== 通知事件 - 其他群聊 =====
                else if (event instanceof GroupEssenceAddNoticeEvent) {
                    listener.onGroupEssenceAdd(botQQ, (GroupEssenceAddNoticeEvent) event);
                    listener.onGroupEssence(botQQ, (GroupEssenceNoticeEvent) event);
                } else if (event instanceof GroupEssenceNoticeEvent) {
                    listener.onGroupEssence(botQQ, (GroupEssenceNoticeEvent) event);
                } else if (event instanceof GroupRecallNoticeEvent) {
                    listener.onGroupRecall(botQQ, (GroupRecallNoticeEvent) event);
                } else if (event instanceof GroupUploadNoticeEvent) {
                    listener.onGroupUpload(botQQ, (GroupUploadNoticeEvent) event);
                } else if (event instanceof GroupCardNoticeEvent) {
                    listener.onGroupCard(botQQ, (GroupCardNoticeEvent) event);
                } else if (event instanceof GroupMsgEmojiLikeNoticeEvent) {
                    listener.onGroupMsgEmojiLike(botQQ, (GroupMsgEmojiLikeNoticeEvent) event);
                } else if (event instanceof TitleNoticeEvent) {
                    listener.onGroupTitle(botQQ, (TitleNoticeEvent) event);
                }

                // ===== 通知事件 - 其他 =====
                else if (event instanceof PokeNoticeEvent) {
                    listener.onPoke(botQQ, (PokeNoticeEvent) event);
                } else if (event instanceof InputStatusNoticeEvent) {
                    listener.onInputStatus(botQQ, (InputStatusNoticeEvent) event);
                } else if (event instanceof ProfileLikeNoticeEvent) {
                    listener.onProfileLike(botQQ, (ProfileLikeNoticeEvent) event);
                } else if (event instanceof BotOfflineNoticeEvent) {
                    listener.onBotOffline(botQQ, (BotOfflineNoticeEvent) event);
                }

                // ===== 请求事件 =====
                else if (event instanceof GroupAddRequestEvent) {
                    listener.onGroupAddRequest(botQQ, (GroupAddRequestEvent) event);
                    listener.onGroupRequest(botQQ, (GroupRequestEvent) event);
                } else if (event instanceof GroupInviteRequestEvent) {
                    listener.onGroupInviteRequest(botQQ, (GroupInviteRequestEvent) event);
                    listener.onGroupRequest(botQQ, (GroupRequestEvent) event);
                } else if (event instanceof GroupRequestEvent) {
                    listener.onGroupRequest(botQQ, (GroupRequestEvent) event);
                } else if (event instanceof FriendRequestEvent) {
                    listener.onFriendRequest(botQQ, (FriendRequestEvent) event);
                }

            } catch (Exception e) {
                log.error("OneBotEventListener 分发异常, listener: {}, event: {}",
                        listener.getClass().getSimpleName(), event.getClass().getSimpleName(), e);
            }
        }
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
