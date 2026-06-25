package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 */
@Data
public class GroupMemberData {

    @JsonProperty("group_id")
    private Long groupId;

    @JsonProperty("user_id")
    private Long userId;

    private String nickname;

    private String card;

    private String role;

}