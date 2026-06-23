package com.github.zhygtx.napcat.event.notice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 好友消息撤回通知。
 * <p>
 * 对应 {@code notice_type=friend_recall}。
 * 当好友撤回其发送的消息时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendRecallNoticeEvent extends NoticeEvent {

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("message_id")
    private long messageId;
}
