package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * CSRF Token 响应数据。
 * <p>
 * 对应 <code>/get_csrf_token</code> 的 data 字段。
 */
@Data
public class CsrfTokenData {

    /** token 值 */
    private int token;
}
