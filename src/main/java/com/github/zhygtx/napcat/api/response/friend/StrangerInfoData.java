package com.github.zhygtx.napcat.api.response.friend;

import lombok.Data;

/**
 * 陌生人信息响应数据。
 * <p>
 * 对应 <code>/get_stranger_info</code> 的 data 字段。
 * <pre>
 * {
 *   "user_id": 123456789,
 *   "uid": "u_xxx",
 *   "nickname": "昵称",
 *   "age": 25,
 *   "qid": "...",
 *   "qqLevel": 10,
 *   "sex": "unknown",
 *   "long_nick": "个性签名",
 *   "reg_time": 1500000000,
 *   "is_vip": false,
 *   "is_years_vip": false,
 *   "vip_level": 0,
 *   "remark": "",
 *   "status": 0,
 *   "login_days": 100
 * }
 * </pre>
 */
@Data
public class StrangerInfoData {

    /** 用户 QQ */
    private long userId;

    /** UID */
    private String uid;

    /** 昵称 */
    private String nickname;

    /** 年龄 */
    private int age;

    /** QID */
    private String qid;

    /** QQ 等级 */
    private int qqLevel;

    /** 性别 */
    private String sex;

    /** 个性签名 */
    private String longNick;

    /** 注册时间 */
    private long regTime;

    /** 是否 VIP */
    private boolean isVip;

    /** 是否年费 VIP */
    private boolean isYearsVip;

    /** VIP 等级 */
    private int vipLevel;

    /** 备注 */
    private String remark;

    /** 状态 */
    private int status;

    /** 登录天数 */
    private int loginDays;
}
