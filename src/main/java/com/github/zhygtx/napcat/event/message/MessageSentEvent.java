package com.github.zhygtx.napcat.event.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 消息发送事件（机器人自己发出的消息）。
 * <p>
 * 对应 {@code post_type=message_sent}。
 * 根据 {@code message_type} 区分私聊发送 / 群聊发送。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageSentEvent extends MessageEvent {

    @JsonProperty("message_type")
    private String messageType;

    @JsonProperty("target_id")
    private long targetId;

    @JsonProperty("sender")
    private Object sender;
}
