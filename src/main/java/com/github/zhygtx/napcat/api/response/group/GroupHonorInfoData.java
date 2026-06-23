package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 群荣誉信息响应数据。
 * <p>
 * 对应 <code>/get_group_honor_info</code> 的 data 字段。
 */
@Data
public class GroupHonorInfoData {

    /** 群号 */
    private long groupId;

    /** 当前龙王信息 */
    private JsonNode currentTalkative;

    /** 荣誉列表（talkative/performer/emotion 等各类型的榜单） */
    private JsonNode honorList;
}
