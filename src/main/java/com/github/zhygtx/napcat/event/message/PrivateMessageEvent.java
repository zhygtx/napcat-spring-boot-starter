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

    @JsonProperty("message_type")
    private String messageType = "private";

    @JsonProperty("sub_type")
    private String subType;

    @JsonProperty("target_id")
    private Long targetId;

    @JsonProperty("temp_source")
    private Integer tempSource;

    @JsonProperty("sender")
    private FriendSender sender;
}
