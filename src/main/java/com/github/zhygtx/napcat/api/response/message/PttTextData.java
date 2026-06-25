package com.github.zhygtx.napcat.api.response.message;

import lombok.Data;

/**
 * 获取语音转文字结果
 * <p>
 */
@Data
public class PttTextData {

    /** 得到的文本 */
    private String text;

}