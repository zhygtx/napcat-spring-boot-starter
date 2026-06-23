package com.github.zhygtx.napcat.api;

import lombok.Getter;

/**
 * Bot 离线异常。
 * <p>
 * 当 Bot 的 WebSocket 连接断开时，该 Bot 的所有待处理 API 请求
 * 都会以此异常结束，让调用方及时感知连接已断开。
 */
@Getter
public class BotOfflineException extends RuntimeException {

    /** Bot QQ 号
     * -- GETTER --
     *  获取已离线的 Bot QQ 号。
     */
    private final Long botQQ;

    /**
     * 构造 Bot 离线异常。
     *
     * @param botQQ 已离线的 Bot QQ 号
     */
    public BotOfflineException(Long botQQ) {
        super("Bot [" + botQQ + "] 已离线，所有待处理的 API 请求已取消");
        this.botQQ = botQQ;
    }

}
