package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 群详细信息响应数据。
 * <p>
 * 对应 <code>/get_group_detail_info</code> 的 data 字段。
 */
@Data
public class GroupDetailInfoData {

    /** 群号 */
    private long groupId;

    /** 群名称 */
    private String groupName;

    /** 群成员数 */
    private int memberCount;

    /** 最大成员数 */
    private int maxMemberCount;

    /** 群主 QQ */
    private long ownerQq;

    /** 群创建时间 */
    private long createTime;

    /** 群分类 */
    private int groupClass;

    /** 群标签 */
    private JsonNode tags;

    /** 群描述 */
    private String groupDesc;

    /** 群头像 */
    private String groupFace;

    /** 加群选项 */
    private String addOption;

    /** 是否允许群机器人加群 */
    private String robotAddOption;

    /** 是否可搜索 */
    private int searchable;

    /** 群公告 */
    private String memo;

    /** 群等级 */
    private int groupLevel;

    /** 群活跃度 */
    private int activeMemberCount;
}
