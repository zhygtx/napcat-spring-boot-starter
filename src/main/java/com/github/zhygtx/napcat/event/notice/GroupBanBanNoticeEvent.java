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
 * 群成员被禁言通知。
 * <p>
 * 对应 {@code notice_type=group_ban, sub_type=ban}。
 * 当群成员被管理员禁言时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupBanBanNoticeEvent extends GroupBanNoticeEvent {

    /** 禁言时长（秒） */
    @JsonProperty("duration")
    private long duration;
}
