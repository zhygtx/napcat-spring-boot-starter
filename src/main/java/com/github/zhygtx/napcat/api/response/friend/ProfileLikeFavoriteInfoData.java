package com.github.zhygtx.napcat.api.response.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * <p>
 */
@Data
public class ProfileLikeFavoriteInfoData {

    /** 点赞用户信息 */
    private List<String> userInfos;

    /** 总点赞数 */
    @JsonProperty("total_count")
    private Integer totalCount;

    /** 最后点赞时间 */
    @JsonProperty("last_time")
    private Long lastTime;

    /** 今日点赞数 */
    @JsonProperty("today_count")
    private Integer todayCount;

}