package com.github.zhygtx.napcat.api.response.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取版本信息
 * <p>
 */
@Data
public class VersionInfoData {

    /** 应用名称 */
    @JsonProperty("app_name")
    private String appName;

    /** 协议版本 */
    @JsonProperty("protocol_version")
    private String protocolVersion;

    /** 应用版本 */
    @JsonProperty("app_version")
    private String appVersion;

}