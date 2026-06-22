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

/**
 * NapCat WebSocket 配置
 *
 * <p>负责将 {@link NapCatWebSocketHandler} 注册到路径 "/ws/bot/{botQQ}/{secretKey}" 上。
 *
 * <p>注意：Spring WebSocket 原生不支持像 {@code @PathVariable} 那样自动提取路径模板变量，
 * 所以我们用通配符路径 "{botQQ}" 和 "{secretKey}" 作为占位符，实际解析在 Handler 中
 * 通过 {@code session.getUri().getPath()} 手动提取。
 */
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
        
        if (wsConfig.isSecretKeyIsolation()) {
            basePath += "/*";  // /ws/bot/{secretKey}
        } 
        if (wsConfig.isAccountIsolation()) {
            basePath += "/*";  // /ws/bot/{botQQ}
        }
        
        registry.addHandler(handler, basePath)
                .setAllowedOrigins("*");
    }
}
