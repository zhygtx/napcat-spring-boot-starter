package com.github.zhygtx.napcat.api.api;

import com.github.zhygtx.napcat.api.response.*;
import com.github.zhygtx.napcat.api.response.message.*;
import com.github.zhygtx.napcat.api.response.system.*;
import com.github.zhygtx.napcat.api.response.friend.*;
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
     * @param botQQ       目标 Bot 的 QQ 号
     * @param message     转发消息节点列表（JsonNode 数组格式）
     * @param messageType 消息类型（"private" / "group"）
     * @param userId      用户 QQ（私聊时必填）
     * @param groupQQ     群号（群聊时必填）
     * @param source      合并转发来源（可选）
     * @param news        合并转发新闻列表（可选）
     * @param summary     合并转发摘要（可选）
     * @param prompt      合并转发提示（可选）
     * @param timeout     自定义发送超时毫秒（可选）
     * @return 异步响应，成功时 data 包含 message_id
     */
    CompletableFuture<ApiResponse<SendMsgData>> sendForwardMsg(long botQQ, JsonNode message, String messageType, String userId, long groupQQ,
                                                                String source, JsonNode news, String summary, String prompt, Long timeout);

    /**
     * 发送群合并转发消息。
     * <p>
     * 在群聊中发送合并转发消息。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param message 转发消息节点列表
     * @param source  合并转发来源（可选）
     * @param news    合并转发新闻列表（可选）
     * @param summary 合并转发摘要（可选）
     * @param prompt  合并转发提示（可选）
     * @param timeout 自定义发送超时毫秒（可选）
     * @return 异步响应，成功时 data 包含 message_id
     */
    CompletableFuture<ApiResponse<SendMsgData>> sendGroupForwardMsg(long botQQ, long groupQQ, JsonNode message,
                                                                     String source, JsonNode news, String summary, String prompt, Long timeout);

    /**
     * 发送私聊合并转发消息。
     * <p>
     * 在私聊中发送合并转发消息。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param userId  目标用户 QQ
     * @param message 转发消息节点列表
     * @param source  合并转发来源（可选）
     * @param news    合并转发新闻列表（可选）
     * @param summary 合并转发摘要（可选）
     * @param prompt  合并转发提示（可选）
     * @param timeout 自定义发送超时毫秒（可选）
     * @return 异步响应，成功时 data 包含 message_id
     */
    CompletableFuture<ApiResponse<SendMsgData>> sendPrivateForwardMsg(long botQQ, String userId, JsonNode message,
                                                                       String source, JsonNode news, String summary, String prompt, Long timeout);

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
     * <p>
     * 将一条消息转发给指定好友。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param userId    目标用户 QQ
     * @param groupQQ   群号（可选，group_id）
     * @param messageId 要转发的消息 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> forwardFriendSingleMsg(long botQQ, String userId, long groupQQ, String messageId);

    /**
     * 转发群单条消息。
     * <p>
     * 将一条消息转发到指定群聊。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param groupQQ   目标群号
     * @param userId    用户 QQ（可选）
     * @param messageId 要转发的消息 ID（number|string）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> forwardGroupSingleMsg(long botQQ, long groupQQ, String userId, String messageId);

    // ==================== 历史消息 ====================

    /**
     * 获取群历史消息。
     * <p>
     * 获取指定群聊的历史消息记录。
     *
     * @param botQQ          目标 Bot 的 QQ 号
     * @param groupQQ        目标群号
     * @param messageSeq     起始消息序号
     * @param count          获取消息数量
     * @param reverseOrder   是否反向排序（最新的在前）
     * @param disableGetUrl  是否禁用获取 URL
     * @param parseMultMsg   是否解析合并消息
     * @param quickReply     是否快速回复
     * @param reverseOrderCompat 是否反向排序（旧版本兼容）
     * @return 异步响应，包含消息列表
     */
    CompletableFuture<ApiResponse<HistoryMsgData>> getGroupMsgHistory(long botQQ, long groupQQ, long messageSeq, int count, boolean reverseOrder, boolean disableGetUrl, boolean parseMultMsg, boolean quickReply, boolean reverseOrderCompat);

    /**
     * 获取好友历史消息。
     * <p>
     * 获取指定好友的历史消息记录。
     *
     * @param botQQ             目标 Bot 的 QQ 号
     * @param userId            目标用户 QQ
     * @param messageSeq        起始消息序号
     * @param count             获取消息数量
     * @param reverseOrder      是否反向排序
     * @param disableGetUrl     是否禁用获取 URL
     * @param parseMultMsg      是否解析合并消息
     * @param quickReply        是否快速回复
     * @param reverseOrderCompat 是否反向排序（旧版本兼容）
     * @return 异步响应，包含消息列表
     */
    CompletableFuture<ApiResponse<HistoryMsgData>> getFriendMsgHistory(long botQQ, String userId, long messageSeq, int count, boolean reverseOrder, boolean disableGetUrl, boolean parseMultMsg, boolean quickReply, boolean reverseOrderCompat);

    // ==================== 标记已读 ====================

    /**
     * 标记消息已读（Go-CQHTTP 兼容）。
     * <p>
     * 将指定消息标记为已读。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param groupQQ   群号（群消息时必填）
     * @param userId    用户 QQ（私聊时必填）
     * @param messageId 消息 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> markMsgAsRead(long botQQ, long groupQQ, String userId, String messageId);

    /**
     * 标记群聊已读。
     * <p>
     * 标记特定用户在群聊中的消息已读。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param groupQQ   目标群号
     * @param userId    用户 QQ（可选）
     * @param messageId 消息 ID（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> markGroupMsgAsRead(long botQQ, long groupQQ, String userId, String messageId);

    /**
     * 标记私聊已读。
     * <p>
     * 将指定私聊会话的未读消息标记为已读。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param userId    目标用户 QQ
     * @param groupQQ   群号（可选）
     * @param messageId 消息 ID（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> markPrivateMsgAsRead(long botQQ, String userId, long groupQQ, String messageId);

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
     * <p>
     * 向指定用户发送戳一戳，自动判断群聊或私聊。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param userId  目标用户 QQ
     * @param groupQQ 群号（群聊戳一戳时必填）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendPoke(long botQQ, String userId, long groupQQ);

    // ==================== 表情/点赞 ====================

    /**
     * 获取表情点赞详情。
     * <p>
     * 获取指定消息的某个表情的点赞详细信息（JSON 字段为驼峰命名 emojiId / emojiType）。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param messageId 消息 ID
     * @param emojiId   表情 ID
     * @param emojiType 表情类型
     * @param count     获取数量
     * @param cookie    分页 cookie
     * @return 异步响应，包含点赞详情
     */
    CompletableFuture<ApiResponse<EmojiLikeDetailData>> fetchEmojiLike(long botQQ, String messageId, String emojiId, String emojiType, String count, String cookie);

    /**
     * 获取消息表情点赞列表。
     * <p>
     * 获取指定消息的所有表情点赞信息。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param messageId 消息 ID
     * @param emojiId   表情 ID（必填）
     * @param emojiType 表情类型
     * @param groupQQ   群号
     * @param count     获取数量
     * @return 异步响应，包含点赞列表
     */
    CompletableFuture<ApiResponse<EmojiLikesData>> getEmojiLikes(long botQQ, String messageId, String emojiId, String emojiType, long groupQQ, int count);

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
    CompletableFuture<ApiResponse<VoidData>> setMsgEmojiLike(long botQQ, String messageId, String emojiId, boolean set);

    /**
     * 获取自定义表情。
     * <p>
     * 获取机器人的自定义表情列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count  获取数量
     * @return 异步响应，包含自定义表情列表
     */
    CompletableFuture<ApiResponse<List<CustomFaceData>>> fetchCustomFace(long botQQ, int count);

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
     * <p>
     * 获取指定群聊的所有相册列表。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param groupQQ   目标群号
     * @param attachInfo 附加信息（可选，默认空字符串）
     * @return 异步响应，包含群相册列表
     */
    CompletableFuture<ApiResponse<QunAlbumListData>> getQunAlbumList(long botQQ, long groupQQ, String attachInfo);

    /**
     * 获取群相册媒体列表。
     * <p>
     * 获取指定群相册中的媒体文件列表。
     *
     * @param botQQ      目标 Bot 的 QQ 号
     * @param groupQQ    目标群号
     * @param albumId    相册 ID
     * @param attachInfo 附加信息（可选，默认空字符串）
     * @return 异步响应，包含相册媒体列表
     */
    CompletableFuture<ApiResponse<GroupAlbumMediaListData>> getGroupAlbumMediaList(long botQQ, long groupQQ, String albumId, String attachInfo);

    /**
     * 删除群相册媒体。
     * <p>
     * 删除群相册中的指定媒体文件。
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
     * <p>
     * 对群相册中的媒体文件进行点赞操作。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param albumId 相册 ID
     * @param lloc    媒体 ID
     * @param batchId 批次 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupAlbumMediaLike(long botQQ, long groupQQ, String albumId, String lloc, String batchId);

    /**
     * 发表群相册评论。
     * <p>
     * 对群相册中的媒体文件发表评论。
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
     * <p>
     * 将本地图片上传到指定群聊的相册中。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param groupQQ   目标群号
     * @param albumId   相册 ID
     * @param albumName 相册名称
     * @param file      图片文件路径
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> uploadImageToQunAlbum(long botQQ, long groupQQ, String albumId, String albumName, String file);

    // ==================== 流式传输 ====================

    /**
     * 上传文件流（分块上传）。
     * <p>
     * 以分块方式上传文件流。
     *
     * @param botQQ          目标 Bot 的 QQ 号
     * @param streamId       流 ID
     * @param chunkData      分块数据（Base64 编码）
     * @param chunkIndex     分块索引
     * @param totalChunks    总分块数
     * @param fileSize       文件总大小
     * @param expectedSha256 期望的 SHA256 校验值（可选）
     * @param isComplete     是否上传完成
     * @param filename       文件名（可选）
     * @param reset          是否重置流
     * @param verifyOnly     是否仅验证
     * @param fileRetention  文件保留时间（毫秒），默认 300000
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> uploadFileStream(long botQQ, String streamId, String chunkData, int chunkIndex, int totalChunks, long fileSize, String expectedSha256, boolean isComplete, String filename, boolean reset, boolean verifyOnly, long fileRetention);

    /**
     * 下载文件流（分块下载）。
     * <p>
     * 以分块方式下载文件流。
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
     * <p>
     * 以分块方式下载语音文件流。
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
     * <p>
     * 以分块方式下载图片文件流。
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
     * <p>
     * 清理流式传输过程中产生的临时文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> cleanStreamTempFile(long botQQ);

    /**
     * 测试下载流。
     * <p>
     * 测试流式下载功能是否正常。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param error 是否触发测试错误
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> testDownloadStream(long botQQ, boolean error);

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
     * @param brief   简要描述
     * @return 异步响应，包含收藏结果
     */
    CompletableFuture<ApiResponse<CreateCollectionData>> createCollection(long botQQ, String rawData, String brief);

    /**
     * 获取收藏列表。
     * <p>
     * 获取指定分类的收藏列表。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param category 分类 ID
     * @param count    获取数量
     * @return 异步响应，包含收藏列表
     */
    CompletableFuture<ApiResponse<CollectionListData>> getCollectionList(long botQQ, String category, String count);

    // ==================== 频道/公会 ====================

    /**
     * 获取频道列表。
     * <p>
     * 获取机器人加入的 QQ 频道列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，包含频道列表
     */
    CompletableFuture<ApiResponse<List<GuildListData>>> getGuildList(long botQQ);

    /**
     * 获取频道服务资料。
     * <p>
     * 获取指定频道的服务资料信息。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param guildId 频道 ID
     * @return 异步响应，包含频道服务资料
     */
    CompletableFuture<ApiResponse<GuildServiceProfileData>> getGuildServiceProfile(long botQQ, String guildId);

    // ==================== AI ====================

    /**
     * 获取 AI 语音。
     * <p>
     * 将文本转换为 AI 语音，返回语音 URL 字符串。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param character AI 角色名
     * @param text      要转语音的文本
     * @param groupQQ   群号（可选）
     * @return 异步响应，data 为语音 URL 字符串
     */
    CompletableFuture<ApiResponse<String>> getAiRecord(long botQQ, String character, String text, long groupQQ);

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
     * <p>
     * 获取可用的 AI 语音角色列表。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 群号（可选）
     * @param chatType 聊天类型（可选）
     * @return 异步响应，包含 AI 角色列表
     */
    CompletableFuture<ApiResponse<List<AiCharactersData>>> getAiCharacters(long botQQ, long groupQQ, String chatType);

    // ==================== 闪传 ====================

    /**
     * 创建闪传任务。
     * <p>
     * 创建文件闪传任务，用于快速传输文件。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param files    要传输的文件
     * @param name     任务名称
     * @param thumbPath 缩略图路径
     * @return 异步响应，包含闪传任务信息
     */
    CompletableFuture<ApiResponse<FlashTaskData>> createFlashTask(long botQQ, String files, String name, String thumbPath);

    /**
     * 获取闪传文件列表。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param filesetId 文件集 ID
     * @return 异步响应，包含文件列表
     */
    CompletableFuture<ApiResponse<VoidData>> getFlashFileList(long botQQ, String filesetId);

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
     * @param botQQ     目标 Bot 的 QQ 号
     * @param userId    用户 QQ（私聊时填）
     * @param groupQQ   群号（群聊时填）
     * @param filesetId 文件集 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendFlashMsg(long botQQ, String userId, long groupQQ, String filesetId);

    // ==================== 在线文件 ====================

    /**
     * 获取在线文件消息。
     * <p>
     * 获取指定用户的在线文件消息。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param userId 用户 QQ
     * @return 异步响应，包含在线文件消息信息
     */
    CompletableFuture<ApiResponse<OnlineFileMsgData>> getOnlineFileMsg(long botQQ, String userId);

    /**
     * 发送在线文件。
     * <p>
     * 向指定用户发送在线文件。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param userId   用户 QQ
     * @param filePath 本地文件路径
     * @param fileName 文件名（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendOnlineFile(long botQQ, String userId, String filePath, String fileName);

    /**
     * 发送在线文件夹。
     * <p>
     * 向指定用户发送在线文件夹。
     *
     * @param botQQ      目标 Bot 的 QQ 号
     * @param userId     用户 QQ
     * @param folderPath 本地文件夹路径
     * @param folderName 文件夹名称（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendOnlineFolder(long botQQ, String userId, String folderPath, String folderName);

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
     * @param botQQ     目标 Bot 的 QQ 号
     * @param userId    用户 QQ
     * @param eventType 事件类型
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setInputStatus(long botQQ, String userId, int eventType);

    /**
     * 获取最近联系人。
     * <p>
     * 获取机器人最近联系过的用户列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 获取数量
     * @return 异步响应，包含最近联系人列表
     */
    CompletableFuture<ApiResponse<List<RecentContactData>>> getRecentContact(long botQQ, int count);

    /**
     * 获取模型展示。
     * <p>
     * 获取指定模型的展示信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param model 模型名称
     * @return 异步响应，包含模型展示信息
     */
    CompletableFuture<ApiResponse<List<ModelShowData>>> getModelShow(long botQQ, String model);

    /**
     * 设置模型展示。
     * <p>
     * 设置指定模型的展示内容。
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
    CompletableFuture<ApiResponse<MiniAppArkData>> getMiniAppArk(long botQQ, String appId, String appPath, String appVersion, String appQq, String appType);

    /**
     * 分享群（Ark 卡片）。
     * <p>
     * 获取群的 Ark 分享卡片内容（Ark JSON 字符串）。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，data 为 Ark JSON 字符串
     */
    CompletableFuture<ApiResponse<String>> ArkShareGroup(long botQQ, long groupQQ);

    /**
     * 分享用户（Ark 卡片）。
     * <p>
     * 获取用户的 Ark 分享卡片内容。
     *
     * @param botQQ       目标 Bot 的 QQ 号
     * @param userId      用户 QQ（可选）
     * @param phoneNumber 手机号（可选）
     * @return 异步响应，data 包含 ark 字段（Ark JSON 字符串）
     */
    CompletableFuture<ApiResponse<ArkShareData>> ArkSharePeer(long botQQ, String userId, String phoneNumber);

    /**
     * 发送群 Ark 分享。
     * <p>
     * 获取群分享的 Ark 卡片内容，返回 Ark JSON 字符串。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，data 为 Ark JSON 字符串
     */
    CompletableFuture<ApiResponse<String>> sendGroupArkShare(long botQQ, long groupQQ);

    /**
     * 发送 Ark 分享（通用）。
     * <p>
     * 获取用户推荐的 Ark 分享卡片内容。
     *
     * @param botQQ       目标 Bot 的 QQ 号
     * @param userId      用户 QQ（可选）
     * @param groupQQ     群号（可选）
     * @param phoneNumber 手机号
     * @return 异步响应，data 包含 ark 字段（Ark JSON 字符串）
     */
    CompletableFuture<ApiResponse<ArkShareData>> sendArkShare(long botQQ, String userId, long groupQQ, String phoneNumber);

    /**
     * 单击内联键盘按钮。
     * <p>
     * 模拟用户点击内联键盘上的某个按钮。
     *
     * @param botQQ        目标 Bot 的 QQ 号
     * @param groupQQ      群号
     * @param botAppid     机器人 AppID
     * @param buttonId     按钮 ID
     * @param callbackData 回调数据
     * @param msgSeq       消息序列号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> clickInlineKeyboardButton(long botQQ, long groupQQ, String botAppid, String buttonId, String callbackData, String msgSeq);

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

    // ==================== 新增方法 ====================

    /**
     * 取消点赞群相册媒体。
     * <p>
     * 取消对群相册中媒体文件的点赞。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param albumId 相册 ID
     * @param batchId 批次 ID
     * @param lloc    媒体 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> cancelGroupAlbumMediaLike(long botQQ, long groupQQ, String albumId, String batchId, String lloc);

    /**
     * 获取语音转文字结果。
     * <p>
     * 获取指定语音消息的文字识别结果。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param messageId 消息 ID（类型为 number|string）
     * @return 异步响应，包含语音转文字结果
     */
    CompletableFuture<ApiResponse<PttTextData>> fetchPttText(long botQQ, String messageId);

    /**
     * 添加自定义表情。
     * <p>
     * 向表情收藏中添加新的自定义表情。
     *
     * @param botQQ      目标 Bot 的 QQ 号
     * @param file       表情文件路径
     * @param emojiId    表情 ID
     * @param packageId  表情包 ID
     * @param fileName   文件名
     * @param fileSize   文件大小
     * @param md5        文件 MD5
     * @param isMarkFace 是否为贴图表情
     * @param isOrigin   是否为原图
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> addCustomFace(long botQQ, String file, String emojiId, String packageId, String fileName, long fileSize, String md5, boolean isMarkFace, boolean isOrigin);

    /**
     * 删除自定义表情。
     * <p>
     * 从表情收藏中删除自定义表情。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param resId 资源 ID
     * @param ids   表情 ID 列表
     * @param md5   文件 MD5
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> deleteCustomFace(long botQQ, String resId, String ids, String md5);

    /**
     * 获取自定义表情详情。
     * <p>
     * 获取自定义表情的详细信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 获取数量
     * @return 异步响应，包含自定义表情详情
     */
    CompletableFuture<ApiResponse<CustomFaceDetailData>> fetchCustomFaceDetail(long botQQ, int count);

    /**
     * 修改自定义表情描述。
     * <p>
     * 修改指定自定义表情的描述信息。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param emojiId 表情 ID
     * @param resId   资源 ID
     * @param md5     文件 MD5
     * @param desc    描述内容
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setCustomFaceDesc(long botQQ, String emojiId, String resId, String md5, String desc);
}
