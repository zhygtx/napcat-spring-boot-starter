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
 * 群成员增加通知基类。
 * <p>
 * 对应 {@code notice_type=group_increase}。
 * {@code sub_type} 为 {@code approve}（同意加群）或 {@code invite}（邀请加群）。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupIncreaseNoticeEvent extends GroupNoticeEvent {

    @JsonProperty("sub_type")
    private String subType;

    @JsonProperty("operator_id")
    private long operatorId;
}
