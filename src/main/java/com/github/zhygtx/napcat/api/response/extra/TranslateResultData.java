package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * 翻译结果响应数据。
 * <p>
 * 对应 <code>/translate_en2zh</code> 的 data 字段。
 */
@Data
public class TranslateResultData {

    /** 翻译后的词汇列表 */
    private Object words;
}
