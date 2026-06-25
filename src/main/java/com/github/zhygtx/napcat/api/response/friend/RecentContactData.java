package com.github.zhygtx.napcat.api.response.friend;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * <p>
 */
@Data
public class RecentContactData {

    /** 最后一条消息 */
    private JsonNode lastestMsg;

    /** 对象QQ */
    private String peerUin;

    /** 备注 */
    private String remark;

    /** 消息时间 */
    private String msgTime;

    /** 聊天类型 */
    private Long chatType;

    /** 消息ID */
    private String msgId;

    /** 发送者昵称 */
    private String sendNickName;

    /** 发送者群名片 */
    private String sendMemberName;

    /** 对象名称 */
    private String peerName;

}