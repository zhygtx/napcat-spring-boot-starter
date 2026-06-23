package com.github.zhygtx.napcat.api.response.group;

import lombok.Data;

/**
 * 群信息响应数据。
 * <p>
 * 对应 <code>/get_group_info</code> 的 data 字段。
 * <pre>
 * {
 *   "group_id": 123456,
 *   "group_name": "测试群",
 *   "member_count": 100,
 *   "max_member_count": 500,
 *   "group_all_shut": 0,
 *   "group_remark": ""
 * }
 * </pre>
 */
@Data
public class GroupInfoData {

    /** 群号 */
    private long groupId;

    /** 群名称 */
    private String groupName;

    /** 成员人数 */
    private int memberCount;

    /** 最大成员人数 */
    private int maxMemberCount;

    /** 是否全员禁言（0 表示未开启） */
    private int groupAllShut;

    /** 群备注 */
    private String groupRemark;
}
