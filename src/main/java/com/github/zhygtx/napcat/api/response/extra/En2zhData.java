package com.github.zhygtx.napcat.api.response.extra;

import java.util.List;
import lombok.Data;

/**
 * 英文单词翻译
 * <p>
 */
@Data
public class En2zhData {

    /** 翻译结果列表 */
    private List<String> words;

}