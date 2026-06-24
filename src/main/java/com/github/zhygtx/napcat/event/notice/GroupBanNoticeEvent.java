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
 * 群禁言通知基类。
 * <p>
 * 对应 {@code notice_type=group_ban}。
 * {@code sub_type} 为 {@code ban}（禁言）或 {@code lift_ban}（解除禁言）。
 * {@code duration} 为禁言时长（秒），解除禁言时为 0。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupBanNoticeEvent extends GroupNoticeEvent {

    /** 子类型（ban / lift_ban） */
    @JsonProperty("sub_type")
    private String subType;

    /** 操作者 QQ 号（管理员或群主） */
    @JsonProperty("operator_id")
    private long operatorId;

    /** 禁言时长（秒），解除禁言时为 0 */
    @JsonProperty("duration")
    private long duration;
}
