package com.github.zhygtx.napcat.event.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zhygtx.napcat.event.sender.FriendSender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 私聊消息事件。
 * <p>
 * 对应 {@code post_type=message, message_type=private}。
 * 根据 {@code sub_type} 区分好友消息、临时会话等。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrivateMessageEvent extends MessageEvent {

    /** 消息类型，固定为 "private" */
    @JsonProperty("message_type")
    private String messageType = "private";

    /** 子类型（friend / group / other） */
    @JsonProperty("sub_type")
    private String subType;

    /** 临时会话来源 ID（临时会话时有效） */
    @JsonProperty("target_id")
    private Long targetId;

    /** 临时会话来源类型 */
    @JsonProperty("temp_source")
    private Integer tempSource;

    /** 发送者信息 */
    @JsonProperty("sender")
    private FriendSender sender;
}
