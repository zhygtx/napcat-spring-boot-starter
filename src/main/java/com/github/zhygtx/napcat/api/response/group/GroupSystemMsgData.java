package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 群系统消息响应数据。
 * <p>
 * 对应 <code>/get_group_system_msg</code> 的 data 字段。
 */
@Data
public class GroupSystemMsgData {

    /** 加群请求列表 */
    private JsonNode joinRequests;

    /** 被邀请入群列表 */
    private JsonNode invitedRequests;
}
