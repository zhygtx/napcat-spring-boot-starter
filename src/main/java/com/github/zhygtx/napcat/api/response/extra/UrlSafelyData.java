package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * 检查URL安全性
 * <p>
 */
@Data
public class UrlSafelyData {

    /** 安全等级 (1: 安全, 2: 未知, 3: 危险) */
    private Long level;

}