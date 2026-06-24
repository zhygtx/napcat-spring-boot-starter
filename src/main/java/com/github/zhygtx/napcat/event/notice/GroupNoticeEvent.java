package com.github.zhygtx.napcat.event.notice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 群通知事件基类。
 * <p>
 * 包含群号和用户号两个公共字段，
 * 所有与群组相关的通知事件都继承此类。
 */
@Getter
@Setter
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GroupNoticeEvent extends NoticeEvent {

    /** 群号 */
    @JsonProperty("group_id")
    private long groupId;

    /** 相关用户 QQ 号 */
    @JsonProperty("user_id")
    private long userId;
}
