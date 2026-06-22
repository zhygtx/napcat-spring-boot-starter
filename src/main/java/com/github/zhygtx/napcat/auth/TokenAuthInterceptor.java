package com.github.zhygtx.napcat.auth;

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
 * WebSocket 握手拦截器，负责 Token 鉴权。
 * <p>
 * 工作流程：
 * <ol>
 *   <li>{@code enableToken=false} → 直接放行</li>
 *   <li>提取 {@code Authorization: Bearer <token>} 请求头，缺失或格式错误 → 401</li>
 *   <li>静态模式（配置了 {@code token-value}）→ 比对 token，不匹配 → 401</li>
 *   <li>动态模式（{@link BotRegistrar} 有注册记录）→ 通过路径后缀查找对应 bot，校验 token</li>
 *   <li>前述均不满足 → 401</li>
 * </ol>
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

    /**
     * 在握手之前执行，用于进行 Token 鉴权。
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param wsHandler WebSocket 处理器
     * @param attributes 握手属性
     * @return 如果返回 {@code true}，则继续握手过程；如果返回 {@code false}，则中断握手过程。
     */
    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                   @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) {
        // 1. 未启用 token 校验，直接放行
        if (!properties.getWs().isEnableToken()) {
            return true;
        }

        String basePath = properties.getWs().getUrl();

        // 2. 提取 Authorization 请求头
        List<String> authHeaders = request.getHeaders().get("Authorization");
        if (authHeaders == null || authHeaders.isEmpty()) {
            log.warn("握手拒绝：缺少 Authorization 请求头");
            reject(response, "Missing Authorization header");
            return false;
        }

        String authValue = authHeaders.getFirst();
        if (!authValue.startsWith("Bearer ")) {
            log.warn("握手拒绝：Authorization 格式错误，期望 Bearer <token>，实际: {}", authValue);
            reject(response, "Invalid Authorization format, expected: Bearer <token>");
            return false;
        }

        String token = authValue.substring(7);

        // 3. 静态模式：比对 token-value
        String staticToken = properties.getWs().getTokenValue();
        if (staticToken != null && !staticToken.isEmpty()) {
            if (staticToken.equals(token)) {
                // 放行，并将路径后缀存入 session 属性
                extractAndPutPathSuffix(request.getURI(), basePath, attributes);
                return true;
            }
            log.warn("握手拒绝：静态 token 不匹配");
            reject(response, "Invalid token");
            return false;
        }

        // 4. 动态模式：查询 BotRegistrar 注册表
        String pathSuffix = extractPathSuffix(request.getURI(), basePath);
        if (pathSuffix != null && botRegistrar.hasRegistration(pathSuffix)) {
            String expectedToken = botRegistrar.getToken(pathSuffix);
            if (expectedToken == null || expectedToken.equals(token)) {
                attributes.put("bot_path_suffix", pathSuffix);
                return true;
            }
            log.warn("握手拒绝：bot [{}] token 不匹配", pathSuffix);
            reject(response, "Invalid token");
            return false;
        }

        // 5. 无法通过任何方式验证
        log.warn("握手拒绝：未找到 bot [{}] 的注册信息，且未配置静态 token", pathSuffix);
        reject(response, "Unauthorized: no matching bot registration or static token");
        return false;
    }


    /**
     * WebSocket握手完成后的回调方法
     * 该方法在WebSocket握手成功后调用，用于进行握手的后续处理
     * @param request 当前的HTTP请求对象，包含握手请求的相关信息
     * @param response 当前的HTTP响应对象，用于向客户端返回握手响应
     * @param wsHandler WebSocket处理器，用于处理WebSocket消息的交互
     * @param exception 握手过程中可能发生的异常对象，如果握手成功则为null
     */
    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                               @NonNull WebSocketHandler wsHandler, Exception exception) {
        // 无需额外处理
    // 该实现中不需要进行任何额外操作，保持空实现
    }

    /**
    * 拒绝请求并返回未授权响应
    * @param response 服务器响应对象
    * @param message 要返回的错误消息
    */
    private void reject(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        try {
            response.getBody().write(message.getBytes());
        } catch (Exception ignored) {
        }
    }

    /**
     * 从URI中提取路径后缀
     * @param uri 要处理的URI对象
     * @param basePath 基础路径
     * @return 返回基础路径之后的部分，如果不匹配则返回null
     */
    private String extractPathSuffix(URI uri, String basePath) {
        String path = uri.getPath();
        if (path != null && path.startsWith(basePath + "/")) {
            return path.substring(basePath.length() + 1);
        }
        return null;
    }

    /**
     * 从URI中提取路径后缀并将其添加到属性映射中
     * @param uri 要处理的URI对象
     * @param basePath 基础路径，用于确定后缀的起始位置
     * @param attributes 属性映射，用于存储提取的后缀
     */
    private void extractAndPutPathSuffix(URI uri, String basePath, Map<String, Object> attributes) {
        String suffix = extractPathSuffix(uri, basePath);
        if (suffix != null) {
            attributes.put("bot_path_suffix", suffix);
        }
    }
}
