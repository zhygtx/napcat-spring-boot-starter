package com.github.zhygtx.napcat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Token 鉴权配置校验。
 * <p>
 * 当 {@code napcat.ws.enable-token=true} 且未配置 {@code napcat.ws.token-value} 时，
 * 打印警告日志提醒用户通过 {@code com.github.zhygtx.napcat.auth.BotRegistrar} 动态注册 bot 信息。
 */
@Configuration
@ConditionalOnProperty(prefix = "napcat.ws", name = "enable-token", havingValue = "true")
public class NapCatAuthConfig {

    private static final Logger log = LoggerFactory.getLogger(NapCatAuthConfig.class);

    public NapCatAuthConfig(NapCatProperties properties) {
        NapCatProperties.Ws ws = properties.getWs();
        String staticToken = ws.getTokenValue();
        if (staticToken == null || staticToken.isEmpty()) {
            //noinspection LoggingSimilarMessage
            log.warn("============================================================");
            log.warn("  Token 鉴权已启用，但未配置 napcat.ws.token-value");
            log.warn("  请选择以下方式之一：");
            log.warn("  1. 在 application.yml 中配置 napcat.ws.token-value");
            log.warn("  2. 注入 BotRegistrar 并调用 register(pathSuffix, token)");
            log.warn("  未配置前，所有连接将被拒绝");
            log.warn("============================================================");
        }
    }
}
