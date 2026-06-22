package com.github.zhygtx.napcat.event;

/**
 * Bot 生命周期事件监听器。
 * <p>
 * 用户实现此接口并注册为 Spring Bean，即可在 Bot 上线/离线时执行自定义逻辑。
 * 接口中的方法均为 default，用户可以仅覆写需要的方法。
 * <p>
 * 使用示例：
 * <pre>{@code
 * @Component
 * public class MyBotListener implements BotEventListener {
 *     @Override
 *     public void botOnline(Long botQQ) {
 *         log.info("Bot {} 上线了", botQQ);
 *     }
 * }
 * }</pre>
 */
public interface BotEventListener {

    /**
     * Bot 上线时调用。
     * <p>
     * 在 WebSocket 连接建立、会话注册到 {@code BotSessionRegistry} 后触发。
     *
     * @param botQQ 上线的 Bot QQ 号
     */
    default void botOnline(Long botQQ) {
        // 默认空实现，用户按需覆写
    }

    /**
     * Bot 离线时调用。
     * <p>
     * 在 WebSocket 连接关闭、会话从 {@code BotSessionRegistry} 移除后触发。
     *
     * @param botQQ 离线的 Bot QQ 号
     */
    default void botOffline(Long botQQ) {
        // 默认空实现，用户按需覆写
    }
}
