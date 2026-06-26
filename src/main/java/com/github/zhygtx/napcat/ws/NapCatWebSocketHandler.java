package com.github.zhygtx.napcat.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zhygtx.napcat.NapCatConstants;
import com.github.zhygtx.napcat.api.NapCatApiClient;
import com.github.zhygtx.napcat.event.EventDispatcher;
import com.github.zhygtx.napcat.session.Bot;
import com.github.zhygtx.napcat.session.BotSessionRegistry;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * NapCat WebSocket 处理器。
 * <p>
 * 负责处理 NapCat 客户端的 WebSocket 连接生命周期：
 * <ul>
 *   <li>连接建立 → 从 attributes 创建 {@link Bot} 并注册到 {@link BotSessionRegistry}</li>
 *   <li>消息收发 → 每条消息到达时更新 {@link Bot #lastMessageTime} 用于心跳检测</li>
 *   <li>连接关闭 → 从 {@link BotSessionRegistry} 中移除 Bot</li>
 *   <li>传输错误 → 记录错误日志</li>
 * </ul>
 */
@Component
public class NapCatWebSocketHandler extends AbstractWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(NapCatWebSocketHandler.class);

    private static final ObjectMapper SHARED_MAPPER = new ObjectMapper();

    private final BotSessionRegistry botRegistry;
    private final EventDispatcher eventDispatcher;
    private final NapCatApiClient apiClient;

    public NapCatWebSocketHandler(BotSessionRegistry botRegistry,
                                  EventDispatcher eventDispatcher,
                                  NapCatApiClient apiClient) {
        this.botRegistry = botRegistry;
        this.eventDispatcher = eventDispatcher;
        this.apiClient = apiClient;
    }

    /**
     * 处理连接建立。
     */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        logConnectionDetails(session);

        Bot bot = Bot.fromSessionAttributes(session, session.getAttributes());
        if (bot != null) {
            botRegistry.register(bot);
        } else {
            log.warn("连接缺少 {} 请求头，无法识别 BotQQ，会话不会被注册",
                    NapCatConstants.HEADER_X_SELF_ID);
        }
    }

    /**
     * 处理文本消息。
     * <p>
     * 一次 JSON 解析后根据消息类型分流：
     * <ul>
     *   <li>含有 {@code echo} 字段 → API 响应 → {@link NapCatApiClient#handleResponse(JsonNode)}</li>
     *   <li>含有 {@code post_type} 字段 → 事件上报 → {@link EventDispatcher#dispatch(Long, String, JsonNode)}</li>
     * </ul>
     * 更新该 Bot 的最后消息时间后执行分流处理。
     */
    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        String sessionId = session.getId();
        Long botQQ = (Long) session.getAttributes().get(NapCatConstants.ATTR_BOT_QQ);

        try {
            JsonNode json = SHARED_MAPPER.readTree(payload);

            if (botQQ != null) {
                botRegistry.updateLastMessageTime(botQQ);
            }

            // 消息分流：API 响应 or 事件上报
            if (json.has("echo") && json.has("status")) {
                // API 响应
                apiClient.handleResponse(json);
            } else if (json.has("post_type")) {
                // 事件上报：Dispatcher 内部提交到线程池异步执行
                eventDispatcher.dispatch(botQQ, sessionId, json);
            } else {
                log.warn("无法识别的消息类型 | 会话: {} | 内容前200字符: {}", sessionId,
                        payload.substring(0, Math.min(payload.length(), 200)));
            }

        } catch (Exception e) {
            log.error("消息处理异常 | 会话: {}", sessionId, e);
        }
    }

    /**
     * 处理二进制消息。
     * <p>
     * 同样更新 Bot 的最后消息时间。
     */
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        byte[] data = new byte[message.getPayload().remaining()];
        message.getPayload().get(data);
        log.debug("[二进制消息] 会话: {} | 大小: {} bytes", session.getId(), data.length);

        // 二进制消息也更新心跳时间
        Long botQQ = (Long) session.getAttributes().get(NapCatConstants.ATTR_BOT_QQ);
        if (botQQ != null) {
            botRegistry.updateLastMessageTime(botQQ);
        }
    }

    /**
     * 处理连接关闭。
     * <p>
     * 验证关闭的 session 与注册中心当前 session 一致后才移除 Bot，
     * 防止旧连接的关闭回调误删已由新连接替换的 Bot 注册信息。
     * <p>
     * 场景：同一 BotQQ 建立新连接时，register() 会关闭旧连接，
     * 旧连接的 afterConnectionClosed 回调不应影响新连接的注册状态。
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) {
        Long botQQ = (Long) session.getAttributes().get(NapCatConstants.ATTR_BOT_QQ);
        String sessionId = session.getId();
        if (botQQ != null) {
            Bot currentBot = botRegistry.getBot(botQQ);
            // 仅当关闭的 session 是注册中心当前记录的 session 时才执行移除
            if (currentBot != null
                    && currentBot.getSession() != null
                    && currentBot.getSession().getId().equals(sessionId)) {
                botRegistry.remove(botQQ);
                apiClient.cancelPendingRequestsForBot(botQQ);
            } else {
                log.debug("连接关闭但 Bot [{}] 已有新会话，跳过移除 | 关闭会话: {} | 当前会话: {}",
                        botQQ, sessionId,
                        currentBot != null && currentBot.getSession() != null
                                ? currentBot.getSession().getId() : "null");
            }
        }
        log.info("连接关闭 | BotQQ: {} | 会话: {} | 状态码: {} | 关闭原因: {}",
                botQQ, sessionId, status.getCode(), status.getReason());
    }

    /**
     * 处理传输错误。
     */
    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) {
        if (isClosedChannel(exception)) {
            log.warn("连接通道已关闭 | 会话: {} | {}", session.getId(), exception.getMessage());
        } else {
            log.error("传输错误 | 会话: {} | 错误: {}", session.getId(), exception.getMessage(), exception);
        }
    }

    private boolean isClosedChannel(Throwable exception) {
        Throwable cause = exception;
        while (cause != null) {
            String name = cause.getClass().getSimpleName();
            if ("ClosedChannelException".equals(name)) return true;
            cause = cause.getCause();
        }
        return false;
    }

    /**
     * 打印新连接的详细诊断信息。
     */
    private void logConnectionDetails(WebSocketSession session) {
        log.info("========== 新 WebSocket 连接已建立 ==========");
        log.info("会话 ID: {}", session.getId());
        log.info("远程地址: {}", session.getRemoteAddress());
        log.info("本地地址: {}", session.getLocalAddress());
        log.info("连接 URI: {}", session.getUri());
        log.info("------ 请求头 ------");
        session.getHandshakeHeaders().forEach((key, value) ->
                log.info("  {}: {}", key, value));
        log.info("------ Session Attributes ------");
        session.getAttributes().forEach((key, value) ->
                log.info("  {} = {}", key, value));
        log.info("==============================================");
    }
}
