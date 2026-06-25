package com.github.zhygtx.napcat.api.response.friend;

import lombok.Data;

/**
 * 获取 Cookies
 * <p>
 */
@Data
public class CookiesData {

    /** Cookies */
    private String cookies;

    /** CSRF Token */
    private String bkn;

}