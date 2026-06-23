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

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("age")
    private int age;

    @JsonProperty("card")
    private String card;

    @JsonProperty("role")
    private String role;

    @JsonProperty("title")
    private String title;

    @JsonProperty("level")
    private String level;
}
