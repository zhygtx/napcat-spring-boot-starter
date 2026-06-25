package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 发送群 AI 语音
 * <p>
 */
@Data
public class GroupAiRecordData {

    /** 消息ID */
    @JsonProperty("message_id")
    private Long messageId;

}