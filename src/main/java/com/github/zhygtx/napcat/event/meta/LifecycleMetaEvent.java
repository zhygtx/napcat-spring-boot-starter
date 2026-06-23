package com.github.zhygtx.napcat.event.meta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 生命周期元事件基类。
 * <p>
 * 对应 {@code meta_event_type=lifecycle}。
 * 用于通知框架生命周期变化，{@code sub_type} 为：
 * <ul>
 *   <li>{@code enable} — 启用</li>
 *   <li>{@code disable} — 禁用</li>
 *   <li>{@code connect} — 连接</li>
 * </ul>
 * <p>
 * 具体子类：
 * <ul>
 *   <li>{@link LifecycleConnectMetaEvent} — 连接事件</li>
 * </ul>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LifecycleMetaEvent extends MetaEvent {

    @JsonProperty("sub_type")
    private String subType;
}
