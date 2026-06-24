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
 * 戳一戳（双击头像）通知。
 * <p>
 * 对应 {@code notice_type=notify, sub_type=poke}。
 * 当用户戳了机器人或在群聊中戳了其他人时触发。
 * 该事件可同时用于好友和群聊场景，{@code groupId} 在好友场景下为 {@code null}。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeNoticeEvent extends NoticeEvent {

    /** 群号（好友戳一戳时为 null） */
    @JsonProperty("group_id")
    private Long groupId;

    /** 发送戳一戳的用户 QQ 号 */
    @JsonProperty("user_id")
    private long userId;

    /** 被戳的用户 QQ 号 */
    @JsonProperty("target_id")
    private long targetId;
}
