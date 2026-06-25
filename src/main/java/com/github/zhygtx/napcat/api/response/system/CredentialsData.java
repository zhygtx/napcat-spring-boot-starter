package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 获取登录凭证
 * <p>
 */
@Data
public class CredentialsData {

    /** Cookies */
    private String cookies;

    /** CSRF Token */
    private Long token;

}