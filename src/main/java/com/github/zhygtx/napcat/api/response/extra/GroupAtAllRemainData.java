package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取群艾特全体剩余次数
 * <p>
 */
@Data
public class GroupAtAllRemainData {

    /** 是否可以艾特全体 */
    @JsonProperty("can_at_all")
    private Boolean canAtAll;

    /** 群艾特全体剩余次数 */
    @JsonProperty("remain_at_all_count_for_group")
    private Integer remainAtAllCountForGroup;

    /** 个人艾特全体剩余次数 */
    @JsonProperty("remain_at_all_count_for_uin")
    private Integer remainAtAllCountForUin;

}