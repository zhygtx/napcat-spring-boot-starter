package com.github.zhygtx.napcat.api.response.friend;

import lombok.Data;

/**
 * 最近联系人条目响应数据。
 * <p>
 * 对应 <code>/get_recent_contact</code> 的 data 数组条目。
 */
@Data
public class RecentContactData {

    /** 联系人类型（group/private） */
    private String type;

    /** 群号（群聊时有效） */
    private long groupId;

    /** 用户 QQ（私聊时有效） */
    private long userId;

    /** 最后消息时间 */
    private long lastMsgTime;

    /** 最后消息内容 */
    private String lastMsgAbstract;

    /** 未读计数 */
    private int unreadCount;
}
