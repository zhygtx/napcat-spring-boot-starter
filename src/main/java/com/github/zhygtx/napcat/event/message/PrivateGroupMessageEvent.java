package com.github.zhygtx.napcat.event.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 群临时会话私聊消息事件。
 * <p>
 * 对应 {@code post_type=message, message_type=private, sub_type=group}（即 message.private.group）。
 * 表示来自群成员的临时会话消息，额外携带 {@code target_id} 和 {@code temp_source} 字段。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrivateGroupMessageEvent extends PrivateMessageEvent {

    /** 临时会话来源 ID（群号） */
    @JsonProperty("target_id")
    private Long targetId;

    /** 临时会话来源类型 */
    @JsonProperty("temp_source")
    private Integer tempSource;
}
