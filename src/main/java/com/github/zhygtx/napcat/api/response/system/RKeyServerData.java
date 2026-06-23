package com.github.zhygtx.napcat.api.response.system;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * RKey 服务器响应数据。
 * <p>
 * 对应 <code>/get_rkey_server</code> 的 data 字段。
 */
@Data
public class RKeyServerData {

    /** 服务器列表 */
    private JsonNode servers;
}
