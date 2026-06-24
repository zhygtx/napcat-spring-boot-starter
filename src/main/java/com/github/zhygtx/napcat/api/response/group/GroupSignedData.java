package com.github.zhygtx.napcat.api.response.group;

import lombok.Data;

/**
 * 群打卡条目响应数据。
 * <p>
 * 对应 <code>/get_group_signed_list</code> 的 data 数组条目。
 * <pre>
 * {
 *   "user_id": 123456789,
 *   "nick": "打卡者",
 *   "time": 1680000000,
 *   "rank": 1
 * }
 * </pre>
 */
@Data
public class GroupSignedData {

    /** 打卡者 QQ */
    private long userId;

    /** 打卡者昵称 */
    private String nick;

    /** 打卡时间 */
    private long time;

    /** 打卡排名 */
    private int rank;
}
