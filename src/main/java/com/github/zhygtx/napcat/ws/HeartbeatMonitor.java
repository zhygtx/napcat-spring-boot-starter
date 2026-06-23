package com.github.zhygtx.napcat.ws;

import com.github.zhygtx.napcat.config.NapCatProperties;
import com.github.zhygtx.napcat.session.Bot;
import com.github.zhygtx.napcat.session.BotSessionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

/**
 * 心跳超时检测器。
 * <p>
 * 定时任务，每隔 5 秒扫描所有在线 Bot，检查其 {@link Bot#getLastMessageTime()} 与当前时间的差距。
 * 如果超过 {@code napcat.ws.heartbeat-timeout} 秒未收到任何消息，则认为该连接已死，
 * 主动关闭 WebSocket 会话并从注册表中移除。
 * <p>
 * 此机制用于处理 NapCat 端异常断开（进程崩溃、网络闪断等）后，服务端无法及时感知的情况。
 */
@Component
public class HeartbeatMonitor {

    private static final Logger log = LoggerFactory.getLogger(HeartbeatMonitor.class);

    private final BotSessionRegistry botRegistry;
    private final long heartbeatTimeoutSec;

    public HeartbeatMonitor(BotSessionRegistry botRegistry, NapCatProperties properties) {
        this.botRegistry = botRegistry;
        this.heartbeatTimeoutSec = properties.getWs().getHeartbeatTimeout();
    }

    /**
     * 每 5 秒执行一次心跳超时检测。
     * <p>
     * 遍历所有在线 Bot，如果 {@code now - lastMessageTime >= heartbeatTimeoutSec}，
     * 则关闭该连接并从注册表中移除。
     */
    @Scheduled(fixedRate = 5000)
    public void check() {
        if (botRegistry.getOnlineCount() == 0) {
            return;
        }

        Instant now = Instant.now();
        for (Bot bot : botRegistry.getAllBots()) {
            Instant lastMsg = bot.getLastMessageTime();
            if (lastMsg == null) continue;

            long idleSeconds = Duration.between(lastMsg, now).getSeconds();
            if (idleSeconds >= heartbeatTimeoutSec) {
                Long botQQ = bot.getBotQQ();
                log.warn("Bot [{}] 心跳超时，已 {} 秒未收到消息（阈值: {} 秒），连接将被关闭",
                        botQQ, idleSeconds, heartbeatTimeoutSec);

                // 先移除再关闭，避免注册中心中残留
                botRegistry.remove(botQQ);
                try {
                    if (bot.getSession() != null && bot.getSession().isOpen()) {
                        bot.getSession().close();
                    }
                } catch (Exception e) {
                    log.warn("关闭 Bot [{}] 超时连接时异常: {}", botQQ, e.getMessage());
                }
            }
        }
    }
}
