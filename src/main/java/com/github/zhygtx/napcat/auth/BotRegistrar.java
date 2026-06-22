package com.github.zhygtx.napcat.auth;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Bot 注册管理器。
 * <p>
 * 用户可注入此类，调用 {@link #register(String, String)} 逐个注册每个 bot 的链接后缀和 token。
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

    /**
     * 注册一个 bot 连接路径后缀及其可选的 token。
     *
     * @param pathSuffix 基础 URL 之后的路径后缀（例如 "123456" 对应 /ws/bot/123456）
     * @param token      可选 token，为 null 表示该 bot 不需要 token 校验
     */
    public void register(String pathSuffix, String token) {
        registrations.put(pathSuffix, token);
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
