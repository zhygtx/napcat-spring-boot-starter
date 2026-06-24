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

    /** 龙王榜单列表 */
    private JsonNode talkativeList;

    /** 群聊之火/达人榜单列表 */
    private JsonNode performerList;

    /** 传说榜单列表 */
    private JsonNode legendList;

    /** 快乐源泉/表情榜单列表 */
    private JsonNode emotionList;

    /** 新兴群友榜单列表 */
    private JsonNode strongNewbieList;

    /** 荣誉列表（保留兼容，与上述各榜单对应） */
    private JsonNode honorList;
}
