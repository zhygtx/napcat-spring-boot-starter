package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 表情点赞列表响应数据。
 * <p>
 * 对应 <code>/get_emoji_likes</code> 的 data 字段。
 * <pre>
 * {
 *   "emoji_like_list": [ ... ]
 * }
 * </pre>
 */
@Data
public class EmojiLikesData {

    /** 表情点赞列表 */
    private JsonNode emojiLikeList;
}
