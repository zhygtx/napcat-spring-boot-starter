package com.github.zhygtx.napcat.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * OneBot v11 事件基类。
 * <p>
 * 所有事件都包含以下基础字段：
 * <ul>
 *   <li>time — 事件发生的时间戳（秒）</li>
 *   <li>selfId — 收到事件的机器人 QQ 号</li>
 *   <li>postType — 事件类型（message / message_sent / notice / request / meta_event）</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseEvent {

    /** 事件发生的时间戳（秒） */
    @JsonProperty("time")
    private long time;

    /** 收到事件的机器人 QQ 号 */
    @JsonProperty("self_id")
    private long selfId;

    /** 事件类型（message / message_sent / notice / request / meta_event） */
    @JsonProperty("post_type")
    private String postType;

    /** 当前事件会触发的回调类型列表 */
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Class<? extends BaseEvent>> triggeredTypes;
}
