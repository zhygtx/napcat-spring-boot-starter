package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * <p>
 */
@Data
public class GroupNoticeData {

    /** 发送者QQ */
    @JsonProperty("sender_id")
    private Long senderId;

    /** 发布时间 */
    @JsonProperty("publish_time")
    private Long publishTime;

    /** 公告ID */
    @JsonProperty("notice_id")
    private String noticeId;

    /** 公告内容 */
    private GroupNoticeMessageData message;

    /** 设置项 */
    private JsonNode settings;

    /** 阅读数 */
    @JsonProperty("read_num")
    private Long readNum;

}