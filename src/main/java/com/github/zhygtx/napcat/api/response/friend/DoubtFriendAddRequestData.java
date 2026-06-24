package com.github.zhygtx.napcat.api.response.friend;

import lombok.Data;

/**
 * 可疑好友申请条目响应数据。
 * <p>
 * 对应 <code>/get_doubt_friends_add_request</code> 的 data 数组条目。
 */
@Data
public class DoubtFriendAddRequestData {

    /** 申请者 QQ */
    private long requesterUin;

    /** 申请者昵称 */
    private String requesterNick;

    /** 申请来源描述 */
    private String source;

    /** 申请消息 */
    private String message;

    /** 申请标识（flag） */
    private String flag;

    /** 申请时间 */
    private long time;
}
