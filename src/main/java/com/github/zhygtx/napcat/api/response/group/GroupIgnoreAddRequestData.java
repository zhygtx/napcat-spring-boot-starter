package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 群被忽略的加群请求响应数据。
 * <p>
 * 对应 <code>/get_group_ignore_add_request</code> 的 data 字段（数组形式）。
 */
@Data
public class GroupIgnoreAddRequestData {

    /** 请求 ID */
    private long requestId;

    /** 请求者 QQ */
    private long requesterUin;

    /** 请求者昵称 */
    private String requesterNick;

    /** 请求消息 */
    private String message;

    /** 群号 */
    private long groupId;

    /** 群名 */
    private String groupName;

    /** 是否已检查 */
    private boolean checked;

    /** 操作者 QQ */
    private long actorUin;

    /** 操作者昵称 */
    private String actorNick;

    /** 请求时间 */
    private long time;

    /** 原始 JSON（保留未映射字段） */
    private JsonNode raw;
}
