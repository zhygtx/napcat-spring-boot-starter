package com.github.zhygtx.napcat.session;

import com.github.zhygtx.napcat.event.BotEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bot 会话注册中心。
 * <p>
 * 以 BotQQ 为 key，管理所有在线的 Bot 会话。
 * 提供注册、移除、查询等基础操作，线程安全。
 * <p>
 * 工作流程：
 * <ol>
 *   <li>WebSocket 连接建立后，由 {@code NapCatWebSocketHandler} 调用 {@link #register(Bot)}</li>
 *   <li>WebSocket 连接关闭后，由 {@code NapCatWebSocketHandler} 调用 {@link #remove(Long)}</li>
 *   <li>其他组件可通过 {@link #getBot(Long)} 或 {@link #getAllBots()} 获取在线 Bot 信息</li>
 * </ol>
 */
@Component
public class BotSessionRegistry {

    private static final Logger log = LoggerFactory.getLogger(BotSessionRegistry.class);

    /** 以 BotQQ 为 key 的在线 Bot 映射表 */
    private final ConcurrentHashMap<Long, Bot> onlineBots = new ConcurrentHashMap<>();

    /** 用户注册的事件监听器列表（Spring 自动收集所有 BotEventListener Bean） */
    private final List<BotEventListener> listeners;

    public BotSessionRegistry(List<BotEventListener> listeners) {
        this.listeners = listeners;
    }

    /**
     * 注册一个 Bot 会话到在线列表。
     * <p>
     * 如果该 BotQQ 已有旧会话，会先关闭旧会话再替换。
     * 注册成功后触发 {@link BotEventListener#botOnline} 事件。
     * @param bot 要注册的 Bot 会话对象
     */
    public void register(Bot bot) {
        Long botQQ = bot.getBotQQ();
        Bot oldBot = onlineBots.put(botQQ, bot);
        if (oldBot != null) {
            log.warn("Bot [{}] 已有旧连接，将被新连接替换", botQQ);
            closeSessionSilently(oldBot);
        }
        log.info("Bot [{}] 已注册，当前在线数量: {}", botQQ, onlineBots.size());
        // 触发上线事件
        listeners.forEach(listener -> listener.botOnline(botQQ));
    }

    /**
     * 从在线列表中移除指定 BotQQ 的会话。
     * 移除成功后触发 {@link BotEventListener#botOffline} 事件。
     * @param botQQ 要移除的 Bot QQ 号
     * @return 被移除的 Bot 会话，如果不存在则返回 null
     */
    public Bot remove(Long botQQ) {
        Bot removed = onlineBots.remove(botQQ);
        if (removed != null) {
            log.info("Bot [{}] 已移除，当前在线数量: {}", botQQ, onlineBots.size());
            // 触发离线事件
            listeners.forEach(listener -> listener.botOffline(botQQ));
        }
        return removed;
    }

    /**
     * 获取指定 BotQQ 的在线会话。
     *
     * @param botQQ Bot QQ 号
     * @return Bot 会话，如果不在线则返回 null
     */
    public Bot getBot(Long botQQ) {
        return onlineBots.get(botQQ);
    }

    /**
     * 获取所有在线 Bot 会话。
     *
     * @return 所有在线 Bot 会话的不可变集合视图
     */
    public Collection<Bot> getAllBots() {
        return onlineBots.values();
    }

    /**
     * 获取当前在线 Bot 数量。
     *
     * @return 在线 Bot 数量
     */
    public int getOnlineCount() {
        return onlineBots.size();
    }

    /**
     * 判断指定 BotQQ 是否在线。
     *
     * @param botQQ Bot QQ 号
     * @return 如果在线则返回 true
     */
    public boolean isOnline(Long botQQ) {
        return onlineBots.containsKey(botQQ);
    }

    /**
     * 更新指定 Bot 的最后消息时间为当前时间。
     * <p>
     * 每次收到消息时调用，用于心跳超时检测作为"心跳"信号。
     *
     * @param botQQ Bot QQ 号
     */
    public void updateLastMessageTime(Long botQQ) {
        Bot bot = onlineBots.get(botQQ);
        if (bot != null) {
            bot.setLastMessageTime(Instant.now());
        }
    }

    /**
     * 静默关闭旧 Bot 的 WebSocket 会话。
     */
    private void closeSessionSilently(Bot bot) {
        try {
            if (bot.getSession() != null && bot.getSession().isOpen()) {
                bot.getSession().close();
            }
        } catch (Exception e) {
            log.warn("关闭 Bot [{}] 旧会话时出现异常: {}", bot.getBotQQ(), e.getMessage());
        }
    }
}
