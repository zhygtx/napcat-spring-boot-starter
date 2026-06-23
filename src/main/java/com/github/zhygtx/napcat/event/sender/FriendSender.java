package com.github.zhygtx.napcat.event.sender;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import lombok.Data;
/**
 * 私聊消息发送者信息（FriendSender）。
 * <p>
 * 字段说明：
 * <ul>
 *   <li>userId — 发送者 QQ 号</li>
 *   <li>nickname — 昵称</li>
 *   <li>sex — 性别（male / female / unknown）</li>
 *   <li>age — 年龄</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendSender {

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("age")
    private int age;
}
