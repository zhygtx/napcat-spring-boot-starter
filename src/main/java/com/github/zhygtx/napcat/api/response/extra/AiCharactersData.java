package com.github.zhygtx.napcat.api.response.extra;

import java.util.List;
import lombok.Data;

/**
 * <p>
 */
@Data
public class AiCharactersData {

    /** 角色类型 */
    private String type;

    /** 角色列表 */
    private List<AiCharactersItemData> characters;

}