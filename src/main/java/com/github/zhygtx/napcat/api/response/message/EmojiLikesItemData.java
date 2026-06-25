package com.github.zhygtx.napcat.api.response.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 */
@Data
public class EmojiLikesItemData {

    /** 点击者QQ号 */
    @JsonProperty("user_id")
    private String userId;

    /** 昵称? */
    @JsonProperty("nick_name")
    private String nickName;

}