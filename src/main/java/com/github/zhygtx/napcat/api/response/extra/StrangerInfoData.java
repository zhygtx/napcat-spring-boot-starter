package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取陌生人信息
 * <p>
 */
@Data
public class StrangerInfoData {

    /** 用户QQ */
    @JsonProperty("user_id")
    private Long userId;

    /** UID */
    private String uid;

    /** 昵称 */
    private String nickname;

    /** 年龄 */
    private Long age;

    /** QID */
    private String qid;

    /** QQ等级 */
    private Long qqLevel;

    /** 性别 */
    private String sex;

    /** 个性签名 */
    @JsonProperty("long_nick")
    private String longNick;

    /** 注册时间 */
    @JsonProperty("reg_time")
    private Long regTime;

    /** 是否VIP */
    @JsonProperty("is_vip")
    private Boolean isVip;

    /** 是否年费VIP */
    @JsonProperty("is_years_vip")
    private Boolean isYearsVip;

    /** VIP等级 */
    @JsonProperty("vip_level")
    private Long vipLevel;

    /** 备注 */
    private String remark;

    /** 状态 */
    private Long status;

    /** 登录天数 */
    @JsonProperty("login_days")
    private Long loginDays;

}