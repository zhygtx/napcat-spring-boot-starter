package com.github.zhygtx.napcat.api.response.system;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 运行状态响应数据。
 * <p>
 * 对应 <code>/get_status</code> 的 data 字段。
 * <pre>
 * {
 *   "online": true,
 *   "good": true,
 *   "stat": { ... }
 * }
 * </pre>
 */
@Data
public class StatusData {

    /** 是否在线 */
    private boolean online;

    /** 状态是否良好 */
    private boolean good;

    /** 统计信息（具体结构不固定，用 JsonNode 承接） */
    private JsonNode stat;
}
