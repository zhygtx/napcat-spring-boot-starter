package com.github.zhygtx.napcat.api.response.group;

import lombok.Data;

/**
 * 群禁言成员响应数据。
 * <p>
 * 对应 <code>/get_group_shut_list</code> 的 data 数组条目。
 */
@Data
public class GroupShutMemberData {

    /** QQ 号 */
    private long userId;

    /** 禁言到期时间戳 */
    private long shutUpTime;
}
