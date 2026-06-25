package com.github.zhygtx.napcat.api.api;

import com.github.zhygtx.napcat.api.response.*;
import com.github.zhygtx.napcat.api.response.extra.*;
import com.github.zhygtx.napcat.api.response.file.*;
import com.github.zhygtx.napcat.api.response.friend.*;
import com.github.zhygtx.napcat.api.response.group.*;
import com.github.zhygtx.napcat.api.response.message.*;
import com.github.zhygtx.napcat.api.response.system.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 消息相关 API 接口。
 * <p>
 * 提供发送消息、撤回消息、获取消息详情等核心消息操作。
 * 所有方法通过指定 {@code botQQ} 来区分目标 Bot 连接，异步返回 {@link CompletableFuture}。
 */
@SuppressWarnings("unused")
public interface ApiMessage {

    /**
     * 设置群待办。
     * <p>
     * 将指定消息设置为群待办
     * <p>
     * 对应 NapCat API: {@code set_group_todo}
     * <p>
     * 分类：核心接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param messageId 【可选】消息ID
     * @param messageSeq 【可选】消息Seq (可选)
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupTodo(long botQQ, Long groupId, String messageId, String messageSeq);

    /**
     * 完成群待办。
     * <p>
     * 将指定消息对应的群待办标记为已完成
     * <p>
     * 对应 NapCat API: {@code complete_group_todo}
     * <p>
     * 分类：核心接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param messageId 【可选】消息ID
     * @param messageSeq 【可选】消息Seq (可选)
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> completeGroupTodo(long botQQ, Long groupId, String messageId, String messageSeq);

    /**
     * 取消群待办。
     * <p>
     * 将指定消息对应的群待办取消
     * <p>
     * 对应 NapCat API: {@code cancel_group_todo}
     * <p>
     * 分类：核心接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param messageId 【可选】消息ID
     * @param messageSeq 【可选】消息Seq (可选)
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> cancelGroupTodo(long botQQ, Long groupId, String messageId, String messageSeq);

    /**
     * 获取表情点赞详情。
     * <p>
     * 对应 NapCat API: {@code fetch_emoji_like}
     * <p>
     * 分类：消息扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @param emojiId 【必填】表情ID
     * @param emojiType 【必填】表情类型
     * @param count 【必填】获取数量（默认 20）
     * @param cookie 【必填】分页Cookie（默认 ）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<EmojiLikeData>> fetchEmojiLike(long botQQ, Long messageId, Long emojiId, Long emojiType, Integer count, String cookie);

    /**
     * 获取消息表情点赞列表。
     * <p>
     * 对应 NapCat API: {@code get_emoji_likes}
     * <p>
     * 分类：消息扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【可选】群号，短ID可不传
     * @param messageId 【必填】消息ID，可以传递长ID或短ID
     * @param emojiId 【必填】表情ID
     * @param emojiType 【可选】表情类型
     * @param count 【必填】数量，0代表全部（默认 0）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<EmojiLikesData>> getEmojiLikes(long botQQ, Long groupId, String messageId, String emojiId, String emojiType, Integer count);

    /**
     * 获取语音转文字结果。
     * <p>
     * 对应 NapCat API: {@code fetch_ptt_text}
     * <p>
     * 分类：消息扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<PttTextData>> fetchPttText(long botQQ, Long messageId);

    /**
     * 分享群 (Ark)。
     * <p>
     * 获取群分享的 Ark 内容
     * <p>
     * 对应 NapCat API: {@code ArkShareGroup}
     * <p>
     * 分类：消息扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<String>> arksharegroup(long botQQ, Long groupId);

    /**
     * 分享用户 (Ark)。
     * <p>
     * 获取用户推荐的 Ark 内容
     * <p>
     * 对应 NapCat API: {@code ArkSharePeer}
     * <p>
     * 分类：消息扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【可选】QQ号
     * @param groupId 【可选】群号
     * @param phoneNumber 【必填】手机号（默认 ）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<ArksharepeerData>> arksharepeer(long botQQ, Long userId, Long groupId, String phoneNumber);

    /**
     * 分享群 (Ark)。
     * <p>
     * 获取群分享的 Ark 内容
     * <p>
     * 对应 NapCat API: {@code send_group_ark_share}
     * <p>
     * 分类：消息扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<String>> sendGroupArkShare(long botQQ, Long groupId);

    /**
     * 分享用户 (Ark)。
     * <p>
     * 获取用户推荐的 Ark 内容
     * <p>
     * 对应 NapCat API: {@code send_ark_share}
     * <p>
     * 分类：消息扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【可选】QQ号
     * @param groupId 【可选】群号
     * @param phoneNumber 【必填】手机号（默认 ）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<ArksharepeerData>> sendArkShare(long botQQ, Long userId, Long groupId, String phoneNumber);

    /**
     * 转发单条消息。
     * <p>
     * 转发单条消息
     * <p>
     * 对应 NapCat API: {@code forward_friend_single_msg}
     * <p>
     * 分类：消息接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @param groupId 【可选】目标群号
     * @param userId 【可选】目标用户QQ
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> forwardFriendSingleMsg(long botQQ, Long messageId, Long groupId, Long userId);

    /**
     * 转发单条消息。
     * <p>
     * 转发单条消息
     * <p>
     * 对应 NapCat API: {@code forward_group_single_msg}
     * <p>
     * 分类：消息接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @param groupId 【可选】目标群号
     * @param userId 【可选】目标用户QQ
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> forwardGroupSingleMsg(long botQQ, Long messageId, Long groupId, Long userId);

    /**
     * 标记群聊已读。
     * <p>
     * 标记指定渠道的消息为已读
     * <p>
     * 对应 NapCat API: {@code mark_group_msg_as_read}
     * <p>
     * 分类：消息接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【可选】用户QQ
     * @param groupId 【可选】群号
     * @param messageId 【可选】消息ID
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> markGroupMsgAsRead(long botQQ, Long userId, Long groupId, String messageId);

    /**
     * 标记私聊已读。
     * <p>
     * 标记指定渠道的消息为已读
     * <p>
     * 对应 NapCat API: {@code mark_private_msg_as_read}
     * <p>
     * 分类：消息接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【可选】用户QQ
     * @param groupId 【可选】群号
     * @param messageId 【可选】消息ID
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> markPrivateMsgAsRead(long botQQ, Long userId, Long groupId, String messageId);

    /**
     * 获取消息。
     * <p>
     * 根据消息 ID 获取消息详细信息
     * <p>
     * 对应 NapCat API: {@code get_msg}
     * <p>
     * 分类：消息接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<MsgData>> getMsg(long botQQ, Long messageId);

    /**
     * 发送私聊消息。
     * <p>
     * 发送私聊消息
     * <p>
     * 对应 NapCat API: {@code send_private_msg}
     * <p>
     * 分类：消息接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageType 【可选】消息类型 (private/group)
     * @param userId 【可选】用户QQ
     * @param groupId 【可选】群号
     * @param message 【必填】OneBot 11 消息混合类型
     * @param autoEscape 【可选】是否作为纯文本发送
     * @param source 【可选】合并转发来源
     * @param news 【可选】合并转发新闻
     * @param summary 【可选】合并转发摘要
     * @param prompt 【可选】合并转发提示
     * @param timeout 【可选】自定义发送超时(毫秒)，覆盖自动计算值
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupMsgData>> sendPrivateMsg(long botQQ, String messageType, Long userId, Long groupId, String message, Boolean autoEscape, String source, List<JsonNode> news, String summary, String prompt, Long timeout);

    /**
     * 发送消息。
     * <p>
     * 发送私聊或群聊消息
     * <p>
     * 对应 NapCat API: {@code send_msg}
     * <p>
     * 分类：消息接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageType 【可选】消息类型 (private/group)
     * @param userId 【可选】用户QQ
     * @param groupId 【可选】群号
     * @param message 【必填】OneBot 11 消息混合类型
     * @param autoEscape 【可选】是否作为纯文本发送
     * @param source 【可选】合并转发来源
     * @param news 【可选】合并转发新闻
     * @param summary 【可选】合并转发摘要
     * @param prompt 【可选】合并转发提示
     * @param timeout 【可选】自定义发送超时(毫秒)，覆盖自动计算值
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupMsgData>> sendMsg(long botQQ, String messageType, Long userId, Long groupId, String message, Boolean autoEscape, String source, List<JsonNode> news, String summary, String prompt, Long timeout);

    /**
     * 撤回消息。
     * <p>
     * 撤回已发送的消息
     * <p>
     * 对应 NapCat API: {@code delete_msg}
     * <p>
     * 分类：消息接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> deleteMsg(long botQQ, Long messageId);

    /**
     * 设置消息表情点赞。
     * <p>
     * 对应 NapCat API: {@code set_msg_emoji_like}
     * <p>
     * 分类：消息扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @param emojiId 【必填】表情ID
     * @param set 【可选】是否设置
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<MsgEmojiLikeData>> setMsgEmojiLike(long botQQ, Long messageId, Long emojiId, Boolean set);

    /**
     * 标记消息已读 (Go-CQHTTP)。
     * <p>
     * 标记指定渠道的消息为已读
     * <p>
     * 对应 NapCat API: {@code mark_msg_as_read}
     * <p>
     * 分类：消息接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【可选】用户QQ
     * @param groupId 【可选】群号
     * @param messageId 【可选】消息ID
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> markMsgAsRead(long botQQ, Long userId, Long groupId, String messageId);

    /**
     * 标记所有消息已读。
     * <p>
     * 对应 NapCat API: {@code _mark_all_as_read}
     * <p>
     * 分类：消息接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<String>> markAllAsRead(long botQQ);

    /**
     * 发送戳一戳。
     * <p>
     * 在群聊或私聊中发送戳一戳动作
     * <p>
     * 对应 NapCat API: {@code group_poke}
     * <p>
     * 分类：核心接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【可选】群号
     * @param userId 【必填】用户QQ
     * @param targetId 【可选】目标QQ
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> groupPoke(long botQQ, Long groupId, Long userId, String targetId);

    /**
     * 发送戳一戳。
     * <p>
     * 在群聊或私聊中发送戳一戳动作
     * <p>
     * 对应 NapCat API: {@code friend_poke}
     * <p>
     * 分类：核心接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【可选】群号
     * @param userId 【必填】用户QQ
     * @param targetId 【可选】目标QQ
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> friendPoke(long botQQ, Long groupId, Long userId, String targetId);

    /**
     * 发送戳一戳。
     * <p>
     * 在群聊或私聊中发送戳一戳动作
     * <p>
     * 对应 NapCat API: {@code send_poke}
     * <p>
     * 分类：核心接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【可选】群号
     * @param userId 【必填】用户QQ
     * @param targetId 【可选】目标QQ
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> sendPoke(long botQQ, Long groupId, Long userId, String targetId);

    /**
     * 点击内联键盘按钮。
     * <p>
     * 对应 NapCat API: {@code click_inline_keyboard_button}
     * <p>
     * 分类：消息扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param botAppid 【必填】机器人AppID
     * @param buttonId 【必填】按钮ID（默认 ）
     * @param callbackData 【必填】回调数据（默认 ）
     * @param msgSeq 【必填】消息序列号（默认 10086）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<DoubtFriendsAddRequestData>> clickInlineKeyboardButton(long botQQ, Long groupId, String botAppid, String buttonId, String callbackData, String msgSeq);
}