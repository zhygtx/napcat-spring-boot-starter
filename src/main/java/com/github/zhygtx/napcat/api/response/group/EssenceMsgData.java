package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 精华消息条目响应数据。
 * <p>
 * 对应 <code>/get_essence_msg_list</code> 的 data 数组条目。
 */
@Data
public class EssenceMsgData {

    /** 发送者 QQ */
    private long senderId;

    /** 发送者昵称 */
    private String senderNick;

    /** 发送时间 */
    private long senderTime;

    /** 操作者 QQ */
    private long operatorId;

    /** 操作者昵称 */
    private String operatorNick;

    /** 操作时间 */
    private long operatorTime;

    /** 消息内容（原始 JSON） */
    private JsonNode content;
}
