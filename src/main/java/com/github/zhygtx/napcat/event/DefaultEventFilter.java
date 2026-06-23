package com.github.zhygtx.napcat.event;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * 默认事件过滤器，放行所有事件。
 * <p>
 * 仅当用户未注册任何 {@link EventFilter} Bean 时才会生效，
 * 确保事件分发始终能正常工作。
 */
@Component
@ConditionalOnMissingBean(EventFilter.class)
public class DefaultEventFilter implements EventFilter {

    @Override
    public boolean filter(Long botQQ, BaseEvent event) {
        return true;
    }
}
