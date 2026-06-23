package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * Cookies 响应数据。
 * <p>
 * 对应 <code>/get_cookies</code> 的 data 字段。
 */
@Data
public class CookiesData {

    /** Cookies 字符串 */
    private String cookies;
}
