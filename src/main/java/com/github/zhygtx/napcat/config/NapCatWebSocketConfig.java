package com.github.zhygtx.napcat.config;

import com.github.zhygtx.napcat.auth.TokenAuthInterceptor;
import com.github.zhygtx.napcat.ws.NapCatWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class NapCatWebSocketConfig implements WebSocketConfigurer {

    private final NapCatProperties properties;
    private final NapCatWebSocketHandler handler;
    private final TokenAuthInterceptor tokenAuthInterceptor;

    public NapCatWebSocketConfig(NapCatProperties properties, NapCatWebSocketHandler handler,
                                 TokenAuthInterceptor tokenAuthInterceptor) {
        this.properties = properties;
        this.handler = handler;
        this.tokenAuthInterceptor = tokenAuthInterceptor;
    }

    /**
     * 注册 WebSocket 处理器，添加 Token 鉴权拦截器。
     * @param registry WebSocket 处理器注册器
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        NapCatProperties.Ws wsConfig = properties.getWs();
        String basePath = wsConfig.getUrl();

        registry.addHandler(handler, basePath + "/**")
                .addInterceptors(tokenAuthInterceptor)
                .setAllowedOrigins("*");
    }
}
