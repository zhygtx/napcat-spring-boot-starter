package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 */
@Data
public class GroupIgnoreAddRequestData {

    /** 请求ID */
    @JsonProperty("request_id")
    private Long requestId;

    /** 邀请者QQ */
    @JsonProperty("invitor_uin")
    private Long invitorUin;

    /** 邀请者昵称 */
    @JsonProperty("invitor_nick")
    private String invitorNick;

    /** 群号 */
    @JsonProperty("group_id")
    private Long groupId;

    /** 验证信息 */
    private String message;

    /** 群名称 */
    @JsonProperty("group_name")
    private String groupName;

    /** 是否已处理 */
    private Boolean checked;

    /** 处理者QQ */
    private Long actor;

    /** 请求者昵称 */
    @JsonProperty("requester_nick")
    private String requesterNick;

}