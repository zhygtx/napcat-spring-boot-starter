package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 */
@Data
public class GroupShutData {

    @JsonProperty("user_id")
    private Long userId;

    private String nickname;

    @JsonProperty("shut_up_time")
    private Long shutUpTime;

}