package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 获取 CSRF Token
 * <p>
 */
@Data
public class CsrfTokenData {

    /** CSRF Token */
    private Long token;

}