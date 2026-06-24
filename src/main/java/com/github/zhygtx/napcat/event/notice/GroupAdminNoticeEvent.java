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
 * 群管理员变动通知基类。
 * <p>
 * 对应 {@code notice_type=group_admin}。
 * {@code sub_type} 为 {@code set}（设置管理员）或 {@code unset}（取消管理员）。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupAdminNoticeEvent extends GroupNoticeEvent {

    /** 子类型（set / unset） */
    @JsonProperty("sub_type")
    private String subType;
}
