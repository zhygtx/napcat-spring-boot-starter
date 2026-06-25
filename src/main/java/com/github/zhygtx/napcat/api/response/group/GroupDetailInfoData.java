package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取群详细信息
 * <p>
 */
@Data
public class GroupDetailInfoData {

    /** 群号 */
    @JsonProperty("group_id")
    private Long groupId;

    /** 群名称 */
    @JsonProperty("group_name")
    private String groupName;

    /** 成员数量 */
    @JsonProperty("member_count")
    private Integer memberCount;

    /** 最大成员数量 */
    @JsonProperty("max_member_count")
    private Integer maxMemberCount;

    /** 全员禁言状态 */
    @JsonProperty("group_all_shut")
    private Long groupAllShut;

    /** 群备注 */
    @JsonProperty("group_remark")
    private String groupRemark;

}