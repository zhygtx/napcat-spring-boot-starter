package com.github.zhygtx.napcat.event.notice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * 群表情回应通知。
 * <p>
 * 对应 {@code notice_type=group_msg_emoji_like}。
 * 当群消息被添加表情回应时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMsgEmojiLikeNoticeEvent extends GroupNoticeEvent {

    @JsonProperty("message_id")
    private long messageId;

    @JsonProperty("likes")
    private List<Map<String, Object>> likes;
}
