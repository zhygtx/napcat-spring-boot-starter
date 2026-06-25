package com.github.zhygtx.napcat.api.response.friend;

import lombok.Data;

/**
 * 获取资料点赞
 * <p>
 */
@Data
public class ProfileLikeData {

    /** 用户UID */
    private String uid;

    /** 时间 */
    private String time;

    private ProfileLikeFavoriteInfoData favoriteInfo;

    private ProfileLikeVoteInfoData voteInfo;

}