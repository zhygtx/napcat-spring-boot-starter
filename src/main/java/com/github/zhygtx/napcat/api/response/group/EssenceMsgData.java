package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * <p>
 */
@Data
public class EssenceMsgData {

    /** 消息序号 */
    @JsonProperty("msg_seq")
    private Long msgSeq;

    /** 消息随机数 */
    @JsonProperty("msg_random")
    private Long msgRandom;

    /** 发送者QQ */
    @JsonProperty("sender_id")
    private Long senderId;

    /** 发送者昵称 */
    @JsonProperty("sender_nick")
    private String senderNick;

    /** 操作者QQ */
    @JsonProperty("operator_id")
    private Long operatorId;

    /** 操作者昵称 */
    @JsonProperty("operator_nick")
    private String operatorNick;

    /** 消息ID */
    @JsonProperty("message_id")
    private Long messageId;

    /** 操作时间 */
    @JsonProperty("operator_time")
    private Long operatorTime;

    /** 消息内容 */
    private List<String> content;

}