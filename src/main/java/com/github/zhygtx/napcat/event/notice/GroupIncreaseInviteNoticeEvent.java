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
 * 群成员增加（邀请加入）通知。
 * <p>
 * 对应 {@code notice_type=group_increase, sub_type=invite}。
 * 当某用户被邀请加入群聊时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupIncreaseInviteNoticeEvent extends GroupIncreaseNoticeEvent {

    /** 邀请者 QQ 号 */
    @JsonProperty("operator_id")
    private long operatorId;
}
