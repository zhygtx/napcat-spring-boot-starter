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
 * 群名片变更通知。
 * <p>
 * 对应 {@code notice_type=group_card}。
 * 当群成员的名片发生变更时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupCardNoticeEvent extends GroupNoticeEvent {

    /** 新群名片 */
    @JsonProperty("card_new")
    private String cardNew;

    /** 旧群名片 */
    @JsonProperty("card_old")
    private String cardOld;
}
