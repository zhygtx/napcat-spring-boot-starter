package com.github.zhygtx.napcat.event.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 群请求事件基类。
 * <p>
 * 对应 {@code request_type=group}。
 * {@code sub_type} 为 {@code add}（加群申请）或 {@code invite}（被邀请入群）。
 * 当有人请求加群或邀请机器人入群时触发。
 * <p>
 * 具体子类：
 * <ul>
 *   <li>{@link GroupAddRequestEvent} — 加群申请</li>
 *   <li>{@link GroupInviteRequestEvent} — 被邀请入群</li>
 * </ul>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupRequestEvent extends RequestEvent {

    /** 子类型（add / invite） */
    @JsonProperty("sub_type")
    private String subType;

    /** 群号 */
    @JsonProperty("group_id")
    private long groupId;
}
