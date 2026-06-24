package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * Ark 分享响应数据。
 * <p>
 * 对应 <code>/ArkSharePeer</code>、<code>/send_ark_share</code> 的 data 字段。
 * <pre>
 * {
 *   "ark": "{...}"
 * }
 * </pre>
 */
@Data
public class ArkShareData {

    /** Ark JSON 内容 */
    private String ark;
}
