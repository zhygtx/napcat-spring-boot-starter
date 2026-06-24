package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 频道服务资料响应数据。
 * <p>
 * 对应 <code>/get_guild_service_profile</code> 的 data 字段。
 */
@Data
public class GuildServiceProfileData {

    /** 频道 ID */
    private String guildId;

    /** 用户昵称 */
    private String nickName;

    /** 用户头像 URL */
    private String avatarUrl;

    /** 加入时间 */
    private long joinTime;

    /** 角色 */
    private int role;
}
