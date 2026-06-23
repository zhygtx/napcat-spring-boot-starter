package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 凭证响应数据（Cookies + CSRF Token）。
 * <p>
 * 对应 <code>/get_credentials</code> 的 data 字段。
 */
@Data
public class CredentialsData {

    /** Cookies 字符串 */
    private String cookies;

    /** CSRF Token */
    private int csrfToken;
}
