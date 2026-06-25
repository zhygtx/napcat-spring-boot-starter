package com.github.zhygtx.napcat.api.response.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取用户在线状态
 * <p>
 */
@Data
public class UserStatusData {

    /** 在线状态 */
    private Long status;

    /** 扩展状态 */
    @JsonProperty("ext_status")
    private Long extStatus;

}