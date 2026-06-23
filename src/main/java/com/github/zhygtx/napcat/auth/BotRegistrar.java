package com.github.zhygtx.napcat.auth;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bot 连接注册管理器，提供注册信息的增删改查。
 * <p>
 * 管理的是连接注册信息（路径后缀 → token 映射），而非 Bot 在线状态。
 * 删除或修改注册信息后，已被踢出的连接将无法重连；
 * 已建立的连接不受影响，需配合 {@code BotSessionRegistry} 关闭会话。
 * <p>
 * 使用示例：
 * <pre>{@code
 * @Component
 * public class MyBotInit {
 *     @Autowired
 *     private BotRegistrar botRegistrar;
 *
 *     @PostConstruct
 *     public void init() {
 *         botRegistrar.register("123456", "token-for-123");
 *         botRegistrar.register("789012", null);  // 该 bot 不需要 token 校验
 *     }
 * }
 * }</pre>
 */
@Component
public class BotRegistrar {

    /**
     * 存储已注册的 bot 连接路径后缀和 token 的并发映射表。
     */
    private final ConcurrentHashMap<String, String> registrations = new ConcurrentHashMap<>();

    // ==================== 增 ====================

    /**
     * 注册一个 bot 连接路径后缀及其可选的 token。
     *
     * @param pathSuffix 基础 URL 之后的路径后缀（例如 "123456" 对应 /ws/bot/123456）
     * @param token      可选 token，为 null 表示该 bot 不需要 token 校验
     */
    public void register(String pathSuffix, String token) {
        registrations.put(pathSuffix, token);
    }

    // ==================== 删 ====================

    /**
     * 撤销一个 bot 连接路径后缀的注册。
     * <p>
     * 撤销后该路径后缀对应的连接将被拒绝握手。
     * 已建立的连接不受影响，但断开后无法重连。
     *
     * @param pathSuffix 要撤销的路径后缀
     * @return 被撤销的 token，如果该后缀未注册则返回 null
     */
    public String unregister(String pathSuffix) {
        return registrations.remove(pathSuffix);
    }

    // ==================== 改 ====================

    /**
     * 更新指定路径后缀的 token。
     * <p>
     * 如果该路径后缀未注册，则自动注册（等同于 {@link #register}）。
     * 已建立的连接不受影响，但重连时需使用新 token。
     *
     * @param pathSuffix 路径后缀
     * @param newToken   新 token，为 null 表示不需要 token 校验
     * @return 旧的 token，如果之前未注册则返回 null
     */
    public String updateToken(String pathSuffix, String newToken) {
        return registrations.put(pathSuffix, newToken);
    }

    // ==================== 查 ====================

    /**
     * 获取所有已注册的连接信息（只读副本）。
     *
     * @return 不可变的 pathSuffix → token 映射
     */
    public Map<String, String> getAllRegistrations() {
        return Collections.unmodifiableMap(new ConcurrentHashMap<>(registrations));
    }

    // === 以下为包级可见方法，供 TokenAuthInterceptor 使用 ===

    boolean hasRegistration(String pathSuffix) {
        return registrations.containsKey(pathSuffix);
    }

    String getToken(String pathSuffix) {
        return registrations.get(pathSuffix);
    }

    boolean isEmpty() {
        return registrations.isEmpty();
    }
}
