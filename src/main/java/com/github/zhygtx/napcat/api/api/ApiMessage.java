package com.github.zhygtx.napcat.api.api;

import com.github.zhygtx.napcat.api.response.*;
import com.github.zhygtx.napcat.api.response.message.*;

import java.util.concurrent.CompletableFuture;

/**
 * 消息相关 API 接口。
 * <p>
 * 提供发送群消息、私聊消息、通用消息、撤回消息和获取消息详情的核心消息操作。
 * 所有方法通过指定 {@code botQQ} 来区分目标 Bot 连接，异步返回 {@link CompletableFuture}。
 * <p>
 * 消息内容支持 CQ 码格式（如 {@code [CQ:image,file=xxx.jpg]}）或消息段 JSON 字符串。
 */
@SuppressWarnings("unused")
public interface ApiMessage {

    /**
     * 发送群消息。
     * <p>
     * 向指定群发送一条消息。如果群号不存在或机器人不在群中则返回失败。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param groupQQ   目标群号
     * @param message   消息内容，支持 CQ 码或消息段 JSON 字符串
     * @param autoEscape 是否将消息内容作为纯文本发送（{@code true} 时 CQ 码不会被解析）
     * @return 异步响应，成功时 data 包含 {@code message_id}（消息 ID）
     */
    CompletableFuture<ApiResponse<SendMsgData>> sendGroupMsg(long botQQ, long groupQQ, String message, boolean autoEscape);

    /**
     * 发送私聊消息。
     * <p>
     * 向指定用户发送一条私聊消息。对方需为机器人的好友或曾主动发起过会话。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param userId    目标用户 QQ
     * @param message   消息内容，支持 CQ 码或消息段 JSON 字符串
     * @param autoEscape 是否将消息内容作为纯文本发送
     * @return 异步响应，成功时 data 包含 {@code message_id}
     */
    CompletableFuture<ApiResponse<SendMsgData>> sendPrivateMsg(long botQQ, String userId, String message, boolean autoEscape);

    /**
     * 发送消息 — 自动识别私聊/群聊的通用接口。
     * <p>
     * 根据 {@code messageType} 参数自动路由到私聊或群聊。私聊时需传 {@code userId}，
     * 群聊时需传 {@code groupQQ}。
     *
     * @param botQQ      目标 Bot 的 QQ 号
     * @param messageType 消息类型：{@code "private"} 或 {@code "group"}
     * @param userId     用户 QQ（私聊时必填，群聊时填 {@code null} 或 {@code "0"}）
     * @param groupQQ    群号（群聊时必填）
     * @param message    消息内容
     * @param autoEscape 是否作为纯文本发送
     * @return 异步响应，成功时 data 包含 {@code message_id}
     */
    CompletableFuture<ApiResponse<SendMsgData>> sendMsg(long botQQ, String messageType, String userId, long groupQQ, String message, boolean autoEscape);

    /**
     * 撤回消息。
     * <p>
     * 根据消息 ID 撤回一条已发送的消息。撤回操作有时间限制（通常为 2 分钟内）。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param messageId 要撤回的消息 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> deleteMsg(long botQQ, long messageId);

    /**
     * 获取消息详情。
     * <p>
     * 根据消息 ID 获取指定消息的完整内容，包括发送者、时间、消息内容等。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param messageId 消息 ID
     * @return 异步响应，data 包含消息的详细信息（sender、time、message 等）
     */
    CompletableFuture<ApiResponse<GetMsgData>> getMsg(long botQQ, long messageId);
}
