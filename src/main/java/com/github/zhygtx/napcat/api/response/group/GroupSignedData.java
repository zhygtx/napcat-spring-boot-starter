package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 */
@Data
public class GroupSignedData {

    /** 打卡者QQ */
    @JsonProperty("user_id")
    private Long userId;

    /** 打卡者昵称 */
    private String nick;

    /** 打卡时间 */
    private Long time;

    /** 打卡排名 */
    private Long rank;

}