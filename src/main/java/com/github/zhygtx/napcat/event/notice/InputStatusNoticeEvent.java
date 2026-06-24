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
 * 输入状态通知。
 * <p>
 * 对应 {@code notice_type=notify, sub_type=input_status}。
 * 当好友正在输入时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputStatusNoticeEvent extends NoticeEvent {

    /** 正在输入的用户 QQ 号 */
    @JsonProperty("user_id")
    private long userId;

    /** 群号（群聊输入状态时有效） */
    @JsonProperty("group_id")
    private Long groupId;

    /** 输入状态文本 */
    @JsonProperty("status_text")
    private String statusText;

    /** 输入事件类型 */
    @JsonProperty("event_type")
    private int eventType;
}
