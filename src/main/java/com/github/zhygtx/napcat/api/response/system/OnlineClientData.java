package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 在线客户端信息响应数据。
 * <p>
 * 对应 <code>/get_online_clients</code> 的 data 数组条目。
 */
@Data
public class OnlineClientData {

    /** 客户端 ID */
    private long appId;

    /** 设备名称 */
    private String deviceName;

    /** 设备类型 */
    private String deviceKind;

    /** 客户端平台 */
    private String platform;

    /** 客户端版本 */
    private String clientVersion;
}
