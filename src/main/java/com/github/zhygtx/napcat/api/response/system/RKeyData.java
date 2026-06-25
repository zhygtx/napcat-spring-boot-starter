package com.github.zhygtx.napcat.api.response.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 */
@Data
public class RkeyData {

    /** 类型 (private/group) */
    private String type;

    /** RKey */
    private String rkey;

    /** 创建时间 */
    @JsonProperty("created_at")
    private Long createdAt;

    /** 有效期 */
    private Long ttl;

}