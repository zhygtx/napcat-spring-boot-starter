package com.github.zhygtx.napcat.api.response.group;

import lombok.Data;

/**
 * 群成员信息响应数据。
 * <p>
 * 对应 <code>/get_group_member_info</code> 的 data 字段。
 * <pre>
 * {
 *   "group_id": 123456,
 *   "user_id": 123456789,
 *   "nickname": "昵称",
 *   "card": "名片",
 *   "sex": "male",
 *   "age": 25,
 *   "join_time": 1700000000,
 *   "last_sent_time": 1700000000,
 *   "level": "50",
 *   "role": "member",
 *   ...
 * }
 * </pre>
 */
@Data
public class GroupMemberData {

    /** 群号 */
    private long groupId;

    /** QQ 号 */
    private long userId;

    /** 昵称 */
    private String nickname;

    /** 群名片 */
    private String card;

    /** 性别 */
    private String sex;

    /** 年龄 */
    private int age;

    /** 入群时间戳 */
    private long joinTime;

    /** 最后发言时间戳 */
    private long lastSentTime;

    /** 等级 */
    private String level;

    /** QQ 等级 */
    private int qqLevel;

    /** 角色（owner/admin/member） */
    private String role;

    /** 头衔 */
    private String title;

    /** 地区 */
    private String area;

    /** 是否不良记录 */
    private boolean unfriendly;

    /** 头衔过期时间 */
    private long titleExpireTime;

    /** 是否允许修改名片 */
    private boolean cardChangeable;

    /** 禁言截止时间戳 */
    private long shutUpTimestamp;

    /** 是否为机器人 */
    private boolean isRobot;

    /** Q 龄 */
    private int qage;
}
