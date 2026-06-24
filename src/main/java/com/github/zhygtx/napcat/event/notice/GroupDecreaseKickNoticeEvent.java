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
 * 群成员被踢出群通知。
 * <p>
 * 对应 {@code notice_type=group_decrease, sub_type=kick}。
 * 当群成员被管理员移出群聊时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDecreaseKickNoticeEvent extends GroupDecreaseNoticeEvent {

    /** 操作者 QQ 号（踢人的管理员） */
    @JsonProperty("operator_id")
    private long operatorId;
}
