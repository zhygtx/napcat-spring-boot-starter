package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取群成员信息
 * <p>
 */
@Data
public class GroupMemberInfoData {

    @JsonProperty("group_id")
    private Long groupId;

    @JsonProperty("user_id")
    private Long userId;

    private String nickname;

    private String card;

    private String role;

}