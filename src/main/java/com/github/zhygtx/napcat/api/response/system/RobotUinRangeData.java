package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 机器人 UIN 范围响应数据。
 * <p>
 * 对应 <code>/get_robot_uin_range</code> 的 data 数组条目。
 */
@Data
public class RobotUinRangeData {

    /** 最小 Uin */
    private long minUin;

    /** 最大 Uin */
    private long maxUin;
}
