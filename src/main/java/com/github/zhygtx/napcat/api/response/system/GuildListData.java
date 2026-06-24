package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 频道列表条目响应数据。
 * <p>
 * 对应 <code>/get_guild_list</code> 的 data 数组条目。
 */
@Data
public class GuildListData {

    /** 频道 ID */
    private String guildId;

    /** 频道名称 */
    private String guildName;

    /** 频道头像 URL */
    private String guildFace;

    /** 频道描述 */
    private String guildDesc;

    /** 频道成员数 */
    private int memberCount;

    /** 最大成员数 */
    private int maxMemberCount;

    /** 频道主 QQ */
    private long ownerId;

    /** 是否已加入 */
    private boolean isJoined;
}
