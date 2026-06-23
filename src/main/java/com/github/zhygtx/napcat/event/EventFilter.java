package com.github.zhygtx.napcat.event;

/**
 * 事件过滤器接口。
 * <p>
 * 用户实现此接口并注册为 Spring Bean，即可在事件分发之前进行拦截判断。
 * 支持注册多个过滤器形成过滤器链，所有过滤器必须全部返回 {@code true}，
 * 事件才会被分发（AND 逻辑）。
 * <p>
 * 过滤器链执行在事件解析之后、提交线程池之前，被拦截的事件不会进入线程池，
 * 节省线程资源。
 * <p>
 * 使用示例（注册多个过滤器）：
 * <pre>{@code
 * @Configuration
 * public class MyFilters {
 *     // 只处理群聊消息
 *     @Bean
 *     public EventFilter groupOnlyFilter() {
 *         return (botQQ, event) -> event instanceof GroupMessageEvent;
 *     }
 *
 *     // 黑名单过滤
 *     @Bean
 *     public EventFilter blacklistFilter() {
 *         return (botQQ, event) -> !blacklist.contains(event.getUserId());
 *     }
 * }
 * }</pre>
 */
public interface EventFilter {

    /**
     * 过滤事件。
     * <p>
     * 在事件分发前调用。返回 {@code true} 表示放行（继续后续过滤和分发），
     * 返回 {@code false} 表示拦截（事件将被丢弃，不会分发给任何监听器）。
     *
     * @param botQQ 收到事件的 Bot QQ 号
     * @param event 待分发的事件对象
     * @return true 放行，false 拦截
     */
    boolean filter(Long botQQ, BaseEvent event);
}
