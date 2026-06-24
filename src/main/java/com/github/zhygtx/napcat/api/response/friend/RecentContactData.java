package com.github.zhygtx.napcat.api.response.friend;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 最近会话条目响应数据。
 * <p>
 * 对应 <code>/get_recent_contact</code> 的 data 数组条目。
 * <pre>
 * {
 *   "peerUin": "123456",
 *   "peerName": "测试",
 *   "remark": "",
 *   "msgTime": "1734567890",
 *   "chatType": 1,
 *   "msgId": "12345",
 *   "sendNickName": "发送者",
 *   "sendMemberName": "群名片",
 *   "lastestMsg": { ... }
 * }
 * </pre>
 */
@Data
public class RecentContactData {

    /** 对象 QQ */
    private String peerUin;

    /** 对象名称 */
    private String peerName;

    /** 备注 */
    private String remark;

    /** 消息时间 */
    private String msgTime;

    /** 聊天类型 */
    private int chatType;

    /** 消息 ID */
    private String msgId;

    /** 发送者昵称 */
    private String sendNickName;

    /** 发送者群名片 */
    private String sendMemberName;

    /** 最后一条消息 */
    private JsonNode lastestMsg;
}
