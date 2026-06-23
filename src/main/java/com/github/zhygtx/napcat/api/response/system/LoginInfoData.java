package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 登录号信息响应数据。
 * <p>
 * 对应 <code>/get_login_info</code> 的 data 字段。
 * <pre>
 * {
 *   "user_id": 123456789,
 *   "nickname": "机器人",
 *   "birthday_year": 2000,
 *   "birthday_month": 1,
 *   "birthday_day": 1,
 *   "phone_num": "",
 *   "email": "",
 *   "qid": "...",
 *   "sex": "unknown",
 *   "level": 0,
 *   "age": 0,
 *   "login_days": 0,
 *   "remark": ""
 * }
 * </pre>
 */
@Data
public class LoginInfoData {

    /** QQ 号 */
    private long userId;

    /** 昵称 */
    private String nickname;

    /** 出生年份 */
    private Integer birthdayYear;

    /** 出生月份 */
    private Integer birthdayMonth;

    /** 出生日期 */
    private Integer birthdayDay;

    /** 手机号 */
    private String phoneNum;

    /** 邮箱 */
    private String email;

    /** QID */
    private String qid;

    /** 性别 */
    private String sex;

    /** 等级 */
    private Integer level;

    /** 年龄 */
    private Integer age;

    /** 登录天数 */
    private Integer loginDays;

    /** 备注 */
    private String remark;

    /** 分组 ID */
    private Integer categoryId;

    /** 分组名称 */
    private String categoryName;
}
