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

    @JsonProperty("message_id")
    private long messageId;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("message")
    private List<MessageSegment> message;

    @JsonProperty("raw_message")
    private String rawMessage;

    @JsonProperty("font")
    private long font;
}
