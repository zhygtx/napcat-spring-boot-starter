package com.github.zhygtx.napcat.api.response.system;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * RKey 响应数据。
 * <p>
 * 对应 <code>/get_rkey</code> 的 data 字段。
 */
@Data
public class RKeyData {

    /** RKey 映射 */
    private JsonNode rkey;
}
