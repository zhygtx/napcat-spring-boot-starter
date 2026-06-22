package com.github.zhygtx.napcat.ws;

import com.github.zhygtx.napcat.session.Bot;
import com.github.zhygtx.napcat.session.BotSessionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.time.Instant;

/**
 * NapCat WebSocket 处理器。
 * <p>
 * 负责处理来自 NapCat 客户端的 WebSocket 连接生命周期：
 * <ul>
 *   <li>连接建立 → 读取握手阶段存入的 attributes，创建 {@link Bot} 并注册到 {@link BotSessionRegistry}</li>
 *   <li>消息收发 → 打印文本/二进制消息内容</li>
 *   <li>连接关闭 → 从 {@link BotSessionRegistry} 中移除 Bot</li>
 *   <li>传输错误 → 记录错误日志</li>
 * </ul>
 */
@Component
public class NapCatWebSocketHandler extends AbstractWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(NapCatWebSocketHandler.class);

    private final BotSessionRegistry botRegistry;

    public NapCatWebSocketHandler(BotSessionRegistry botRegistry) {
        this.botRegistry = botRegistry;
    }

    /**
     * WebSocket 连接建立成功时调用。
     * <p>
     * 从 session attributes 中读取握手段提取的 Bot 身份信息，
     * 构造 {@link Bot} 对象并注册到 {@link BotSessionRegistry}。
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("========== 新 WebSocket 连接已建立 ==========");
        log.info("会话 ID: {}", session.getId());
        log.info("远程地址: {}", session.getRemoteAddress());
        log.info("本地地址: {}", session.getLocalAddress());
        log.info("连接 URI: {}", session.getUri());
        log.info("------ 请求头 ------");
        session.getHandshakeHeaders().forEach((key, value) ->
                log.info("  {}: {}", key, value)
        );
        log.info("------ Session Attributes ------");
        session.getAttributes().forEach((key, value) ->
                log.info("  {} = {}", key, value)
        );
        log.info("==========================================");

        // 从 attributes 中读取 Bot 身份信息
        Long botQQ = (Long) session.getAttributes().get("bot_qq");
        String pathSuffix = (String) session.getAttributes().get("bot_path_suffix");
        String token = (String) session.getAttributes().get("bot_token");

        if (botQQ != null) {
            Bot bot = new Bot();
            bot.setBotQQ(botQQ);
            bot.setSession(session);
            bot.setPathSuffix(pathSuffix);
            bot.setToken(token);
            bot.setConnectTime(Instant.now());
            bot.setLastMessageTime(Instant.now());
            botRegistry.register(bot);
        } else {
            log.warn("连接缺少 x-self-id 请求头，无法识别 BotQQ，会话将不会被注册到会话管理器");
        }
    }

    /**
     * 收到文本消息时调用。
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("[文本消息] 会话: {} | 内容: {}", session.getId(), message.getPayload());
    }

    /**
     * 收到二进制消息时调用。
     */
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        byte[] data = new byte[message.getPayload().remaining()];
        message.getPayload().get(data);
        log.info("[二进制消息] 会话: {} | 大小: {} bytes | 内容(hex): {}", session.getId(), data.length, bytesToHex(data));
    }

    /**
     * 连接关闭时调用。
     * <p>
     * 从 {@link BotSessionRegistry} 中移除对应的 Bot 会话。
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long botQQ = (Long) session.getAttributes().get("bot_qq");
        if (botQQ != null) {
            botRegistry.remove(botQQ);
        }
        log.info("连接关闭 | BotQQ: {} | 会话: {} | 状态码: {} | 原因: {}",
                botQQ, session.getId(), status.getCode(), status.getReason());
    }

    /**
     * 传输错误时调用。
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("传输错误 | 会话: {} | 错误: {}", session.getId(), exception.getMessage(), exception);
    }

    /**
     * 将字节数组转换为十六进制字符串（用于打印二进制消息）。
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}
