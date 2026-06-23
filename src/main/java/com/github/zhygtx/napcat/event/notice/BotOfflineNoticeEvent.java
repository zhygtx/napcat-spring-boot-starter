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
 * Bot QQ 账号离线通知。
 * <p>
 * 对应 {@code notice_type=bot_offline}。
 * 当 Bot 的 QQ 账号因被踢下线、风控或其他原因离线（但 WebSocket 连接仍保持）时触发。
 * <p>
 * 收到此事件后 SDK 会自动调用 {@code BotEventListener.botOffline()}，
 * 但不会关闭 WebSocket 连接，NapCat 可在账号恢复后继续使用同一条连接。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BotOfflineNoticeEvent extends NoticeEvent {

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("message")
    private String message;
}
