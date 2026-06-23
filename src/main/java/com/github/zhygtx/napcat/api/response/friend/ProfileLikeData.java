package com.github.zhygtx.napcat.api.response.friend;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 资料点赞响应数据。
 * <p>
 * 对应 <code>/get_profile_like</code> 的 data 字段。
 */
@Data
public class ProfileLikeData {

    /** 赞的总数 */
    private int count;

    /** 近期的点赞列表 */
    private JsonNode list;
}
