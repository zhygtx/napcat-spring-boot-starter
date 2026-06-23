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
 * 个人资料点赞通知。
 * <p>
 * 对应 {@code notice_type=notify, sub_type=profile_like}。
 * 当有人给机器人点赞时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileLikeNoticeEvent extends NoticeEvent {

    @JsonProperty("operator_id")
    private long operatorId;

    @JsonProperty("operator_nick")
    private String operatorNick;

    @JsonProperty("times")
    private int times;
}
