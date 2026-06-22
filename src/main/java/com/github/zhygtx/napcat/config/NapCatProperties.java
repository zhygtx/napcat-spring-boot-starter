package com.github.zhygtx.napcat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "napcat")
@Data
public class NapCatProperties {
    private Ws ws = new Ws();

    @Data
    public static class Ws {
        private String url = "/ws/bot";           // WebSocket 端点路径
        private int timeout = 30000;              // WebSocket 连接超时时间（毫秒）
        private int heartbeatTimeout = 60000;      // 心跳超时时间（毫秒）- 新增
        private boolean accountIsolation = false; // 是否启用账号级隔离
        private boolean secretKeyIsolation = false;// 是否启用密钥鉴权
    }
}