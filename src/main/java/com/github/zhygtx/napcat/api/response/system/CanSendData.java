package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 是否可以发送图片/语音的响应数据。
 * <p>
 * 对应 <code>/can_send_image</code>、<code>/can_send_record</code> 的 data 字段。
 */
@Data
public class CanSendData {

    /** 是否可以发送 */
    private boolean yes;
}
