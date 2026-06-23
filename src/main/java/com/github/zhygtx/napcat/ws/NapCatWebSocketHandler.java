package com.github.zhygtx.napcat.ws;

import com.github.zhygtx.napcat.NapCatConstants;
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

    private final BotSessionRegistry botRegistry;
    private final EventDispatcher eventDispatcher;

    public NapCatWebSocketHandler(BotSessionRegistry botRegistry, EventDispatcher eventDispatcher) {
        this.botRegistry = botRegistry;
        this.eventDispatcher = eventDispatcher;
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
     * 更新该 Bot 的最后消息时间以供心跳超时检测使用，然后将事件提交到 Dispatcher。
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        log.debug("[文本消息] 会话: {} | 内容: {}", session.getId(), payload);

        // 获取 BotQQ，更新心跳时间，并分发事件
        Long botQQ = (Long) session.getAttributes().get(NapCatConstants.ATTR_BOT_QQ);
        if (botQQ != null) {
            botRegistry.updateLastMessageTime(botQQ);
            eventDispatcher.dispatch(botQQ, payload);
        } else {
            // 如果尚未从 attributes 中获取到 botQQ（例如心跳等连接初始化阶段的消息），
            // 尝试从消息本身的 self_id 字段解析
            eventDispatcher.dispatch(null, payload);
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
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session,@NonNull CloseStatus status) {
        Long botQQ = (Long) session.getAttributes().get(NapCatConstants.ATTR_BOT_QQ);
        if (botQQ != null) {
            botRegistry.remove(botQQ);
        }
        log.info("连接关闭 | BotQQ: {} | 会话: {} | 状态码: {} | 关闭原因: {}",
                botQQ, session.getId(), status.getCode(), status.getReason());
    }

    /**
     * 处理传输错误。
     */
    @Override
    public void handleTransportError(WebSocketSession session,@NonNull Throwable exception) {
        log.error("传输错误 | 会话: {} | 错误: {}", session.getId(), exception.getMessage(), exception);
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
