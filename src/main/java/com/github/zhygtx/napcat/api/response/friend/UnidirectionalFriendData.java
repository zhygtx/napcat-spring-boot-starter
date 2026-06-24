package com.github.zhygtx.napcat.api.response.friend;

import lombok.Data;

/**
 * 单向好友条目响应数据。
 * <p>
 * 对应 <code>/get_unidirectional_friend_list</code> 的 data 数组条目。
 * <pre>
 * {
 *   "uin": 123456789,
 *   "uid": "u_123",
 *   "nick_name": "单向好友",
 *   "age": 20,
 *   "source": "来源"
 * }
 * </pre>
 */
@Data
public class UnidirectionalFriendData {

    /** QQ号 */
    private long uin;

    /** 用户UID */
    private String uid;

    /** 昵称 */
    private String nickName;

    /** 年龄 */
    private int age;

    /** 来源 */
    private String source;
}
