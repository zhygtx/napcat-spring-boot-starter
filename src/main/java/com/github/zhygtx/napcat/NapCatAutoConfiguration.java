package com.github.zhygtx.napcat;

import com.github.zhygtx.napcat.api.NapCat;
import com.github.zhygtx.napcat.api.NapCatApiClient;
import com.github.zhygtx.napcat.auth.BotRegistrar;
import com.github.zhygtx.napcat.auth.TokenAuthInterceptor;
import com.github.zhygtx.napcat.config.NapCatProperties;
import com.github.zhygtx.napcat.config.NapCatWebSocketConfig;
import com.github.zhygtx.napcat.event.EventDispatcher;
import com.github.zhygtx.napcat.event.EventExecutor;
import com.github.zhygtx.napcat.session.BotSessionRegistry;
import com.github.zhygtx.napcat.util.EventLogger;
import com.github.zhygtx.napcat.util.LogSanitizer;
import com.github.zhygtx.napcat.util.NapCatObjectMapper;
import com.github.zhygtx.napcat.ws.HeartbeatMonitor;
import com.github.zhygtx.napcat.ws.NapCatWebSocketHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@AutoConfiguration
@EnableConfigurationProperties(NapCatProperties.class)
@EnableScheduling
@Import({
        NapCatWebSocketConfig.class,
        NapCatWebSocketHandler.class,
        HeartbeatMonitor.class,
        TokenAuthInterceptor.class,
        BotRegistrar.class,
        BotSessionRegistry.class,
        EventDispatcher.class,
        EventExecutor.class,
        LogSanitizer.class,
        NapCatObjectMapper.class,
        NapCatApiClient.class,
        NapCat.class,
        EventLogger.class
})
public class NapCatAutoConfiguration {
}
