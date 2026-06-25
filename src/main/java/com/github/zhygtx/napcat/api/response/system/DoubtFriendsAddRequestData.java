package com.github.zhygtx.napcat.api.response.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 */
@Data
public class DoubtFriendsAddRequestData {

    @JsonProperty("user_id")
    private Long userId;

    private String nickname;

    private Long age;

    private String sex;

    private String reason;

    private String flag;

}