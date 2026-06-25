package com.github.zhygtx.napcat.api.response.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 */
@Data
public class UnidirectionalFriendData {

    /** QQ号 */
    private Long uin;

    /** 用户UID */
    private String uid;

    /** 昵称 */
    @JsonProperty("nick_name")
    private String nickName;

    /** 年龄 */
    private Long age;

    /** 来源 */
    private String source;

}