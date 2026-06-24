package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 群忽略通知响应数据。
 * <p>
 * 对应 <code>/get_group_ignored_notifies</code> 的 data 字段。
 * <pre>
 * {
 *   "invited_requests": [ ... ],
 *   "join_requests": [ ... ]
 * }
 * </pre>
 */
@Data
public class GroupIgnoredNotifiesData {

    /** 被邀请入群请求列表 */
    private JsonNode invitedRequests;

    /** 加群请求列表 */
    private JsonNode joinRequests;
}
