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
 * 群聊消息发送事件（机器人自己发出的群聊消息）。
 * <p>
 * 对应 {@code post_type=message_sent, message_type=group}。
 * 根据 {@code sub_type} 进一步区分普通群消息发送等。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMessageSentEvent extends MessageSentEvent {

    /** 消息类型，固定为 "group" */
    @JsonProperty("message_type")
    private String messageType;

    /** 子类型（normal 等） */
    @JsonProperty("sub_type")
    private String subType;

    /** 群号 */
    @JsonProperty("group_id")
    private long groupId;

    /** 发送者信息 */
    @JsonProperty("sender")
    private Object sender;
}
