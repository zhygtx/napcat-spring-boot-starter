package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * AI 语音响应数据。
 * <p>
 * 对应 <code>/get_ai_record</code> 的 data 字段。
 */
@Data
public class AiRecordData {

    /** AI 语音内容（JSON 字符串或 base64） */
    private JsonNode record;
}
