package com.github.zhygtx.napcat.api.response.group;

import lombok.Data;

/**
 * 群扩展信息响应数据。
 * <p>
 * 对应 <code>/get_group_info_ex</code> 的 data 字段。
 */
@Data
public class GroupInfoExData {

    /** 群号 */
    private long groupId;

    /** 群名称 */
    private String groupName;

    /** 群备注 */
    private String groupRemark;

    /** 群主 QQ */
    private long ownerQq;

    /** 管理员列表 */
    private String admins;
}
