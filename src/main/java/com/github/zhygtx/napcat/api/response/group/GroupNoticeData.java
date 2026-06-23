package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 群公告响应数据。
 * <p>
 * 对应 <code>/get_group_notice</code> 的 data 数组条目。
 */
@Data
public class GroupNoticeData {

    /** 公告 ID */
    private String noticeId;

    /** 发送者 QQ */
    private long senderId;

    /** 发布时间 */
    private long publishTime;

    /** 公告消息（含 text 和 images） */
    private JsonNode message;

    /** 是否置顶 */
    private boolean pinned;
}
