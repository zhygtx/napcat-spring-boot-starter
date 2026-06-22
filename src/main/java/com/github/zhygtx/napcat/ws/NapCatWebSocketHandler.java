package com.github.zhygtx.napcat.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * NapCat WebSocket 处理器
 * <p>
 * 负责处理来自 NapCat 客户端的 WebSocket 连接。
 * 当前实现：
 *  - 打印握手信息（请求头、URI、参数等）
 *  - 打印收到的文本/二进制消息内容
 *  - 打印连接关闭和传输错误日志
 */
@Component
public class NapCatWebSocketHandler extends AbstractWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(NapCatWebSocketHandler.class);

    /**
     * 当 WebSocket 连接建立成功时调用
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        log.info("========== 新 WebSocket 连接已建立 ==========");
        log.info("会话 ID: {}", session.getId());
        log.info("远程地址: {}", session.getRemoteAddress());
        log.info("本地地址: {}", session.getLocalAddress());
        log.info("连接 URI: {}", session.getUri());
        log.info("------ 请求头 ------");
        session.getHandshakeHeaders().forEach((key, value) ->
                log.info("  {}: {}", key, value)
        );
        log.info("------ 路径参数 ------");
        session.getAttributes().forEach((key, value) ->
                log.info("  {} = {}", key, value)
        );
        log.info("==========================================");
    }

    /**
     * 当收到文本消息时调用
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message){
        log.info("[文本消息] 会话: {} | 内容: {}", session.getId(), message.getPayload());
    }

    /**
     * 当收到二进制消息时调用
     */
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message){
        byte[] data = new byte[message.getPayload().remaining()];
        message.getPayload().get(data);
        log.info("[二进制消息] 会话: {} | 大小: {} bytes | 内容(hex): {}", session.getId(), data.length, bytesToHex(data));
    }

    /**
     * 当连接关闭时调用
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        log.info("连接关闭 | 会话: {} | 状态码: {} | 原因: {}", session.getId(), status.getCode(), status.getReason());
    }

    /**
     * 当传输错误时调用
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("传输错误 | 会话: {} | 错误: {}", session.getId(), exception.getMessage(), exception);
    }

    /**
     * 将字节数组转换为十六进制字符串（用于打印二进制消息）
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}
