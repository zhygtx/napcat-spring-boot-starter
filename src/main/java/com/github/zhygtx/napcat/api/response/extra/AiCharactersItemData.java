package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 */
@Data
public class AiCharactersItemData {

    /** 角色ID */
    @JsonProperty("character_id")
    private String characterId;

    /** 角色名称 */
    @JsonProperty("character_name")
    private String characterName;

    /** 预览URL */
    @JsonProperty("preview_url")
    private String previewUrl;

}