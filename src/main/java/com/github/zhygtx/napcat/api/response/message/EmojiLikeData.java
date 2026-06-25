package com.github.zhygtx.napcat.api.response.message;

import java.util.List;
import lombok.Data;

/**
 * 获取表情点赞详情
 * <p>
 */
@Data
public class EmojiLikeData {

    /** 表情回应列表 */
    private List<EmojiLikeItemData> emojiLikesList;

    /** 分页Cookie */
    private String cookie;

    /** 是否最后一页 */
    private Boolean isLastPage;

    /** 是否第一页 */
    private Boolean isFirstPage;

    /** 结果状态码 */
    private Long result;

    /** 错 误信息 */
    private String errMsg;

}