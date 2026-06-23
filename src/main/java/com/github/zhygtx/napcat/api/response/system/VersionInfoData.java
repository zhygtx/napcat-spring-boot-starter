package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 版本信息响应数据。
 * <p>
 * 对应 <code>/get_version_info</code> 的 data 字段。
 * <pre>
 * {
 *   "app_name": "NapCat.Onebot",
 *   "protocol_version": "v11",
 *   "app_version": "1.0.0"
 * }
 * </pre>
 */
@Data
public class VersionInfoData {

    /** 应用名称 */
    private String appName;

    /** 协议版本 */
    private String protocolVersion;

    /** 应用版本 */
    private String appVersion;
}
