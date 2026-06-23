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
 * 群精华消息通知基类。
 * <p>
 * 对应 {@code notice_type=essence}。
 * {@code sub_type} 为 {@code add}（添加精华）或 {@code delete}（删除精华）。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupEssenceNoticeEvent extends GroupNoticeEvent {

    @JsonProperty("sub_type")
    private String subType;

    @JsonProperty("message_id")
    private long messageId;

    @JsonProperty("sender_id")
    private long senderId;

    @JsonProperty("operator_id")
    private long operatorId;
}
