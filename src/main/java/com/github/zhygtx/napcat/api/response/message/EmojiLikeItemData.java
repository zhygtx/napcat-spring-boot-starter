package com.github.zhygtx.napcat.api.response.message;

import lombok.Data;

/**
 * <p>
 */
@Data
public class EmojiLikeItemData {

    /** TinyID */
    private String tinyId;

    /** 昵称 */
    private String nickName;

    /** 头像URL */
    private String headUrl;

}