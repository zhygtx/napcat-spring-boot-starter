package com.github.zhygtx.napcat.auth;

import com.github.zhygtx.napcat.NapCatConstants;
import com.github.zhygtx.napcat.config.NapCatProperties;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * WebSocket 握手拦截器，负责提取 Bot 身份信息并进行 Token 鉴权。
 * <p>
 * 工作流程：
 * <ol>
 *   <li>提取请求头 {@code x-self-id} 作为 BotQQ</li>
 *   <li>提取请求头 {@code Authorization: Bearer <token>}</li>
 *   <li>未启用 Token 校验 → 直接放行</li>
 *   <li>静态模式（配置了 token-value）→ 比对 token</li>
 *   <li>动态模式（{@link BotRegistrar} 有注册记录）→ 按路径后缀查找并校验</li>
 *   <li>前述均不满足 → 拒绝握手</li>
 * </ol>
 * <p>
 * 提取的 attributes 存入 {@link NapCatConstants} 中定义的 key。
 */
@Component
public class TokenAuthInterceptor implements HandshakeInterceptor {

    private static final Logger log = LoggerFactory.getLogger(TokenAuthInterceptor.class);

    private final NapCatProperties properties;
    private final BotRegistrar botRegistrar;

    public TokenAuthInterceptor(NapCatProperties properties, BotRegistrar botRegistrar) {
        this.properties = properties;
        this.botRegistrar = botRegistrar;
    }

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                   @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) {
        extractIdentity(request, attributes);

        if (!properties.getWs().isEnableToken()) {
            return true;
        }

        String token = extractBearerToken(request);
        if (token == null) {
            reject(response, "Missing or invalid Authorization header");
            return false;
        }
        attributes.put(NapCatConstants.ATTR_TOKEN, token);

        if (!validateToken(token, attributes)) {
            reject(response, "Invalid token");
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                               @NonNull WebSocketHandler wsHandler, Exception exception) {
        // 无需额外处理
    }

    /**
     * 提取 Bot 身份信息并存入 attributes。
     */
    private void extractIdentity(ServerHttpRequest request, Map<String, Object> attributes) {
        extractXSelfId(request, attributes);
        extractPathSuffix(request.getURI(), properties.getWs().getUrl(), attributes);
    }

    /**
     * 从请求头中提取 x-self-id（BotQQ）。
     */
    private void extractXSelfId(ServerHttpRequest request, Map<String, Object> attributes) {
        List<String> values = request.getHeaders().get(NapCatConstants.HEADER_X_SELF_ID);
        if (values != null && !values.isEmpty()) {
            try {
                attributes.put(NapCatConstants.ATTR_BOT_QQ, Long.parseLong(values.getFirst()));
            } catch (NumberFormatException e) {
                log.warn("x-self-id 格式错误: {}", values.getFirst());
            }
        }
    }

    /**
     * 从 URI 中提取路径后缀并存入 attributes。
     */
    private void extractPathSuffix(URI uri, String basePath, Map<String, Object> attributes) {
        String path = uri.getPath();
        if (path != null && path.startsWith(basePath + "/")) {
            attributes.put(NapCatConstants.ATTR_PATH_SUFFIX, path.substring(basePath.length() + 1));
        }
    }

    /**
     * 从请求头提取 Bearer token。
     *
     * @return token 字符串，若缺失或格式错误则返回 null
     */
    private String extractBearerToken(ServerHttpRequest request) {
        List<String> authHeaders = request.getHeaders().get(NapCatConstants.HEADER_AUTHORIZATION);
        if (authHeaders == null || authHeaders.isEmpty()) {
            log.warn("握手拒绝：缺少 Authorization 请求头");
            return null;
        }
        String authValue = authHeaders.getFirst();
        if (!authValue.startsWith(NapCatConstants.BEARER_PREFIX)) {
            log.warn("握手拒绝：Authorization 格式错误，期望 Bearer <token>，实际: {}", authValue);
            return null;
        }
        return authValue.substring(NapCatConstants.BEARER_PREFIX.length());
    }

    /**
     * 验证 token 是否合法。
     * 优先使用静态 token 配置，其次使用 BotRegistrar 动态注册表。
     */
    private boolean validateToken(String token, Map<String, Object> attributes) {
        // 静态模式
        String staticToken = properties.getWs().getTokenValue();
        if (staticToken != null && !staticToken.isEmpty()) {
            if (staticToken.equals(token)) {
                return true;
            }
            log.warn("握手拒绝：静态 token 不匹配");
            return false;
        }

        // 动态模式
        String pathSuffix = (String) attributes.get(NapCatConstants.ATTR_PATH_SUFFIX);
        if (pathSuffix != null && botRegistrar.hasRegistration(pathSuffix)) {
            String expectedToken = botRegistrar.getToken(pathSuffix);
            if (expectedToken == null || expectedToken.equals(token)) {
                return true;
            }
            log.warn("握手拒绝：bot [{}] token 不匹配", pathSuffix);
            return false;
        }

        log.warn("握手拒绝：未找到 bot [{}] 的注册信息，且未配置静态 token", pathSuffix);
        return false;
    }

    private void reject(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        try {
            response.getBody().write(message.getBytes());
        } catch (Exception ignored) {
            // 写入失败忽略
        }
    }
}
