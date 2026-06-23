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
 * 机器人自己被踢出群通知。
 * <p>
 * 对应 {@code notice_type=group_decrease, sub_type=kick_me}。
 * 当机器人自己被移出群聊时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDecreaseKickMeNoticeEvent extends GroupDecreaseNoticeEvent {

    @JsonProperty("operator_id")
    private long operatorId;
}
