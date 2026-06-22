package com.github.zhygtx.napcat.config;

import com.github.zhygtx.napcat.session.BotSessionRegistry;
import com.github.zhygtx.napcat.ws.NapCatWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class NapCatWebSocketConfig implements WebSocketConfigurer {

    private final NapCatProperties properties;
    private final NapCatWebSocketHandler handler;

    // 通过构造函数注入配置和 Handler
    public NapCatWebSocketConfig(NapCatProperties properties, NapCatWebSocketHandler handler) {
        this.properties = properties;
        this.handler = handler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        NapCatProperties.Ws wsConfig = properties.getWs();
        String basePath = wsConfig.getUrl();
        
        registry.addHandler(handler, basePath)
                .setAllowedOrigins("*");
    }
}
