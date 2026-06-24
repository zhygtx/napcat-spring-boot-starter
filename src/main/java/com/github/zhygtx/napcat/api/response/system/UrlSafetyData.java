package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * URL 安全检查响应数据。
 * <p>
 * 对应 <code>/check_url_safely</code> 的 data 字段。
 * <pre>
 * {
 *   "level": 0
 * }
 * </pre>
 */
@Data
public class UrlSafetyData {

    /** 安全等级（0 安全，1 警告，2 风险，3 危险） */
    private int level;
}
