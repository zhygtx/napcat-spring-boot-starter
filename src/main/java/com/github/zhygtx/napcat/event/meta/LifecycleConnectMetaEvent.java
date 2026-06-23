package com.github.zhygtx.napcat.event.meta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 生命周期连接元事件。
 * <p>
 * 对应 {@code meta_event_type=lifecycle, sub_type=connect}。
 * 当机器人建立连接时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LifecycleConnectMetaEvent extends LifecycleMetaEvent {
}
