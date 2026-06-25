package com.github.zhygtx.napcat.api.response.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * <p>
 */
@Data
public class ProfileLikeVoteInfoData {

    /** 总点赞数 */
    @JsonProperty("total_count")
    private Integer totalCount;

    /** 新增点赞数 */
    @JsonProperty("new_count")
    private Integer newCount;

    /** 新增附近点赞数 */
    @JsonProperty("new_nearby_count")
    private Integer newNearbyCount;

    /** 最后访问时间 */
    @JsonProperty("last_visit_time")
    private Long lastVisitTime;

    /** 点赞用户信息 */
    private List<String> userInfos;

}