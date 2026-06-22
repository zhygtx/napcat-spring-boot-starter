package com.github.zhygtx.napcat.config;

import com.github.zhygtx.napcat.auth.TokenAuthInterceptor;
import com.github.zhygtx.napcat.ws.NapCatWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket 配置。
 * <p>
 * 注册 WebSocket 处理器与鉴权拦截器，并在启用 Token 鉴权但未配置静态 token 时输出警告。
 */
@Configuration
@EnableWebSocket
public class NapCatWebSocketConfig implements WebSocketConfigurer {

    private static final Logger log = LoggerFactory.getLogger(NapCatWebSocketConfig.class);

    private final NapCatProperties properties;
    private final NapCatWebSocketHandler handler;
    private final TokenAuthInterceptor tokenAuthInterceptor;

    public NapCatWebSocketConfig(NapCatProperties properties, NapCatWebSocketHandler handler,
                                 TokenAuthInterceptor tokenAuthInterceptor) {
        this.properties = properties;
        this.handler = handler;
        this.tokenAuthInterceptor = tokenAuthInterceptor;
        warnIfTokenEnabledWithoutConfig();
    }

    private void warnIfTokenEnabledWithoutConfig() {
        NapCatProperties.Ws ws = properties.getWs();
        if (!ws.isEnableToken()) {
            return;
        }
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

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        NapCatProperties.Ws wsConfig = properties.getWs();
        registry.addHandler(handler, wsConfig.getUrl() + "/**")
                .addInterceptors(tokenAuthInterceptor)
                .setAllowedOrigins("*");
    }
}
