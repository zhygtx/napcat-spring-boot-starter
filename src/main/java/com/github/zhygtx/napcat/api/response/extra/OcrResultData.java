package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * OCR 识别结果响应数据。
 * <p>
 * 对应 <code>/ocr_image</code> 的 data 字段。
 */
@Data
public class OcrResultData {

    /** OCR 识别文本列表 */
    private Object texts;

    /** 语言 */
    private String language;
}
