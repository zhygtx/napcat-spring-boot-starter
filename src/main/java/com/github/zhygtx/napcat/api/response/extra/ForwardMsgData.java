package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 发送合并转发消息
 * <p>
 */
@Data
public class ForwardMsgData {

    /** 消息ID */
    @JsonProperty("message_id")
    private Long messageId;

    /** 转发消息的 res_id */
    @JsonProperty("res_id")
    private String resId;

    /** 转发消息的 forward_id */
    @JsonProperty("forward_id")
    private String forwardId;

}