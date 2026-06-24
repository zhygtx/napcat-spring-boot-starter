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
 * 群成员减少通知基类。
 * <p>
 * 对应 {@code notice_type=group_decrease}。
 * {@code sub_type} 为 {@code leave}（退群）、{@code kick}（被踢）、{@code kick_me}（我被踢）或 {@code disband}（群解散）。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDecreaseNoticeEvent extends GroupNoticeEvent {

    /** 子类型（leave / kick / kick_me / disband） */
    @JsonProperty("sub_type")
    private String subType;

    /** 操作者 QQ 号（管理员或退群者本人） */
    @JsonProperty("operator_id")
    private long operatorId;
}
