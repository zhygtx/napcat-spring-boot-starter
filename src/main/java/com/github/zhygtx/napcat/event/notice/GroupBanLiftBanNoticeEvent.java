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
 * 群成员解除禁言通知。
 * <p>
 * 对应 {@code notice_type=group_ban, sub_type=lift_ban}。
 * 当群成员被解除禁言时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupBanLiftBanNoticeEvent extends GroupBanNoticeEvent {

    /** 解除前已禁言的时长（秒） */
    @JsonProperty("duration")
    private long duration;
}
