package com.github.zhygtx.napcat.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

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
}
