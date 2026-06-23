package com.github.zhygtx.napcat.event.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 普通群聊消息发送事件。
 * <p>
 * 对应 {@code post_type=message_sent, message_type=group, sub_type=normal}（即 message_sent.group.normal）。
 * 继承自 {@link GroupMessageSentEvent}，无额外字段，通过类型本身区分事件类型。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupNormalMessageSentEvent extends GroupMessageSentEvent {
}
