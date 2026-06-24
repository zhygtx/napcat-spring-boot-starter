package com.github.zhygtx.napcat.event.sender;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import lombok.Data;
/**
 * 群聊消息发送者信息（GroupSender）。
 * <p>
 * 字段说明：
 * <ul>
 *   <li>userId — 发送者 QQ 号</li>
 *   <li>nickname — 昵称</li>
 *   <li>sex — 性别（male / female / unknown）</li>
 *   <li>age — 年龄</li>
 *   <li>card — 群名片/备注</li>
 *   <li>role — 角色（owner / admin / member）</li>
 *   <li>title — 专属头衔</li>
 *   <li>level — 成员等级</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupSender {

    /** 发送者 QQ 号 */
    @JsonProperty("user_id")
    private long userId;

    /** 昵称 */
    @JsonProperty("nickname")
    private String nickname;

    /** 性别（male / female / unknown） */
    @JsonProperty("sex")
    private String sex;

    /** 年龄 */
    @JsonProperty("age")
    private int age;

    /** 群名片/群昵称 */
    @JsonProperty("card")
    private String card;

    /** 角色（owner / admin / member） */
    @JsonProperty("role")
    private String role;

    /** 专属头衔 */
    @JsonProperty("title")
    private String title;

    /** 成员等级 */
    @JsonProperty("level")
    private String level;
}
