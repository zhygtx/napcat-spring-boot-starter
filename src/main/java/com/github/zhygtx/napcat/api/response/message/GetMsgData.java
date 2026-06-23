package com.github.zhygtx.napcat.api.response.message;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 获取消息 API 的响应数据。
 * <p>
 * 对应 <code>/get_msg</code> 的 data 字段。
 * <pre>
 * {
 *   "time": 1710000000,
 *   "message_type": "group",
 *   "message_id": 123456,
 *   "real_id": 123456,
 *   "message_seq": 1,
 *   "sender": { "user_id": 123456789, "nickname": "昵称" },
 *   "message": "hello",
 *   "raw_message": "hello",
 *   "font": 0,
 *   "group_id": 123456,
 *   "user_id": 123456789
 * }
 * </pre>
 */
@Data
public class GetMsgData {

    /** 发送时间戳 */
    private long time;

    /** 消息类型（private/group） */
    private String messageType;

    /** 消息 ID */
    private long messageId;

    /** 真实 ID */
    private long realId;

    /** 消息序号 */
    private long messageSeq;

    /** 发送者信息（原始 JSON 节点，sender 结构不固定） */
    private JsonNode sender;

    /** 消息内容（支持 String 或 Array） */
    private JsonNode message;

    /** 原始消息内容 */
    private String rawMessage;

    /** 字体 */
    private long font;

    /** 群号（仅群消息有） */
    private Long groupId;

    /** 发送者 QQ 号 */
    private Long userId;

    /** 表情回应列表（可选） */
    private JsonNode emojiLikesList;
}
