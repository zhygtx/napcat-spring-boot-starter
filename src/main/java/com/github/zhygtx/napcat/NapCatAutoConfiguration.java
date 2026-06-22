package com.github.zhygtx.napcat;

import com.github.zhygtx.napcat.config.NapCatProperties;
import com.github.zhygtx.napcat.config.NapCatWebSocketConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties(NapCatProperties.class)
@Import(NapCatWebSocketConfig.class)
public class NapCatAutoConfiguration {
}