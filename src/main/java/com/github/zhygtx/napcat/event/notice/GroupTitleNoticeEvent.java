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
 * 群头衔变更通知。
 * <p>
 * 对应 {@code notice_type=notify, sub_type=title}。
 * 当群成员的专属头衔发生变更时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupTitleNoticeEvent extends GroupNoticeEvent {

    /** 新的专属头衔 */
    @JsonProperty("title")
    private String title;
}
