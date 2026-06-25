package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取群信息
 * <p>
 */
@Data
public class GroupInfoData {

    @JsonProperty("group_id")
    private Long groupId;

    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("member_count")
    private Integer memberCount;

    @JsonProperty("max_member_count")
    private Integer maxMemberCount;

}