package com.github.zhygtx.napcat.api.response.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import lombok.Data;

/**
 * 获取消息
 * <p>
 */
@Data
public class MsgData {

    /** 发送时间 */
    private Long time;

    /** 消息类型 */
    @JsonProperty("message_type")
    private String messageType;

    /** 消息ID */
    @JsonProperty("message_id")
    private Long messageId;

    /** 真实ID */
    @JsonProperty("real_id")
    private Long realId;

    /** 消息序号 */
    @JsonProperty("message_seq")
    private Long messageSeq;

    /** 发送者 */
    private JsonNode sender;

    /** 消息内容 */
    private JsonNode message;

    /** 原始消息内容 */
    @JsonProperty("raw_message")
    private String rawMessage;

    /** 字体 */
    private Long font;

    /** 群号 */
    @JsonProperty("group_id")
    private Long groupId;

    /** 发送者QQ号 */
    @JsonProperty("user_id")
    private Long userId;

    /** 表情回应列表 */
    @JsonProperty("emoji_likes_list")
    private List<String> emojiLikesList;

}