package com.github.zhygtx.napcat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "napcat")
@Data
public class NapCatProperties {
    private Ws ws = new Ws();

    @Data
    public static class Ws {
        private String path = "/ws/bot";
        private int timeout = 30000;
        private int heartbeatInterval = 10000;
    }
}