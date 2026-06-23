package com.github.zhygtx.napcat.service.impl;

import com.github.zhygtx.napcat.event.BotEventListener;
import org.springframework.stereotype.Component;

@Component
public class BotEventListenerImpl implements BotEventListener {
    /**
     * Bot 上线时调用。
     * 在 WebSocket 连接建立、会话注册到 {@code BotSessionRegistry} 后触发。
     * @param botQQ 上线的 Bot QQ 号
     */
    @Override
    public void botOnline(Long botQQ) {
        System.out.println("Bot " + botQQ + " 上线");
    }

    /**
     * Bot 离线时调用。
     * 在 WebSocket 连接关闭、会话从 {@code BotSessionRegistry} 移除后触发。
     * @param botQQ 离线的 Bot QQ 号
     */
    @Override
    public void botOffline(Long botQQ) {
        System.out.println("Bot " + botQQ + " 离线");
    }
}
