package com.github.zhygtx.napcat.api.response.message;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 历史消息响应数据。
 * <p>
 * 对应 <code>/get_group_msg_history</code>、<code>/get_friend_msg_history</code> 的 data 字段。
 * <pre>
 * {
 *   "messages": [ ... ]
 * }
 * </pre>
 */
@Data
public class HistoryMsgData {

    /** 消息列表 */
    private JsonNode messages;
}
