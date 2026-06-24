package com.github.zhygtx.napcat.api.response.message;

import lombok.Data;

/**
 * 语音转文字响应数据。
 * <p>
 * 对应 <code>/fetch_ptt_text</code> 的 data 字段。
 * <pre>
 * {
 *   "text": "..."
 * }
 * </pre>
 */
@Data
public class PttTextData {

    /** 语音转文字结果文本 */
    private String text;
}
