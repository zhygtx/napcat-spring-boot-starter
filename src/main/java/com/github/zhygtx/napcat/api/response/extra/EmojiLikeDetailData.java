package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 表情点赞详情响应数据。
 * <p>
 * 对应 <code>/fetch_emoji_like</code> 的 data 字段。
 * <pre>
 * {
 *   "emojiLikesList": [ ... ],
 *   "cookie": "...",
 *   "isLastPage": false,
 *   "isFirstPage": true
 * }
 * </pre>
 */
@Data
public class EmojiLikeDetailData {

    /** 表情点赞列表 */
    private JsonNode emojiLikesList;

    /** 翻页 cookie */
    private String cookie;

    /** 是否为最后一页 */
    private boolean isLastPage;

    /** 是否为第一页 */
    private boolean isFirstPage;

    /** 结果码 */
    private int result;

    /** 错误信息 */
    private String errMsg;
}
