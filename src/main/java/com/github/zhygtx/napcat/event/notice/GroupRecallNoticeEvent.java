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
 * 群消息撤回通知。
 * <p>
 * 对应 {@code notice_type=group_recall}。
 * 当群内的消息被撤回时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupRecallNoticeEvent extends GroupNoticeEvent {

    /** 操作者 QQ 号（撤回消息的人，可能是发送者本人或管理员） */
    @JsonProperty("operator_id")
    private long operatorId;

    /** 被撤回的消息 ID */
    @JsonProperty("message_id")
    private long messageId;
}
