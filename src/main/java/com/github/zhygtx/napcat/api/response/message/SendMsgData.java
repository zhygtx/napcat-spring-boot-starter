package com.github.zhygtx.napcat.api.response.message;

import lombok.Data;

/**
 * 发送消息 API 的响应数据。
 * <p>
 * 对应 <code>/send_group_msg</code>、<code>/send_private_msg</code>、<code>/send_msg</code> 等 API 的 data 字段。
 * <pre>
 * {
 *   "message_id": 123456,
 *   "res_id": "...",
 *   "forward_id": "..."
 * }
 * </pre>
 */
@Data
public class SendMsgData {

    /** 消息 ID */
    private long messageId;

    /** 转发消息的 res_id（合并转发时可能有值） */
    private String resId;

    /** 转发消息的 forward_id（合并转发时可能有值） */
    private String forwardId;
}
