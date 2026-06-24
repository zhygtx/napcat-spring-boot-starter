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
 * 群成员增加（管理员同意）通知。
 * <p>
 * 对应 {@code notice_type=group_increase, sub_type=approve}。
 * 当管理员同意某用户加入群聊时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupIncreaseApproveNoticeEvent extends GroupIncreaseNoticeEvent {

    /** 同意入群的管理员 QQ 号 */
    @JsonProperty("operator_id")
    private long operatorId;
}
