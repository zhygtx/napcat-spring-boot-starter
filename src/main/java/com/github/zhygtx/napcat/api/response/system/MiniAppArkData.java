package com.github.zhygtx.napcat.api.response.system;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 获取小程序 Ark
 * <p>
 */
@Data
public class MiniAppArkData {

    /** Ark数据 */
    private JsonNode data;

}