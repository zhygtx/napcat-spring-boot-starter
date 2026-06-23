package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * ClientKey 响应数据。
 * <p>
 * 对应 <code>/get_clientkey</code> 的 data 字段。
 */
@Data
public class ClientKeyData {

    /** ClientKey 字符串 */
    private String clientKey;
}
