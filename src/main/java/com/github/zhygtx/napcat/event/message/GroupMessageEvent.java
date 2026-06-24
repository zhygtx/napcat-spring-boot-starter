package com.github.zhygtx.napcat.event.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zhygtx.napcat.event.sender.GroupSender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 群聊消息事件。
 * <p>
 * 对应 {@code post_type=message, message_type=group}。
 * 根据 {@code sub_type} 区分普通消息、匿名消息、通知消息。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMessageEvent extends MessageEvent {

    /** 消息类型，固定为 "group" */
    @JsonProperty("message_type")
    private String messageType = "group";

    /** 子类型（normal / anonymous / notice） */
    @JsonProperty("sub_type")
    private String subType;

    /** 群号 */
    @JsonProperty("group_id")
    private long groupId;

    /** 匿名信息（匿名消息时不为 null） */
    @JsonProperty("anonymous")
    private Object anonymous;

    /** 发送者信息 */
    @JsonProperty("sender")
    private GroupSender sender;
}
