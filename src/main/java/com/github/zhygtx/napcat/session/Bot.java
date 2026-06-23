package com.github.zhygtx.napcat.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.zhygtx.napcat.NapCatConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.time.Instant;
import java.util.Map;

/**
 * Bot 会话封装。
 * <p>
 * 存储单个 Bot 的连接信息和状态，并提供从握手 attributes 创建实例的工厂方法。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bot {

    /** Bot QQ 号 */
    private Long botQQ;

    /** WebSocket 会话对象 */
    private WebSocketSession session;

    /** 连接时提供的密钥 */
    private String token;

    /** 连接时提供的路径后缀 */
    private String pathSuffix;

    /** 最后收到消息的时间（用于心跳超时检测） */
    private Instant lastMessageTime;

    /** 连接建立时间 */
    private Instant connectTime;

    /**
     * session 发送专用锁。
     * <p>
     * WebSocketSession 本身不是线程安全的，多个线程并发调用 sendMessage 会导致
     * 帧数据交错或 IllegalStateException。API 客户端发送请求前需持有此锁。
     */
    @JsonIgnore
    private final transient Object sendLock = new Object();

    /**
     * 从 WebSocket 握手 attributes 创建 Bot 实例。
     * <p>
     * 自动设置 connectTime 和 lastMessageTime 为当前时间。
     *
     * @param session    WebSocket 会话
     * @param attributes 握手阶段存入的 attributes 映射
     * @return 构造好的 Bot 实例，若 attributes 中无 bot_qq 则返回 null
     */
    public static Bot fromSessionAttributes(WebSocketSession session, Map<String, Object> attributes) {
        Long botQQ = (Long) attributes.get(NapCatConstants.ATTR_BOT_QQ);
        if (botQQ == null) {
            return null;
        }
        Bot bot = new Bot();
        bot.setBotQQ(botQQ);
        bot.setSession(session);
        bot.setToken((String) attributes.get(NapCatConstants.ATTR_TOKEN));
        bot.setPathSuffix((String) attributes.get(NapCatConstants.ATTR_PATH_SUFFIX));
        Instant now = Instant.now();
        bot.setConnectTime(now);
        bot.setLastMessageTime(now);
        return bot;
    }
}
