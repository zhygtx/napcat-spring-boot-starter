package com.github.zhygtx.napcat.config;

import com.github.zhygtx.napcat.auth.TokenAuthInterceptor;
import com.github.zhygtx.napcat.ws.NapCatWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * WebSocket 配置。
 * <p>
 * 注册 WebSocket 处理器与鉴权拦截器，并配置传输层参数：
 * <ul>
 *   <li>{@code maxSessionIdleTimeout} — 会话空闲超时（napcat.ws.timeout）</li>
 *   <li>{@code maxTextMessageBufferSize} — 文本消息最大缓冲（napcat.ws.max-text-buffer-size）</li>
 *   <li>{@code maxBinaryMessageBufferSize} — 二进制消息最大缓冲（napcat.ws.max-binary-buffer-size）</li>
 * </ul>
 * 仅当 {@code napcat.ws.server.enable=true}（默认值）时生效。
 */
@Configuration
@EnableWebSocket
@ConditionalOnProperty(prefix = "napcat.ws.server", name = "enable", havingValue = "true", matchIfMissing = true)
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
        String path = wsConfig.getServer().getUrl() + "/**";

        registry.addHandler(handler, path)
                .addInterceptors(tokenAuthInterceptor)
                .setAllowedOrigins("*");

        log.info("WebSocket 注册完成, path={}", path);
    }

    /**
     * 配置 WebSocket 容器级别的传输参数。
     * <p>
     * 这些参数影响所有通过本容器建立的 WebSocket 连接：
     * <ul>
     *   <li>会话空闲超时 — 对应配置 napcat.ws.timeout</li>
     *   <li>文本消息缓冲上限 — 对应配置 napcat.ws.max-text-buffer-size</li>
     *   <li>二进制消息缓冲上限 — 对应配置 napcat.ws.max-binary-buffer-size</li>
     * </ul>
     */
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        NapCatProperties.Ws ws = properties.getWs();
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();

        container.setMaxSessionIdleTimeout((long) ws.getTimeout() * 1000);
        container.setMaxTextMessageBufferSize(ws.getMaxTextBufferSize());
        container.setMaxBinaryMessageBufferSize(ws.getMaxBinaryBufferSize());

        log.info("WebSocket 容器配置: idleTimeout={}s, textBuffer={}, binaryBuffer={}",
                ws.getTimeout(), ws.getMaxTextBufferSize(), ws.getMaxBinaryBufferSize());

        return container;
    }
}
