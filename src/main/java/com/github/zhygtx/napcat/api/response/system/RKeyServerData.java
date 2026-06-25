package com.github.zhygtx.napcat.api.response.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取 RKey 服务器
 * <p>
 */
@Data
public class RkeyServerData {

    /** 私聊 RKey */
    @JsonProperty("private_rkey")
    private String privateRkey;

    /** 群聊 RKey */
    @JsonProperty("group_rkey")
    private String groupRkey;

    /** 过期时间 */
    @JsonProperty("expired_time")
    private Long expiredTime;

    /** 名称 */
    private String name;

}