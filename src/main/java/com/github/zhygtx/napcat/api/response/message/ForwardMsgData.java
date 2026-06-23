package com.github.zhygtx.napcat.api.response.message;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 合并转发消息 API 的响应数据。
 * <p>
 * 对应 <code>/get_forward_msg</code> 的 data 字段。
 */
@Data
public class ForwardMsgData {

    /** 消息内容（数组形式的转发消息节点列表） */
    private JsonNode message;
}
