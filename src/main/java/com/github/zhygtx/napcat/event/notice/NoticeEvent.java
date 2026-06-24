package com.github.zhygtx.napcat.event.notice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zhygtx.napcat.event.BaseEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * OneBot v11 通知事件基类。
 * <p>
 * 对应 {@code post_type=notice}。
 * 根据 {@code notice_type} 区分各类通知。
 */
@Getter
@Setter
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class NoticeEvent extends BaseEvent {

    /** 通知类型（group_upload / group_admin / group_decrease 等） */
    @JsonProperty("notice_type")
    private String noticeType;
}
