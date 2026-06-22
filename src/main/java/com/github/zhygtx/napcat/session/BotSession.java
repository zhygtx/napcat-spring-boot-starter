package com.github.zhygtx.napcat.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.time.Instant;

/**
 * Bot 会话封装
 *
 * 存储单个 Bot 的连接信息和状态
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotSession {
    
    /**
     * Bot QQ 号
     */
    private Long botQQ;
    
    /**
     * WebSocket 会话对象
     */
    private WebSocketSession session;
    
    /**
     * 连接时提供的密钥（如果启用了密钥鉴权）
     */
    private String secretKey;
    
    /**
     * 最后收到消息的时间（用于心跳超时检测）
     */
    private Instant lastMessageTime;
    
    /**
     * 连接建立时间
     */
    private Instant connectTime;
}