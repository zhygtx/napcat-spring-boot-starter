package com.github.zhygtx.napcat.api.response.friend;

import lombok.Data;

/**
 * 好友列表条目响应数据。
 * <p>
 * <code>/get_friend_list</code> 的 data 是一个数组，每一项为此类型。
 * <pre>
 * {
 *   "user_id": 123456789,
 *   "nickname": "好友昵称",
 *   "remark": "备注",
 *   "sex": "male",
 *   "age": 25,
 *   "level": 0,
 *   "qid": "...",
 *   "login_days": 100,
 *   "category_id": 1,
 *   "categoryName": "好友分组",
 *   "categoryId": 1
 * }
 * </pre>
 */
@Data
public class FriendListData {

    /** QQ 号 */
    private long userId;

    /** 昵称 */
    private String nickname;

    /** 备注 */
    private String remark;

    /** 性别 */
    private String sex;

    /** 年龄 */
    private int age;

    /** 等级 */
    private Integer level;

    /** QID */
    private String qid;

    /** 登录天数 */
    private Integer loginDays;

    /** 分组 ID */
    private Integer categoryId;

    /** 分组名称 */
    private String categoryName;

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
}
