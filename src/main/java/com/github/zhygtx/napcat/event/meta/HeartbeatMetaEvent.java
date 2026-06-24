package com.github.zhygtx.napcat.event.meta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 心跳元事件。
 * <p>
 * 对应 {@code meta_event_type=heartbeat}。
 * NapCat 会定期发送心跳事件，用于确认连接状态。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeartbeatMetaEvent extends MetaEvent {

    /** 状态信息（包含 online、good 等字段） */
    @JsonProperty("status")
    private Map<String, Object> status;

    /** 心跳间隔（毫秒） */
    @JsonProperty("interval")
    private long interval;

    /**
     * 判断机器人是否在线。
     */
    public boolean isOnline() {
        if (status != null && status.containsKey("online")) {
            Object online = status.get("online");
            if (online instanceof Boolean) {
                return (Boolean) online;
            }
        }
        return false;
    }

    /**
     * 判断机器人状态是否良好。
     */
    public boolean isGood() {
        if (status != null && status.containsKey("good")) {
            Object good = status.get("good");
            if (good instanceof Boolean) {
                return (Boolean) good;
            }
        }
        return false;
    }
}
