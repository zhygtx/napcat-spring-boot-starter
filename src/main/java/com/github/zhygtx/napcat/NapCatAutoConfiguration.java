package com.github.zhygtx.napcat;

import com.github.zhygtx.napcat.auth.BotRegistrar;
import com.github.zhygtx.napcat.auth.TokenAuthInterceptor;
import com.github.zhygtx.napcat.config.NapCatProperties;
import com.github.zhygtx.napcat.config.NapCatWebSocketConfig;
import com.github.zhygtx.napcat.session.BotSessionRegistry;
import com.github.zhygtx.napcat.ws.NapCatWebSocketHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties(NapCatProperties.class)
@Import({
        NapCatWebSocketConfig.class,
        NapCatWebSocketHandler.class,
        TokenAuthInterceptor.class,
        BotRegistrar.class,
        BotSessionRegistry.class
})
public class NapCatAutoConfiguration {
}
