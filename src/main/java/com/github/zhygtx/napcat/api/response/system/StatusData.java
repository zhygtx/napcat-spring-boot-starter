package com.github.zhygtx.napcat.api.response.system;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 获取运行状态
 * <p>
 */
@Data
public class StatusData {

    /** 是否在线 */
    private Boolean online;

    /** 状态是否良好 */
    private Boolean good;

    /** 统计信息 */
    private JsonNode stat;

}