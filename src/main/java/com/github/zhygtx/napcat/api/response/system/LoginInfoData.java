package com.github.zhygtx.napcat.api.response.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取登录号信息
 * <p>
 */
@Data
public class LoginInfoData {

    @JsonProperty("user_id")
    private Long userId;

    private String nickname;

}