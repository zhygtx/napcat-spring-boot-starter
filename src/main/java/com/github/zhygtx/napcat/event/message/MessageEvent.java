package com.github.zhygtx.napcat.event.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zhygtx.napcat.event.BaseEvent;
import com.github.zhygtx.napcat.event.MessageSegment;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * OneBot v11 消息事件基类。
 * <p>
 * 所有消息事件（私聊、群聊、发送消息）的共同字段。
 */
@Getter
@Setter
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MessageEvent extends BaseEvent {

    /** 消息 ID */
    @JsonProperty("message_id")
    private long messageId;

    /** 发送者 QQ 号 */
    @JsonProperty("user_id")
    private long userId;

    /** 消息内容（消息段列表） */
    @JsonProperty("message")
    private List<MessageSegment> message;

    /** CQ 码格式的原始消息文本 */
    @JsonProperty("raw_message")
    private String rawMessage;

    /** 字体 ID */
    @JsonProperty("font")
    private long font;
}
