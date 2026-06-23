package com.github.zhygtx.napcat.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注在方法上，表示该方法用于处理特定类型的 OneBot 事件。
 * <p>
 * 方法签名必须为 {@code void methodName(Long botQQ, XxxEvent event)}，
 * 其中第二个参数为具体的事件类型，EventDispatcher 会自动匹配并分发。
 * <p>
 * 使用示例：
 * <pre>{@code
 * @Component
 * public class MyHandlers {
 *     @OnEvent
 *     public void onGroupMsg(Long botQQ, GroupMessageEvent event) {
 *         log.info("[{}] 群 {}: {}", botQQ, event.getGroupId(), event.getRawMessage());
 *     }
 * }
 * }</pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnEvent {
}
