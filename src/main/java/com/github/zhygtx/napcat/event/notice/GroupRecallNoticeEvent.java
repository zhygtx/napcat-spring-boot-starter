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
 * 群消息撤回通知。
 * <p>
 * 对应 {@code notice_type=group_recall}。
 * 当群内的消息被撤回时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupRecallNoticeEvent extends GroupNoticeEvent {

    @JsonProperty("operator_id")
    private long operatorId;

    @JsonProperty("message_id")
    private long messageId;
}
