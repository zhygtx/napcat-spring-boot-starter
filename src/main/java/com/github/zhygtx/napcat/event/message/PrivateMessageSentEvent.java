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
 * 私聊消息发送事件（机器人自己发出的私聊消息）。
 * <p>
 * 对应 {@code post_type=message_sent, message_type=private}。
 * 根据 {@code sub_type} 进一步区分好友私聊发送 / 群临时会话发送。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrivateMessageSentEvent extends MessageSentEvent {

    /** 消息类型，固定为 "private" */
    @JsonProperty("message_type")
    private String privateMessageType;

    /** 子类型（friend / group 等） */
    @JsonProperty("sub_type")
    private String subType;

    /** 发送者信息 */
    @JsonProperty("sender")
    private Object sender;
}
