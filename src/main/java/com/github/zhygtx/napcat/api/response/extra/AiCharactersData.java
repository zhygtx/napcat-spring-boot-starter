package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * AI 角色响应数据。
 * <p>
 * 对应 <code>/get_ai_characters</code> 的 data 数组条目。
 */
@Data
public class AiCharactersData {

    /** 角色 ID */
    private String characterId;

    /** 角色名称 */
    private String characterName;

    /** 角色头像 URL */
    private String avatarUrl;

    /** 角色类型 */
    private String characterType;
}
