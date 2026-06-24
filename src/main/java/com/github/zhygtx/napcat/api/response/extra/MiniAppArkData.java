package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * 小程序 Ark 响应数据。
 * <p>
 * 对应 <code>/get_mini_app_ark</code> 的 data 字段。
 */
@Data
public class MiniAppArkData {

    /** Ark JSON 数据 */
    private Object data;
}
