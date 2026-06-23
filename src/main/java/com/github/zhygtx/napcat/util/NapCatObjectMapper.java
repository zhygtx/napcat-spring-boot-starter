package com.github.zhygtx.napcat.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.stereotype.Component;

/**
 * NapCat SDK 共享的 ObjectMapper 实例。
 * <p>
 * 统一配置 snake_case 命名策略，用于 JSON 序列化与反序列化。
 * 注入到 {@link com.github.zhygtx.napcat.event.EventDispatcher EventDispatcher}、
 * {@link com.github.zhygtx.napcat.api.NapCatApiClient NapCatApiClient} 等组件中，
 * 避免重复创建 ObjectMapper 实例。
 */
@Component("napCatObjectMapper")
public class NapCatObjectMapper extends ObjectMapper {

    public NapCatObjectMapper() {
        super();
        // OneBot 协议使用下划线命名
        this.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }
}
