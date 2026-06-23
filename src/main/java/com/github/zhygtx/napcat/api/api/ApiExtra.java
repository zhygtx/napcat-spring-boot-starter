package com.github.zhygtx.napcat.api.api;

import com.github.zhygtx.napcat.api.response.*;
import com.github.zhygtx.napcat.api.response.message.*;
import com.github.zhygtx.napcat.api.response.extra.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 扩展 API 接口。
 * <p>
 * 涵盖 OneBot 标准协议之外的进阶功能，包括：
 * <ul>
 *   <li>合并转发消息（发送/获取/单条转发）</li>
 *   <li>群聊/私聊历史消息</li>
 *   <li>消息已读标记</li>
 *   <li>戳一戳（群/好友）</li>
 *   <li>表情点赞/互动</li>
 *   <li>群相册管理</li>
 *   <li>文件流式传输</li>
 *   <li>OCR 识别 / 翻译</li>
 *   <li>收藏管理</li>
 *   <li>频道/公会</li>
 *   <li>AI 语音/角色</li>
 *   <li>闪传任务</li>
 *   <li>在线文件传输</li>
 *   <li>Ark 分享、内联键盘等</li>
 * </ul>
 * 所有方法通过指定 {@code botQQ} 区分目标 Bot 连接。
 */
@SuppressWarnings("unused")
public interface ApiExtra {

    // ==================== 转发消息 ====================

    /**
     * 发送合并转发消息。
     * <p>
     * 以合并转发（转发聊天记录）形式发送一组消息。
     *
     * @param botQQ      目标 Bot 的 QQ 号
     * @param messages   转发消息节点列表（JsonNode 数组格式）
     * @param messageType 消息类型（"private" / "group"）
     * @param userId     用户 QQ（私聊时必填）
     * @param groupQQ    群号（群聊时必填）
     * @return 异步响应，成功时 data 包含 message_id
     */
    CompletableFuture<ApiResponse<SendMsgData>> sendForwardMsg(long botQQ, JsonNode messages, String messageType, String userId, long groupQQ);

    /**
     * 发送群合并转发消息。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param messages 转发消息节点列表
     * @return 异步响应，成功时 data 包含 message_id
     */
    CompletableFuture<ApiResponse<SendMsgData>> sendGroupForwardMsg(long botQQ, long groupQQ, JsonNode messages);

    /**
     * 发送私聊合并转发消息。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param userId  目标用户 QQ
     * @param messages 转发消息节点列表
     * @return 异步响应，成功时 data 包含 message_id
     */
    CompletableFuture<ApiResponse<SendMsgData>> sendPrivateForwardMsg(long botQQ, String userId, JsonNode messages);

    /**
     * 获取合并转发消息。
     * <p>
     * 根据消息 ID 获取合并转发消息的具体内容。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param messageId 消息 ID
     * @param id        备用消息 ID（可选）
     * @return 异步响应，data 包含消息节点列表
     */
    CompletableFuture<ApiResponse<ForwardMsgData>> getForwardMsg(long botQQ, String messageId, String id);

    /**
     * 转发好友单条消息。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param userId    目标用户 QQ
     * @param messageId 要转发的消息 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> forwardFriendSingleMsg(long botQQ, String userId, long messageId);

    /**
     * 转发群单条消息。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param groupQQ   目标群号
     * @param messageId 要转发的消息 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> forwardGroupSingleMsg(long botQQ, long groupQQ, long messageId);

    // ==================== 历史消息 ====================

    /**
     * 获取群历史消息。
     * <p>
     * 获取指定群聊的历史消息记录。
     *
     * @param botQQ       目标 Bot 的 QQ 号
     * @param groupQQ     目标群号
     * @param messageSeq  起始消息序号
     * @param count       获取消息数量
     * @param reverseOrder 是否反向排序（最新的在前）
     * @return 异步响应，包含消息列表
     */
    CompletableFuture<ApiResponse<VoidData>> getGroupMsgHistory(long botQQ, long groupQQ, long messageSeq, int count, boolean reverseOrder);

    /**
     * 获取好友历史消息。
     *
     * @param botQQ       目标 Bot 的 QQ 号
     * @param userId      目标用户 QQ
     * @param messageSeq  起始消息序号
     * @param count       获取消息数量
     * @param reverseOrder 是否反向排序
     * @return 异步响应，包含消息列表
     */
    CompletableFuture<ApiResponse<VoidData>> getFriendMsgHistory(long botQQ, String userId, long messageSeq, int count, boolean reverseOrder);

    // ==================== 标记已读 ====================

    /**
     * 标记消息已读（Go-CQHTTP 兼容）。
     *
     * @param botQQ      目标 Bot 的 QQ 号
     * @param messageType 消息类型（"private" / "group"）
     * @param groupQQ    群号（群消息时必填）
     * @param userId     用户 QQ（私聊时必填）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> markMsgAsRead(long botQQ, String messageType, long groupQQ, String userId);

    /**
     * 标记群聊已读。
     * <p>
     * 将指定群聊中所有未读消息标记为已读。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> markGroupMsgAsRead(long botQQ, long groupQQ);

    /**
     * 标记私聊已读。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param userId 目标用户 QQ
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> markPrivateMsgAsRead(long botQQ, String userId);

    /**
     * 标记全部已读。
     * <p>
     * 将所有会话的未读消息标记为已读。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> markAllAsRead(long botQQ);

    // ==================== 戳一戳 ====================

    /**
     * 群戳一戳。
     * <p>
     * 在群聊中戳一戳（@）指定成员。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param userId  目标成员 QQ
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> groupPoke(long botQQ, long groupQQ, String userId);

    /**
     * 好友戳一戳。
     * <p>
     * 在私聊中戳一戳指定好友。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param userId 目标用户 QQ
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> friendPoke(long botQQ, String userId);

    /**
     * 发送戳一戳（通用接口）。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param userId 目标用户 QQ
     * @param groupQQ 群号（群聊戳一戳时必填）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendPoke(long botQQ, String userId, long groupQQ);

    // ==================== 表情/点赞 ====================

    /**
     * 获取表情点赞详情。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param messageId 消息 ID
     * @param emojiId   表情 ID
     * @param emojiType 表情类型
     * @param groupQQ   群号（群聊消息时需填）
     * @param userId    用户 QQ（私聊消息时需填）
     * @return 异步响应，包含点赞详情
     */
    CompletableFuture<ApiResponse<VoidData>> fetchEmojiLike(long botQQ, String messageId, String emojiId, String emojiType, long groupQQ, String userId);

    /**
     * 获取消息表情点赞列表。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param messageId 消息 ID
     * @return 异步响应，包含点赞列表
     */
    CompletableFuture<ApiResponse<VoidData>> getEmojiLikes(long botQQ, String messageId);

    /**
     * 设置消息表情点赞。
     * <p>
     * 对某条消息添加或取消表情回应（点赞）。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param messageId 消息 ID
     * @param emojiId   表情 ID
     * @param set       {@code true} 添加 / {@code false} 取消
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setMsgEmojiLike(long botQQ, long messageId, String emojiId, boolean set);

    /**
     * 获取自定义表情。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count  获取数量
     * @return 异步响应，包含自定义表情列表
     */
    CompletableFuture<ApiResponse<VoidData>> fetchCustomFace(long botQQ, int count);

    /**
     * 发送点赞。
     * <p>
     * 给指定用户点赞（QQ 空间的赞）。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param userId 目标用户 QQ
     * @param times  点赞次数
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendLike(long botQQ, String userId, int times);

    // ==================== 群相册 ====================

    /**
     * 获取群相册列表。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，包含群相册列表
     */
    CompletableFuture<ApiResponse<VoidData>> getQunAlbumList(long botQQ, long groupQQ);

    /**
     * 获取群相册媒体列表。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param albumId 相册 ID
     * @return 异步响应，包含相册媒体列表
     */
    CompletableFuture<ApiResponse<VoidData>> getGroupAlbumMediaList(long botQQ, long groupQQ, String albumId);

    /**
     * 删除群相册媒体。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param albumId 相册 ID
     * @param lloc    媒体 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> deleteGroupAlbumMedia(long botQQ, long groupQQ, String albumId, String lloc);

    /**
     * 点赞群相册媒体。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param albumId 相册 ID
     * @param lloc    媒体 ID
     * @param id      点赞 ID
     * @param set     {@code true} 点赞 / {@code false} 取消
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupAlbumMediaLike(long botQQ, long groupQQ, String albumId, String lloc, String id, boolean set);

    /**
     * 发表群相册评论。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param groupQQ  目标群号
     * @param albumId  相册 ID
     * @param lloc     媒体 ID
     * @param content  评论内容
     * @param replyUid 回复目标的 UID（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> doGroupAlbumComment(long botQQ, long groupQQ, String albumId, String lloc, String content, String replyUid);

    /**
     * 上传图片到群相册。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param file   图片文件路径
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> uploadImageToQunAlbum(long botQQ, long groupQQ, String file);

    // ==================== 流式传输 ====================

    /**
     * 上传文件流（分块上传）。
     *
     * @param botQQ       目标 Bot 的 QQ 号
     * @param streamId    流 ID
     * @param chunkData   分块数据（Base64 编码）
     * @param chunkIndex  分块索引
     * @param totalChunks 总分块数
     * @param fileSize    文件总大小
     * @param filename    文件名（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> uploadFileStream(long botQQ, String streamId, String chunkData, int chunkIndex, int totalChunks, long fileSize, String filename);

    /**
     * 下载文件流（分块下载）。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param file     文件路径或 URL（可选）
     * @param fileId   文件 ID（可选）
     * @param chunkSize 分块大小（字节）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> downloadFileStream(long botQQ, String file, String fileId, int chunkSize);

    /**
     * 下载语音文件流。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param file      文件路径或 URL（可选）
     * @param fileId    文件 ID（可选）
     * @param chunkSize 分块大小（字节）
     * @param outFormat 输出格式（如 {@code "mp3"}）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> downloadFileRecordStream(long botQQ, String file, String fileId, int chunkSize, String outFormat);

    /**
     * 下载图片文件流。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param file      文件路径或 URL（可选）
     * @param fileId    文件 ID（可选）
     * @param chunkSize 分块大小（字节）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> downloadFileImageStream(long botQQ, String file, String fileId, int chunkSize);

    /**
     * 清理流式传输临时文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> cleanStreamTempFile(long botQQ);

    /**
     * 测试下载流。
     *
     * @param botQQ       目标 Bot 的 QQ 号
     * @param url         测试 URL
     * @param triggerError 是否触发测试错误
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> testDownloadStream(long botQQ, String url, boolean triggerError);

    // ==================== OCR / 翻译 ====================

    /**
     * 图片 OCR 识别。
     * <p>
     * 识别图片中的文字内容。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param image 图片文件路径或 URL
     * @return 异步响应，data 包含 OCR 识别结果
     */
    CompletableFuture<ApiResponse<OcrResultData>> ocrImage(long botQQ, String image);

    /**
     * 英文单词翻译（英译中）。
     * <p>
     * 将英文单词或短语翻译为中文。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param words 待翻译的英文单词列表
     * @return 异步响应，data 包含翻译结果
     */
    CompletableFuture<ApiResponse<TranslateResultData>> translateEn2zh(long botQQ, List<String> words);

    // ==================== 收藏 ====================

    /**
     * 创建收藏。
     * <p>
     * 将内容收藏到机器人的收藏夹中。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param rawData 要收藏的原始数据
     * @param type    收藏类型
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> createCollection(long botQQ, String rawData, int type);

    /**
     * 获取收藏列表。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param category 分类
     * @param startPos 起始位置
     * @param count    获取数量
     * @return 异步响应，包含收藏列表
     */
    CompletableFuture<ApiResponse<VoidData>> getCollectionList(long botQQ, int category, String startPos, int count);

    // ==================== 频道/公会 ====================

    /**
     * 获取频道列表。
     * <p>
     * 获取机器人加入的 QQ 频道列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，包含频道列表
     */
    CompletableFuture<ApiResponse<VoidData>> getGuildList(long botQQ);

    /**
     * 获取频道服务资料。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param guildId 频道 ID
     * @return 异步响应，包含频道服务资料
     */
    CompletableFuture<ApiResponse<VoidData>> getGuildServiceProfile(long botQQ, String guildId);

    // ==================== AI ====================

    /**
     * 获取 AI 语音。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param character AI 角色名
     * @param text      要转语音的文本
     * @param groupQQ   群号（可选）
     * @return 异步响应，包含 AI 语音信息
     */
    CompletableFuture<ApiResponse<VoidData>> getAiRecord(long botQQ, String character, String text, long groupQQ);

    /**
     * 发送群 AI 语音。
     * <p>
     * 在群聊中发送一段 AI 生成的语音消息。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param groupQQ   目标群号
     * @param character AI 角色名
     * @param text      要转语音的文本
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendGroupAiRecord(long botQQ, long groupQQ, String character, String text);

    /**
     * 获取 AI 角色列表。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 群号（可选）
     * @param chatType 聊天类型（可选）
     * @return 异步响应，包含 AI 角色列表
     */
    CompletableFuture<ApiResponse<VoidData>> getAiCharacters(long botQQ, long groupQQ, String chatType);

    // ==================== 闪传 ====================

    /**
     * 创建闪传任务。
     * <p>
     * 创建文件闪传任务，用于快速传输文件。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param filePaths 要传输的文件路径列表
     * @param isPrivate 是否私密传输
     * @return 异步响应，包含闪传任务信息
     */
    CompletableFuture<ApiResponse<VoidData>> createFlashTask(long botQQ, List<String> filePaths, boolean isPrivate);

    /**
     * 获取闪传文件列表。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param taskId 任务 ID
     * @return 异步响应，包含文件列表
     */
    CompletableFuture<ApiResponse<VoidData>> getFlashFileList(long botQQ, String taskId);

    /**
     * 获取闪传文件 URL。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param taskId 任务 ID
     * @param fileId 文件 ID
     * @return 异步响应，包含文件下载 URL
     */
    CompletableFuture<ApiResponse<VoidData>> getFlashFileUrl(long botQQ, String taskId, String fileId);

    /**
     * 发送闪传消息。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param userId 用户 QQ（私聊时填）
     * @param groupQQ 群号（群聊时填）
     * @param taskId 闪传任务 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendFlashMsg(long botQQ, String userId, long groupQQ, String taskId);

    // ==================== 在线文件 ====================

    /**
     * 获取在线文件消息。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param messageId 消息 ID
     * @param selfId    机器人自身 QQ
     * @return 异步响应，包含在线文件信息
     */
    CompletableFuture<ApiResponse<VoidData>> getOnlineFileMsg(long botQQ, String messageId, String selfId);

    /**
     * 发送在线文件。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param userId   用户 QQ（私聊时填）
     * @param groupQQ  群号（群聊时填）
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param fileSize 文件大小（字节）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendOnlineFile(long botQQ, String userId, long groupQQ, String filePath, String fileName, long fileSize);

    /**
     * 发送在线文件夹。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param userId   用户 QQ（私聊时填）
     * @param groupQQ  群号（群聊时填）
     * @param folderId 文件夹 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendOnlineFolder(long botQQ, String userId, long groupQQ, String folderId);

    /**
     * 接收在线文件。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param taskId  任务 ID
     * @param destPath 保存路径
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> receiveOnlineFile(long botQQ, String taskId, String destPath);

    /**
     * 拒绝接收在线文件。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param taskId 任务 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> refuseOnlineFile(long botQQ, String taskId);

    /**
     * 取消在线文件传输。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param taskId 任务 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> cancelOnlineFile(long botQQ, String taskId);

    // ==================== 其他扩展 ====================

    /**
     * 设置输入状态。
     * <p>
     * 设置机器人正在输入的状态提示。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param userId 用户 QQ（私聊时填）
     * @param groupQQ 群号（群聊时填）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setInputStatus(long botQQ, String userId, long groupQQ);

    /**
     * 获取最近联系人。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count  获取数量
     * @return 异步响应，包含最近联系人列表
     */
    CompletableFuture<ApiResponse<VoidData>> getRecentContact(long botQQ, int count);

    /**
     * 获取模型展示。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param model 模型名称
     * @return 异步响应，包含模型展示信息
     */
    CompletableFuture<ApiResponse<VoidData>> getModelShow(long botQQ, String model);

    /**
     * 设置模型展示。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param model     模型名称
     * @param modelShow 模型展示内容
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setModelShow(long botQQ, String model, String modelShow);

    /**
     * 获取小程序 Ark。
     * <p>
     * 获取小程序的分享 Ark 信息。
     *
     * @param botQQ      目标 Bot 的 QQ 号
     * @param appId      小程序 AppID
     * @param appPath    小程序路径
     * @param appVersion 小程序版本
     * @param appQq      开发者 QQ（可选）
     * @param appType    小程序类型（可选）
     * @return 异步响应，包含小程序 Ark 信息
     */
    CompletableFuture<ApiResponse<VoidData>> getMiniAppArk(long botQQ, String appId, String appPath, String appVersion, String appQq, String appType);

    /**
     * 分享群（Ark 卡片）。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> ArkShareGroup(long botQQ, long groupQQ);

    /**
     * 分享用户（Ark 卡片）。
     *
     * @param botQQ      目标 Bot 的 QQ 号
     * @param userId     用户 QQ（可选）
     * @param phoneNumber 手机号（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> ArkSharePeer(long botQQ, String userId, String phoneNumber);

    /**
     * 发送群 Ark 分享。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param text    文本内容
     * @param title   标题
     * @param desc    描述
     * @param jumpUrl 跳转链接
     * @param preview 预览图片 URL
     * @param tag     标签
     * @param tagIcon 标签图标 URL
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendGroupArkShare(long botQQ, long groupQQ, String text, String title, String desc, String jumpUrl, String preview, String tag, String tagIcon);

    /**
     * 发送 Ark 分享（通用）。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param userId  用户 QQ
     * @param text    文本内容
     * @param title   标题
     * @param desc    描述
     * @param jumpUrl 跳转链接
     * @param preview 预览图片
     * @param tag     标签
     * @param tagIcon 标签图标
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendArkShare(long botQQ, String userId, String text, String title, String desc, String jumpUrl, String preview, String tag, String tagIcon);

    /**
     * 单击内联键盘按钮。
     * <p>
     * 模拟用户点击内联键盘上的某个按钮。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param messageId 消息 ID
     * @param buttonId 按钮 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> clickInlineKeyboardButton(long botQQ, String messageId, String buttonId);

    /**
     * 处理快速操作。
     * <p>
     * 对事件上报中的快速操作进行处理。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param context  事件上下文
     * @param operation 快速操作内容
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> handleQuickOperation(long botQQ, JsonNode context, JsonNode operation);
}
