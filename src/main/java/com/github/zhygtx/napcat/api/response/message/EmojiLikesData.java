package com.github.zhygtx.napcat.api.response.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * 获取消息表情点赞列表
 * <p>
 */
@Data
public class EmojiLikesData {

    /** 表情回应列表 */
    @JsonProperty("emoji_like_list")
    private List<EmojiLikesItemData> emojiLikeList;

}