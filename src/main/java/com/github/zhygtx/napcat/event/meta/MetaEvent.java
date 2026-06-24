package com.github.zhygtx.napcat.event.meta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zhygtx.napcat.event.BaseEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * OneBot v11 元事件基类。
 * <p>
 * 对应 {@code post_type=meta_event}。
 * 元事件是与协议相关的系统事件，分为心跳和生命周期两类。
 */
@Getter
@Setter
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MetaEvent extends BaseEvent {

    /** 元事件类型（heartbeat / lifecycle） */
    @JsonProperty("meta_event_type")
    private String metaEventType;
}
