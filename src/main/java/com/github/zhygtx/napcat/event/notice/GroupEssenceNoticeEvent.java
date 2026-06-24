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

    /** 子类型（add / delete） */
    @JsonProperty("sub_type")
    private String subType;

    /** 消息 ID */
    @JsonProperty("message_id")
    private long messageId;

    /** 消息发送者 QQ 号 */
    @JsonProperty("sender_id")
    private long senderId;

    /** 操作者 QQ 号（设置精华的管理员） */
    @JsonProperty("operator_id")
    private long operatorId;
}
