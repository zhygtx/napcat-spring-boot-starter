package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 发包状态响应数据。
 * <p>
 * 对应 <code>/nc_get_packet_status</code> 的 data 字段。
 */
@Data
public class PacketStatusData {

    /** 发包是否可用 */
    private boolean enable;

    /** 发包速率限制（毫秒） */
    private long interval;
}
