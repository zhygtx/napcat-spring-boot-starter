package com.github.zhygtx.napcat.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;

/**
 * NapCat SDK 共享的 ObjectMapper 包装器。
 * <p>
 * 封装 {@link ObjectMapper}，统一配置 snake_case 命名策略并注册 JavaTimeModule。
 * 注入到 {@link com.github.zhygtx.napcat.event.EventDispatcher EventDispatcher}、
 * {@link com.github.zhygtx.napcat.api.NapCatApiClient NapCatApiClient} 等组件中。
 * <p>
 * 注意：本类不再继承 {@link ObjectMapper}，避免覆盖 Spring Boot 的 Jackson 自动配置。
 */
@Component("napCatObjectMapper")
public class NapCatObjectMapper {

    private final ObjectMapper mapper;

    public NapCatObjectMapper() {
        this.mapper = new ObjectMapper();
        // OneBot 协议使用下划线命名
        this.mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        // 注册 JavaTimeModule 以支持 LocalDateTime 等 Java 8 时间类型
        this.mapper.registerModule(new JavaTimeModule());
    }

    // ==================== 委托方法 ====================

    public ObjectNode createObjectNode() {
        return mapper.createObjectNode();
    }

    public JsonNode valueToTree(Object value) {
        return mapper.valueToTree(value);
    }

    public <T> T readValue(JsonParser parser, JavaType valueType) throws java.io.IOException {
        return mapper.readValue(parser, valueType);
    }

    public <T> T treeToValue(TreeNode node, Class<T> valueType) throws com.fasterxml.jackson.core.JsonProcessingException {
        return mapper.treeToValue(node, valueType);
    }

    public com.fasterxml.jackson.databind.type.TypeFactory getTypeFactory() {
        return mapper.getTypeFactory();
    }
}
