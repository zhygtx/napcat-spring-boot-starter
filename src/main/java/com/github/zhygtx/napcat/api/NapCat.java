package com.github.zhygtx.napcat.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.zhygtx.napcat.api.api.*;
import com.github.zhygtx.napcat.api.response.*;
import com.github.zhygtx.napcat.api.response.message.*;
import com.github.zhygtx.napcat.api.response.group.*;
import com.github.zhygtx.napcat.api.response.friend.*;
import com.github.zhygtx.napcat.api.response.system.*;
import com.github.zhygtx.napcat.api.response.file.*;
import com.github.zhygtx.napcat.api.response.extra.*;
import com.github.zhygtx.napcat.util.NapCatObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * NapCat Bot 主操作类。
 * <p>
 * 实现所有 API 接口，是 NapCat SDK 最核心的用户入口。每个 Bot 实例对应
 * 一个已连接的 NapCat QQ 号，通过指定 {@code botQQ} 来区分目标 Bot。
 * <p>
 * 本类由 OpenAPI 规范自动生成，请勿手动修改。
 */
@Service
public class NapCat implements ApiExtra, ApiFile, ApiFriend, ApiGroup, ApiMessage, ApiSystem {

    private final NapCatApiClient engine;
    private final NapCatObjectMapper mapper;

    public NapCat(NapCatApiClient engine, NapCatObjectMapper mapper) {
        this.engine = engine;
        this.mapper = mapper;
    }

    // ===== 快捷方法 =====

    private <T> CompletableFuture<ApiResponse<T>> req(long botQQ, String action, ObjectNode params, Class<T> dataClass) {
        return engine.sendRequest(botQQ, action, params, engine.type(dataClass));
    }

    private <T> CompletableFuture<ApiResponse<List<T>>> reqList(long botQQ, String action, ObjectNode params, Class<T> elemClass) {
        return engine.sendRequest(botQQ, action, params, engine.listType(elemClass));
    }

    // ================================================================
    // ApiExtra 实现
    // ================================================================

    /**
     * 清理流式传输临时文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> cleanStreamTempFile(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "clean_stream_temp_file", p, VoidData.class);
    }

    /**
     * 下载语音文件流。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【可选】文件路径或 URL
     * @param fileId 【可选】文件 ID
     * @param chunkSize 【可选】分块大小 (字节)
     * @param outFormat 【可选】输出格式
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> downloadFileRecordStream(long botQQ, String file, String fileId, Integer chunkSize, String outFormat) {
        ObjectNode p = mapper.createObjectNode();
        if (file != null) p.put("file", file);
        if (fileId != null) p.put("file_id", fileId);
        if (chunkSize != null) p.put("chunk_size", chunkSize);
        if (outFormat != null) p.put("out_format", outFormat);
        return req(botQQ, "download_file_record_stream", p, VoidData.class);
    }

    /**
     * 下载图片文件流。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【可选】文件路径或 URL
     * @param fileId 【可选】文件 ID
     * @param chunkSize 【可选】分块大小 (字节)
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> downloadFileImageStream(long botQQ, String file, String fileId, Integer chunkSize) {
        ObjectNode p = mapper.createObjectNode();
        if (file != null) p.put("file", file);
        if (fileId != null) p.put("file_id", fileId);
        if (chunkSize != null) p.put("chunk_size", chunkSize);
        return req(botQQ, "download_file_image_stream", p, VoidData.class);
    }

    /**
     * 测试下载流。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param error 【可选】是否触发测试错误
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> testDownloadStream(long botQQ, Boolean error) {
        ObjectNode p = mapper.createObjectNode();
        if (error != null && error) p.put("error", true);
        return req(botQQ, "test_download_stream", p, VoidData.class);
    }

    /**
     * 下载文件流。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【可选】文件路径或 URL
     * @param fileId 【可选】文件 ID
     * @param chunkSize 【可选】分块大小 (字节)
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> downloadFileStream(long botQQ, String file, String fileId, Integer chunkSize) {
        ObjectNode p = mapper.createObjectNode();
        if (file != null) p.put("file", file);
        if (fileId != null) p.put("file_id", fileId);
        if (chunkSize != null) p.put("chunk_size", chunkSize);
        return req(botQQ, "download_file_stream", p, VoidData.class);
    }

    /**
     * 上传文件流。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param streamId 【必填】流 ID
     * @param chunkData 【可选】分块数据 (Base64)
     * @param chunkIndex 【可选】分块索引
     * @param totalChunks 【可选】总分块数
     * @param fileSize 【可选】文件总大小
     * @param expectedSha256 【可选】期望的 SHA256
     * @param isComplete 【可选】是否完成
     * @param filename 【可选】文件名
     * @param reset 【可选】是否重置
     * @param verifyOnly 【可选】是否仅验证
     * @param fileRetention 【必填】文件保留时间 (毫秒)
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> uploadFileStream(long botQQ, String streamId, String chunkData, Long chunkIndex, Long totalChunks, Integer fileSize, String expectedSha256, Boolean isComplete, String filename, Boolean reset, Boolean verifyOnly, Long fileRetention) {
        ObjectNode p = mapper.createObjectNode();
        p.put("stream_id", streamId);
        if (chunkData != null) p.put("chunk_data", chunkData);
        if (chunkIndex != null) p.put("chunk_index", chunkIndex);
        if (totalChunks != null) p.put("total_chunks", totalChunks);
        if (fileSize != null) p.put("file_size", fileSize);
        if (expectedSha256 != null) p.put("expected_sha256", expectedSha256);
        if (isComplete != null && isComplete) p.put("is_complete", true);
        if (filename != null) p.put("filename", filename);
        if (reset != null && reset) p.put("reset", true);
        if (verifyOnly != null && verifyOnly) p.put("verify_only", true);
        p.put("file_retention", fileRetention);
        return req(botQQ, "upload_file_stream", p, VoidData.class);
    }

    /**
     * 批量踢出群成员。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param userId 【必填】QQ号列表
     * @param rejectAddRequest 【可选】是否拒绝加群请求
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupKickMembers(long botQQ, Long groupId, List<Long> userId, String rejectAddRequest) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (userId != null) p.set("user_id", mapper.valueToTree(userId));
        if (rejectAddRequest != null) p.put("reject_add_request", rejectAddRequest);
        return req(botQQ, "set_group_kick_members", p, VoidData.class);
    }

    /**
     * 创建收藏。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param rawData 【必填】原始数据
     * @param brief 【必填】简要描述
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> createCollection(long botQQ, String rawData, String brief) {
        ObjectNode p = mapper.createObjectNode();
        p.put("rawData", rawData);
        p.put("brief", brief);
        return req(botQQ, "create_collection", p, VoidData.class);
    }

    /**
     * 设置个性签名。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param longNick 【必填】签名内容
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setSelfLongnick(long botQQ, String longNick) {
        ObjectNode p = mapper.createObjectNode();
        p.put("longNick", longNick);
        return req(botQQ, "set_self_longnick", p, VoidData.class);
    }

    /**
     * 设置QQ头像。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【必填】图片路径、URL或Base64
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setQqAvatar(long botQQ, String file) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file", file);
        return req(botQQ, "set_qq_avatar", p, VoidData.class);
    }

    /**
     * 英文单词翻译。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param words 【必填】待翻译单词列表
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<En2zhData>> translateEn2zh(long botQQ, List<String> words) {
        ObjectNode p = mapper.createObjectNode();
        if (words != null) p.set("words", mapper.valueToTree(words));
        return req(botQQ, "translate_en2zh", p, En2zhData.class);
    }

    /**
     * 获取ClientKey。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<ClientkeyData>> getClientkey(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "get_clientkey", p, ClientkeyData.class);
    }

    /**
     * 图片 OCR 识别。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param image 【必填】图片路径、URL或Base64
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> ocrImage(long botQQ, String image) {
        ObjectNode p = mapper.createObjectNode();
        p.put("image", image);
        return req(botQQ, "ocr_image", p, VoidData.class);
    }

    /**
     * 图片 OCR 识别 (内部)。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param image 【必填】图片路径、URL或Base64
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> ocrImageInternal(long botQQ, String image) {
        ObjectNode p = mapper.createObjectNode();
        p.put("image", image);
        return req(botQQ, "ocr_image", p, VoidData.class);
    }

    /**
     * 设置专属头衔。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param userId 【必填】QQ号
     * @param specialTitle 【必填】专属头衔
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupSpecialTitle(long botQQ, Long groupId, Long userId, String specialTitle) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("user_id", Long.toString(userId));
        p.put("special_title", specialTitle);
        return req(botQQ, "set_group_special_title", p, VoidData.class);
    }

    /**
     * 获取AI角色列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param chatType 【必填】聊天类型
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<AiCharactersData>>> getAiCharacters(long botQQ, Long groupId, String chatType) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("chat_type", chatType);
        return reqList(botQQ, "get_ai_characters", p, AiCharactersData.class);
    }

    /**
     * 设置QQ资料。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param nickname 【必填】昵称
     * @param personalNote 【可选】个性签名
     * @param sex 【可选】性别 (0: 未知, 1: 男, 2: 女)
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setQqProfile(long botQQ, String nickname, String personalNote, String sex) {
        ObjectNode p = mapper.createObjectNode();
        p.put("nickname", nickname);
        if (personalNote != null) p.put("personal_note", personalNote);
        if (sex != null) p.put("sex", sex);
        return req(botQQ, "set_qq_profile", p, VoidData.class);
    }

    /**
     * 获取群根目录文件列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param fileCount 【必填】文件数量
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupRootFilesData>> getGroupRootFiles(long botQQ, Long groupId, String fileCount) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("file_count", fileCount);
        return req(botQQ, "get_group_root_files", p, GroupRootFilesData.class);
    }

    /**
     * 删除好友。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param friendId 【可选】好友 QQ 号
     * @param userId 【可选】用户 QQ 号
     * @param tempBlock 【可选】是否加入黑名单
     * @param tempBothDel 【可选】是否双向删除
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<String>> deleteFriend(long botQQ, String friendId, Long userId, Boolean tempBlock, Boolean tempBothDel) {
        ObjectNode p = mapper.createObjectNode();
        if (friendId != null) p.put("friend_id", friendId);
        if (userId != null) p.put("user_id", Long.toString(userId));
        if (tempBlock != null && tempBlock) p.put("temp_block", true);
        if (tempBothDel != null && tempBothDel) p.put("temp_both_del", true);
        return req(botQQ, "delete_friend", p, String.class);
    }

    /**
     * 检查URL安全性。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param url 【必填】要检查的 URL
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<UrlSafelyData>> checkUrlSafely(long botQQ, String url) {
        ObjectNode p = mapper.createObjectNode();
        p.put("url", url);
        return req(botQQ, "check_url_safely", p, UrlSafelyData.class);
    }

    /**
     * 获取在线客户端。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<String>>> getOnlineClients(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return reqList(botQQ, "get_online_clients", p, String.class);
    }

    /**
     * 获取群荣誉信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param type 【可选】荣誉类型
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupHonorInfoData>> getGroupHonorInfo(long botQQ, Long groupId, String type) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (type != null) p.put("type", type);
        return req(botQQ, "get_group_honor_info", p, GroupHonorInfoData.class);
    }

    /**
     * 发送群公告。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param content 【必填】公告内容
     * @param image 【可选】公告图片路径或 URL
     * @param pinned 【必填】是否置顶 (0/1)
     * @param type 【必填】类型 (默认为 1)
     * @param confirmRequired 【必填】是否需要确认 (0/1)
     * @param isShowEditCard 【必填】是否显示修改群名片引导 (0/1)
     * @param tipWindowType 【必填】弹窗类型 (默认为 0)
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendGroupNotice(long botQQ, Long groupId, String content, String image, String pinned, String type, String confirmRequired, String isShowEditCard, String tipWindowType) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("content", content);
        if (image != null) p.put("image", image);
        p.put("pinned", pinned);
        p.put("type", type);
        p.put("confirm_required", confirmRequired);
        p.put("is_show_edit_card", isShowEditCard);
        p.put("tip_window_type", tipWindowType);
        return req(botQQ, "_send_group_notice", p, VoidData.class);
    }

    /**
     * 获取群艾特全体剩余次数。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupAtAllRemainData>> getGroupAtAllRemain(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return req(botQQ, "get_group_at_all_remain", p, GroupAtAllRemainData.class);
    }

    /**
     * 发送合并转发消息。
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
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<ForwardMsgData>> sendForwardMsg(long botQQ, String messageType, Long userId, Long groupId, String message, String autoEscape, String source, List<JsonNode> news, String summary, String prompt, Long timeout) {
        ObjectNode p = mapper.createObjectNode();
        if (messageType != null) p.put("message_type", messageType);
        if (userId != null) p.put("user_id", Long.toString(userId));
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        p.put("message", message);
        if (autoEscape != null) p.put("auto_escape", autoEscape);
        if (source != null) p.put("source", source);
        if (news != null) p.set("news", mapper.valueToTree(news));
        if (summary != null) p.put("summary", summary);
        if (prompt != null) p.put("prompt", prompt);
        if (timeout != null) p.put("timeout", timeout);
        return req(botQQ, "send_forward_msg", p, ForwardMsgData.class);
    }

    /**
     * 发送群合并转发消息。
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
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<ForwardMsgData>> sendGroupForwardMsg(long botQQ, String messageType, Long userId, Long groupId, String message, String autoEscape, String source, List<JsonNode> news, String summary, String prompt, Long timeout) {
        ObjectNode p = mapper.createObjectNode();
        if (messageType != null) p.put("message_type", messageType);
        if (userId != null) p.put("user_id", Long.toString(userId));
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        p.put("message", message);
        if (autoEscape != null) p.put("auto_escape", autoEscape);
        if (source != null) p.put("source", source);
        if (news != null) p.set("news", mapper.valueToTree(news));
        if (summary != null) p.put("summary", summary);
        if (prompt != null) p.put("prompt", prompt);
        if (timeout != null) p.put("timeout", timeout);
        return req(botQQ, "send_group_forward_msg", p, ForwardMsgData.class);
    }

    /**
     * 发送私聊合并转发消息。
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
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<ForwardMsgData>> sendPrivateForwardMsg(long botQQ, String messageType, Long userId, Long groupId, String message, String autoEscape, String source, List<JsonNode> news, String summary, String prompt, Long timeout) {
        ObjectNode p = mapper.createObjectNode();
        if (messageType != null) p.put("message_type", messageType);
        if (userId != null) p.put("user_id", Long.toString(userId));
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        p.put("message", message);
        if (autoEscape != null) p.put("auto_escape", autoEscape);
        if (source != null) p.put("source", source);
        if (news != null) p.set("news", mapper.valueToTree(news));
        if (summary != null) p.put("summary", summary);
        if (prompt != null) p.put("prompt", prompt);
        if (timeout != null) p.put("timeout", timeout);
        return req(botQQ, "send_private_forward_msg", p, ForwardMsgData.class);
    }

    /**
     * 获取陌生人信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户QQ
     * @param noCache 【必填】是否不使用缓存
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<StrangerInfoData>> getStrangerInfo(long botQQ, Long userId, String noCache) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", Long.toString(userId));
        p.put("no_cache", noCache);
        return req(botQQ, "get_stranger_info", p, StrangerInfoData.class);
    }

    /**
     * 下载文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param url 【可选】下载链接
     * @param base64 【可选】base64数据
     * @param name 【可选】文件名
     * @param headers 【可选】请求头
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<DownloadFileData>> downloadFile(long botQQ, String url, String base64, String name, String headers) {
        ObjectNode p = mapper.createObjectNode();
        if (url != null) p.put("url", url);
        if (base64 != null) p.put("base64", base64);
        if (name != null) p.put("name", name);
        if (headers != null) p.put("headers", headers);
        return req(botQQ, "download_file", p, DownloadFileData.class);
    }

    /**
     * 上传群文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param file 【必填】资源路径或URL
     * @param name 【必填】文件名
     * @param folder 【可选】父目录 ID
     * @param folderId 【可选】父目录 ID (兼容性字段)
     * @param uploadFile 【必填】是否执行上传
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupFileData>> uploadGroupFile(long botQQ, Long groupId, String file, String name, String folder, String folderId, Boolean uploadFile) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("file", file);
        p.put("name", name);
        if (folder != null) p.put("folder", folder);
        if (folderId != null) p.put("folder_id", folderId);
        p.put("upload_file", uploadFile);
        return req(botQQ, "upload_group_file", p, GroupFileData.class);
    }

    /**
     * 获取群历史消息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param messageSeq 【可选】起始消息序号
     * @param count 【必填】获取消息数量
     * @param reverseOrder 【必填】是否反向排序
     * @param disableGetUrl 【必填】是否禁用获取URL
     * @param parseMultMsg 【必填】是否解析合并消息
     * @param quickReply 【必填】是否快速回复
     * @param reverseOrder2 【必填】是否反向排序(旧版本兼容)
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupMsgHistoryData>> getGroupMsgHistory(long botQQ, Long groupId, String messageSeq, Integer count, Boolean reverseOrder, Boolean disableGetUrl, Boolean parseMultMsg, Boolean quickReply, Boolean reverseOrder2) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (messageSeq != null) p.put("message_seq", messageSeq);
        p.put("count", count);
        p.put("reverse_order", reverseOrder);
        p.put("disable_get_url", disableGetUrl);
        p.put("parse_mult_msg", parseMultMsg);
        p.put("quick_reply", quickReply);
        p.put("reverseOrder", reverseOrder2);
        return req(botQQ, "get_group_msg_history", p, GroupMsgHistoryData.class);
    }

    /**
     * 获取好友历史消息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户QQ
     * @param messageSeq 【可选】起始消息序号
     * @param count 【必填】获取消息数量
     * @param reverseOrder 【必填】是否反向排序
     * @param disableGetUrl 【必填】是否禁用获取URL
     * @param parseMultMsg 【必填】是否解析合并消息
     * @param quickReply 【必填】是否快速回复
     * @param reverseOrder2 【必填】是否反向排序(旧版本兼容)
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupMsgHistoryData>> getFriendMsgHistory(long botQQ, Long userId, String messageSeq, Integer count, Boolean reverseOrder, Boolean disableGetUrl, Boolean parseMultMsg, Boolean quickReply, Boolean reverseOrder2) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", Long.toString(userId));
        if (messageSeq != null) p.put("message_seq", messageSeq);
        p.put("count", count);
        p.put("reverse_order", reverseOrder);
        p.put("disable_get_url", disableGetUrl);
        p.put("parse_mult_msg", parseMultMsg);
        p.put("quick_reply", quickReply);
        p.put("reverseOrder", reverseOrder2);
        return req(botQQ, "get_friend_msg_history", p, GroupMsgHistoryData.class);
    }

    /**
     * 处理快速操作。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param context 【必填】事件上下文
     * @param operation 【必填】快速操作内容
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> handleQuickOperationInternal(long botQQ, JsonNode context, JsonNode operation) {
        ObjectNode p = mapper.createObjectNode();
        if (context != null) p.set("context", context);
        if (operation != null) p.set("operation", operation);
        return req(botQQ, "handle_quick_operation", p, VoidData.class);
    }

    /**
     * 设置群头像。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【必填】头像文件路径或 URL
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupPortraitData>> setGroupPortrait(long botQQ, String file, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file", file);
        p.put("group_id", Long.toString(groupId));
        return req(botQQ, "set_group_portrait", p, GroupPortraitData.class);
    }

    /**
     * 上传私聊文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @param file 【必填】资源路径或URL
     * @param name 【必填】文件名
     * @param uploadFile 【必填】是否执行上传
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupFileData>> uploadPrivateFile(long botQQ, Long userId, String file, String name, Boolean uploadFile) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", Long.toString(userId));
        p.put("file", file);
        p.put("name", name);
        p.put("upload_file", uploadFile);
        return req(botQQ, "upload_private_file", p, GroupFileData.class);
    }

    /**
     * 获取机型显示。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param model 【可选】模型名称
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<ModelShowData>>> getModelShow(long botQQ, String model) {
        ObjectNode p = mapper.createObjectNode();
        if (model != null) p.put("model", model);
        return reqList(botQQ, "_get_model_show", p, ModelShowData.class);
    }

    /**
     * 设置机型。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setModelShow(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "_set_model_show", p, VoidData.class);
    }

    /**
     * 删除群文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param fileId 【必填】文件ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteGroupFile(long botQQ, Long groupId, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("file_id", fileId);
        return req(botQQ, "delete_group_file", p, VoidData.class);
    }

    /**
     * 创建群文件目录。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param folderName 【可选】文件夹名称
     * @param name 【可选】文件夹名称
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupFileFolderData>> createGroupFileFolder(long botQQ, Long groupId, String folderName, String name) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (folderName != null) p.put("folder_name", folderName);
        if (name != null) p.put("name", name);
        return req(botQQ, "create_group_file_folder", p, GroupFileFolderData.class);
    }

    /**
     * 删除群文件目录。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param folderId 【可选】文件夹ID
     * @param folder 【可选】文件夹ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteGroupFolder(long botQQ, Long groupId, String folderId, String folder) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (folderId != null) p.put("folder_id", folderId);
        if (folder != null) p.put("folder", folder);
        return req(botQQ, "delete_group_folder", p, VoidData.class);
    }

    /**
     * 获取群文件系统信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupFileSystemInfoData>> getGroupFileSystemInfo(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return req(botQQ, "get_group_file_system_info", p, GroupFileSystemInfoData.class);
    }

    /**
     * 获取群文件夹文件列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param folderId 【可选】文件夹ID
     * @param folder 【可选】文件夹ID
     * @param fileCount 【必填】文件数量
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupRootFilesData>> getGroupFilesByFolder(long botQQ, Long groupId, String folderId, String folder, String fileCount) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (folderId != null) p.put("folder_id", folderId);
        if (folder != null) p.put("folder", folder);
        p.put("file_count", fileCount);
        return req(botQQ, "get_group_files_by_folder", p, GroupRootFilesData.class);
    }

    /**
     * 获取频道列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> getGuildList(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "get_guild_list", p, VoidData.class);
    }

    /**
     * 获取频道个人信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> getGuildServiceProfile(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "get_guild_service_profile", p, VoidData.class);
    }

    /**
     * 获取 AI 语音。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param character 【必填】角色ID
     * @param groupId 【必填】群号
     * @param text 【必填】语音文本内容
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<String>> getAiRecord(long botQQ, String character, Long groupId, String text) {
        ObjectNode p = mapper.createObjectNode();
        p.put("character", character);
        p.put("group_id", Long.toString(groupId));
        p.put("text", text);
        return req(botQQ, "get_ai_record", p, String.class);
    }

    /**
     * 发送群 AI 语音。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param character 【必填】角色ID
     * @param groupId 【必填】群号
     * @param text 【必填】语音文本内容
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupAiRecordData>> sendGroupAiRecord(long botQQ, String character, Long groupId, String text) {
        ObjectNode p = mapper.createObjectNode();
        p.put("character", character);
        p.put("group_id", Long.toString(groupId));
        p.put("text", text);
        return req(botQQ, "send_group_ai_record", p, GroupAiRecordData.class);
    }

    // ================================================================
    // ApiFile 实现
    // ================================================================

    /**
     * 获取文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【可选】文件路径、URL或Base64
     * @param fileId 【可选】文件ID
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<FileData>> getFile(long botQQ, String file, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        if (file != null) p.put("file", file);
        if (fileId != null) p.put("file_id", fileId);
        return req(botQQ, "get_file", p, FileData.class);
    }

    /**
     * 获取图片。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【可选】文件路径、URL或Base64
     * @param fileId 【可选】文件ID
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<FileData>> getImage(long botQQ, String file, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        if (file != null) p.put("file", file);
        if (fileId != null) p.put("file_id", fileId);
        return req(botQQ, "get_image", p, FileData.class);
    }

    /**
     * 获取语音。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【可选】文件路径、URL或Base64
     * @param fileId 【可选】文件ID
     * @param outFormat 【必填】输出格式
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<FileData>> getRecord(long botQQ, String file, String fileId, String outFormat) {
        ObjectNode p = mapper.createObjectNode();
        if (file != null) p.put("file", file);
        if (fileId != null) p.put("file_id", fileId);
        p.put("out_format", outFormat);
        return req(botQQ, "get_record", p, FileData.class);
    }

    /**
     * 获取群文件URL。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param fileId 【必填】文件ID
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupFileUrlData>> getGroupFileUrl(long botQQ, Long groupId, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("file_id", fileId);
        return req(botQQ, "get_group_file_url", p, GroupFileUrlData.class);
    }

    /**
     * 获取私聊文件URL。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param fileId 【必填】文件ID
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupFileUrlData>> getPrivateFileUrl(long botQQ, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file_id", fileId);
        return req(botQQ, "get_private_file_url", p, GroupFileUrlData.class);
    }

    /**
     * 移动群文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param fileId 【必填】文件ID
     * @param currentParentDirectory 【必填】当前父目录
     * @param targetParentDirectory 【必填】目标父目录
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupFileData>> moveGroupFile(long botQQ, Long groupId, String fileId, String currentParentDirectory, String targetParentDirectory) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("file_id", fileId);
        p.put("current_parent_directory", currentParentDirectory);
        p.put("target_parent_directory", targetParentDirectory);
        return req(botQQ, "move_group_file", p, GroupFileData.class);
    }

    /**
     * 重命名群文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param fileId 【必填】文件ID
     * @param currentParentDirectory 【必填】当前父目录
     * @param newName 【必填】新文件名
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupFileData>> renameGroupFile(long botQQ, Long groupId, String fileId, String currentParentDirectory, String newName) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("file_id", fileId);
        p.put("current_parent_directory", currentParentDirectory);
        p.put("new_name", newName);
        return req(botQQ, "rename_group_file", p, GroupFileData.class);
    }

    /**
     * 传输群文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param fileId 【必填】文件ID
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupFileData>> transGroupFile(long botQQ, Long groupId, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("file_id", fileId);
        return req(botQQ, "trans_group_file", p, GroupFileData.class);
    }

    /**
     * 创建闪传任务。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param files 【必填】文件列表或单个文件路径
     * @param name 【可选】任务名称
     * @param thumbPath 【可选】缩略图路径
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> createFlashTask(long botQQ, String files, String name, String thumbPath) {
        ObjectNode p = mapper.createObjectNode();
        p.put("files", files);
        if (name != null) p.put("name", name);
        if (thumbPath != null) p.put("thumb_path", thumbPath);
        return req(botQQ, "create_flash_task", p, VoidData.class);
    }

    /**
     * 获取闪传文件列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param filesetId 【必填】文件集 ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> getFlashFileList(long botQQ, String filesetId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("fileset_id", filesetId);
        return req(botQQ, "get_flash_file_list", p, VoidData.class);
    }

    /**
     * 获取闪传文件链接。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param filesetId 【必填】文件集 ID
     * @param fileName 【可选】文件名
     * @param fileIndex 【可选】文件索引
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> getFlashFileUrl(long botQQ, String filesetId, String fileName, Long fileIndex) {
        ObjectNode p = mapper.createObjectNode();
        p.put("fileset_id", filesetId);
        if (fileName != null) p.put("file_name", fileName);
        if (fileIndex != null) p.put("file_index", fileIndex);
        return req(botQQ, "get_flash_file_url", p, VoidData.class);
    }

    /**
     * 发送闪传消息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param filesetId 【必填】文件集 ID
     * @param userId 【可选】用户 QQ
     * @param groupId 【可选】群号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendFlashMsg(long botQQ, String filesetId, Long userId, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("fileset_id", filesetId);
        if (userId != null) p.put("user_id", Long.toString(userId));
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        return req(botQQ, "send_flash_msg", p, VoidData.class);
    }

    /**
     * 获取文件分享链接。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param filesetId 【必填】文件集 ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> getShareLink(long botQQ, String filesetId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("fileset_id", filesetId);
        return req(botQQ, "get_share_link", p, VoidData.class);
    }

    /**
     * 获取文件集信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param filesetId 【必填】文件集 ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> getFilesetInfo(long botQQ, String filesetId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("fileset_id", filesetId);
        return req(botQQ, "get_fileset_info", p, VoidData.class);
    }

    /**
     * 获取在线文件消息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> getOnlineFileMsg(long botQQ, Long userId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", Long.toString(userId));
        return req(botQQ, "get_online_file_msg", p, VoidData.class);
    }

    /**
     * 发送在线文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @param filePath 【必填】本地文件路径
     * @param fileName 【可选】文件名 (可选)
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendOnlineFile(long botQQ, Long userId, String filePath, String fileName) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", Long.toString(userId));
        p.put("file_path", filePath);
        if (fileName != null) p.put("file_name", fileName);
        return req(botQQ, "send_online_file", p, VoidData.class);
    }

    /**
     * 发送在线文件夹。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @param folderPath 【必填】本地文件夹路径
     * @param folderName 【可选】文件夹名称 (可选)
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendOnlineFolder(long botQQ, Long userId, String folderPath, String folderName) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", Long.toString(userId));
        p.put("folder_path", folderPath);
        if (folderName != null) p.put("folder_name", folderName);
        return req(botQQ, "send_online_folder", p, VoidData.class);
    }

    /**
     * 接收在线文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @param msgId 【必填】消息 ID
     * @param elementId 【必填】元素 ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> receiveOnlineFile(long botQQ, Long userId, String msgId, String elementId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", Long.toString(userId));
        p.put("msg_id", msgId);
        p.put("element_id", elementId);
        return req(botQQ, "receive_online_file", p, VoidData.class);
    }

    /**
     * 拒绝在线文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @param msgId 【必填】消息 ID
     * @param elementId 【必填】元素 ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> refuseOnlineFile(long botQQ, Long userId, String msgId, String elementId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", Long.toString(userId));
        p.put("msg_id", msgId);
        p.put("element_id", elementId);
        return req(botQQ, "refuse_online_file", p, VoidData.class);
    }

    /**
     * 取消在线文件。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @param msgId 【必填】消息 ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> cancelOnlineFile(long botQQ, Long userId, String msgId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", Long.toString(userId));
        p.put("msg_id", msgId);
        return req(botQQ, "cancel_online_file", p, VoidData.class);
    }

    /**
     * 下载文件集。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param filesetId 【必填】文件集 ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> downloadFileset(long botQQ, String filesetId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("fileset_id", filesetId);
        return req(botQQ, "download_fileset", p, VoidData.class);
    }

    /**
     * 获取文件集 ID。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param shareCode 【必填】分享码或分享链接
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<FilesetIdData>> getFilesetId(long botQQ, String shareCode) {
        ObjectNode p = mapper.createObjectNode();
        p.put("share_code", shareCode);
        return req(botQQ, "get_fileset_id", p, FilesetIdData.class);
    }

    // ================================================================
    // ApiFriend 实现
    // ================================================================

    /**
     * 设置好友备注。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】对方 QQ 号
     * @param remark 【必填】备注内容
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setFriendRemark(long botQQ, Long userId, String remark) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", Long.toString(userId));
        p.put("remark", remark);
        return req(botQQ, "set_friend_remark", p, VoidData.class);
    }

    /**
     * 获取好友列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param noCache 【可选】是否不使用缓存
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<String>>> getFriendList(long botQQ, String noCache) {
        ObjectNode p = mapper.createObjectNode();
        if (noCache != null) p.put("no_cache", noCache);
        return reqList(botQQ, "get_friend_list", p, String.class);
    }

    /**
     * 处理加好友请求。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param flag 【必填】加好友请求的 flag (需从上报中获取)
     * @param approve 【可选】是否同意请求
     * @param remark 【可选】添加后的好友备注
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setFriendAddRequest(long botQQ, String flag, String approve, String remark) {
        ObjectNode p = mapper.createObjectNode();
        p.put("flag", flag);
        if (approve != null) p.put("approve", approve);
        if (remark != null) p.put("remark", remark);
        return req(botQQ, "set_friend_add_request", p, VoidData.class);
    }

    /**
     * 获取 Cookies。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param domain 【必填】需要获取 cookies 的域名
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<CookiesData>> getCookies(long botQQ, String domain) {
        ObjectNode p = mapper.createObjectNode();
        p.put("domain", domain);
        return req(botQQ, "get_cookies", p, CookiesData.class);
    }

    /**
     * 获取最近会话。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 【必填】获取的数量
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<RecentContactData>>> getRecentContact(long botQQ, String count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("count", count);
        return reqList(botQQ, "get_recent_contact", p, RecentContactData.class);
    }

    /**
     * 获取带分组的好友列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<FriendsWithCategoryData>>> getFriendsWithCategory(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return reqList(botQQ, "get_friends_with_category", p, FriendsWithCategoryData.class);
    }

    /**
     * 获取资料点赞。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【可选】QQ号
     * @param start 【必填】起始位置
     * @param count 【必填】获取数量
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<ProfileLikeData>> getProfileLike(long botQQ, Long userId, String start, String count) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", Long.toString(userId));
        p.put("start", start);
        p.put("count", count);
        return req(botQQ, "get_profile_like", p, ProfileLikeData.class);
    }

    /**
     * 设置自定义在线状态。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param faceId 【必填】图标ID
     * @param faceType 【必填】图标类型
     * @param wording 【必填】状态文字内容
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<String>> setDiyOnlineStatus(long botQQ, String faceId, String faceType, String wording) {
        ObjectNode p = mapper.createObjectNode();
        p.put("face_id", faceId);
        p.put("face_type", faceType);
        p.put("wording", wording);
        return req(botQQ, "set_diy_online_status", p, String.class);
    }

    /**
     * 获取单向好友列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<UnidirectionalFriendData>>> getUnidirectionalFriendList(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return reqList(botQQ, "get_unidirectional_friend_list", p, UnidirectionalFriendData.class);
    }

    // ================================================================
    // ApiGroup 实现
    // ================================================================

    /**
     * 删除群相册媒体。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param albumId 【必填】相册ID
     * @param lloc 【必填】媒体ID (lloc)
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> delGroupAlbumMedia(long botQQ, Long groupId, String albumId, String lloc) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("album_id", albumId);
        p.put("lloc", lloc);
        return req(botQQ, "del_group_album_media", p, VoidData.class);
    }

    /**
     * 点赞群相册媒体。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param albumId 【必填】相册ID
     * @param batchId 【必填】batch_id
     * @param lloc 【可选】lloc，若对整个上传操作则不填
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupAlbumMediaLike(long botQQ, Long groupId, String albumId, String batchId, String lloc) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("album_id", albumId);
        p.put("batch_id", batchId);
        if (lloc != null) p.put("lloc", lloc);
        return req(botQQ, "set_group_album_media_like", p, VoidData.class);
    }

    /**
     * 取消点赞群相册媒体。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param albumId 【必填】相册ID
     * @param batchId 【必填】batch_id
     * @param lloc 【可选】lloc，若对整个上传操作则不填
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> cancelGroupAlbumMediaLike(long botQQ, Long groupId, String albumId, String batchId, String lloc) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("album_id", albumId);
        p.put("batch_id", batchId);
        if (lloc != null) p.put("lloc", lloc);
        return req(botQQ, "cancel_group_album_media_like", p, VoidData.class);
    }

    /**
     * 发表群相册评论。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param albumId 【必填】相册 ID
     * @param lloc 【必填】图片 ID
     * @param content 【必填】评论内容
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> doGroupAlbumComment(long botQQ, Long groupId, String albumId, String lloc, String content) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("album_id", albumId);
        p.put("lloc", lloc);
        p.put("content", content);
        return req(botQQ, "do_group_album_comment", p, VoidData.class);
    }

    /**
     * 获取群相册媒体列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param albumId 【必填】相册ID
     * @param attachInfo 【可选】附加信息（用于分页）
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> getGroupAlbumMediaList(long botQQ, Long groupId, String albumId, String attachInfo) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("album_id", albumId);
        if (attachInfo != null) p.put("attach_info", attachInfo);
        return req(botQQ, "get_group_album_media_list", p, VoidData.class);
    }

    /**
     * 获取群相册列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param attachInfo 【可选】附加信息（用于分页，从上一次返回结果中获取）
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<QunAlbumData>> getQunAlbumList(long botQQ, Long groupId, String attachInfo) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (attachInfo != null) p.put("attach_info", attachInfo);
        return req(botQQ, "get_qun_album_list", p, QunAlbumData.class);
    }

    /**
     * 上传图片到群相册。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param albumId 【必填】相册ID
     * @param albumName 【必填】相册名称
     * @param file 【必填】图片路径、URL或Base64
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> uploadImageToQunAlbum(long botQQ, Long groupId, String albumId, String albumName, String file) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("album_id", albumId);
        p.put("album_name", albumName);
        p.put("file", file);
        return req(botQQ, "upload_image_to_qun_album", p, VoidData.class);
    }

    /**
     * 设置群加群选项。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param addType 【必填】加群方式
     * @param groupQuestion 【可选】加群问题
     * @param groupAnswer 【可选】加群答案
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupAddOption(long botQQ, Long groupId, Long addType, String groupQuestion, String groupAnswer) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("add_type", addType);
        if (groupQuestion != null) p.put("group_question", groupQuestion);
        if (groupAnswer != null) p.put("group_answer", groupAnswer);
        return req(botQQ, "set_group_add_option", p, VoidData.class);
    }

    /**
     * 设置群机器人加群选项。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param robotMemberSwitch 【可选】机器人成员开关
     * @param robotMemberExamine 【可选】机器人成员审核
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupRobotAddOption(long botQQ, Long groupId, Long robotMemberSwitch, Long robotMemberExamine) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (robotMemberSwitch != null) p.put("robot_member_switch", robotMemberSwitch);
        if (robotMemberExamine != null) p.put("robot_member_examine", robotMemberExamine);
        return req(botQQ, "set_group_robot_add_option", p, VoidData.class);
    }

    /**
     * 设置群搜索选项。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param noCodeFingerOpen 【可选】未知
     * @param noFingerOpen 【可选】未知
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupSearch(long botQQ, Long groupId, Long noCodeFingerOpen, Long noFingerOpen) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (noCodeFingerOpen != null) p.put("no_code_finger_open", noCodeFingerOpen);
        if (noFingerOpen != null) p.put("no_finger_open", noFingerOpen);
        return req(botQQ, "set_group_search", p, VoidData.class);
    }

    /**
     * 设置群备注。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param remark 【必填】备注
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupRemark(long botQQ, Long groupId, String remark) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("remark", remark);
        return req(botQQ, "set_group_remark", p, VoidData.class);
    }

    /**
     * 获取群详细信息 (扩展)。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> getGroupInfoEx(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return req(botQQ, "get_group_info_ex", p, VoidData.class);
    }

    /**
     * 群打卡。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupSign(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return req(botQQ, "set_group_sign", p, VoidData.class);
    }

    /**
     * 群打卡。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendGroupSign(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return req(botQQ, "send_group_sign", p, VoidData.class);
    }

    /**
     * 获取群组今日打卡列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<GroupSignedData>>> getGroupSignedList(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return reqList(botQQ, "get_group_signed_list", p, GroupSignedData.class);
    }

    /**
     * 获取群详细信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupDetailInfoData>> getGroupDetailInfo(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return req(botQQ, "get_group_detail_info", p, GroupDetailInfoData.class);
    }

    /**
     * 获取群列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param noCache 【可选】是否不使用缓存
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<String>>> getGroupList(long botQQ, String noCache) {
        ObjectNode p = mapper.createObjectNode();
        if (noCache != null) p.put("no_cache", noCache);
        return reqList(botQQ, "get_group_list", p, String.class);
    }

    /**
     * 获取群信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<String>> getGroupInfo(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return req(botQQ, "get_group_info", p, String.class);
    }

    /**
     * 获取群成员列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param noCache 【可选】是否不使用缓存
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<String>>> getGroupMemberList(long botQQ, Long groupId, String noCache) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (noCache != null) p.put("no_cache", noCache);
        return reqList(botQQ, "get_group_member_list", p, String.class);
    }

    /**
     * 获取群成员信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param userId 【必填】QQ号
     * @param noCache 【可选】是否不使用缓存
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<String>> getGroupMemberInfo(long botQQ, Long groupId, Long userId, String noCache) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("user_id", Long.toString(userId));
        if (noCache != null) p.put("no_cache", noCache);
        return req(botQQ, "get_group_member_info", p, String.class);
    }

    /**
     * 处理加群请求。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param flag 【必填】请求flag
     * @param approve 【可选】是否同意
     * @param reason 【可选】拒绝理由
     * @param count 【可选】搜索通知数量
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupAddRequest(long botQQ, String flag, String approve, String reason, Integer count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("flag", flag);
        if (approve != null) p.put("approve", approve);
        if (reason != null) p.put("reason", reason);
        if (count != null) p.put("count", count);
        return req(botQQ, "set_group_add_request", p, VoidData.class);
    }

    /**
     * 退出群组。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param isDismiss 【可选】是否解散
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupLeave(long botQQ, Long groupId, String isDismiss) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (isDismiss != null) p.put("is_dismiss", isDismiss);
        return req(botQQ, "set_group_leave", p, VoidData.class);
    }

    /**
     * 设置群名称。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param groupName 【必填】群名称
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupName(long botQQ, Long groupId, String groupName) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("group_name", groupName);
        return req(botQQ, "set_group_name", p, VoidData.class);
    }

    /**
     * 设置群名片。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param userId 【必填】用户QQ
     * @param card 【可选】群名片
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupCard(long botQQ, Long groupId, Long userId, String card) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("user_id", Long.toString(userId));
        if (card != null) p.put("card", card);
        return req(botQQ, "set_group_card", p, VoidData.class);
    }

    /**
     * 获取群公告。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<GroupNoticeData>>> getGroupNotice(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return reqList(botQQ, "_get_group_notice", p, GroupNoticeData.class);
    }

    /**
     * 获取群精华消息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<EssenceMsgData>>> getEssenceMsgList(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return reqList(botQQ, "get_essence_msg_list", p, EssenceMsgData.class);
    }

    /**
     * 获取群忽略通知。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupIgnoredNotifiesData>> getGroupIgnoredNotifies(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "get_group_ignored_notifies", p, GroupIgnoredNotifiesData.class);
    }

    /**
     * 移出精华消息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【可选】消息ID
     * @param msgSeq 【可选】消息序号
     * @param msgRandom 【可选】消息随机数
     * @param groupId 【可选】群号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteEssenceMsg(long botQQ, String messageId, String msgSeq, String msgRandom, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        if (messageId != null) p.put("message_id", messageId);
        if (msgSeq != null) p.put("msg_seq", msgSeq);
        if (msgRandom != null) p.put("msg_random", msgRandom);
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        return req(botQQ, "delete_essence_msg", p, VoidData.class);
    }

    /**
     * 设置精华消息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setEssenceMsg(long botQQ, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        return req(botQQ, "set_essence_msg", p, VoidData.class);
    }

    /**
     * 删除群公告。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param noticeId 【必填】公告ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> delGroupNotice(long botQQ, Long groupId, String noticeId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("notice_id", noticeId);
        return req(botQQ, "_del_group_notice", p, VoidData.class);
    }

    /**
     * 获取群禁言列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<String>>> getGroupShutList(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return reqList(botQQ, "get_group_shut_list", p, String.class);
    }

    /**
     * 获取群被忽略的加群请求。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<GroupIgnoreAddRequestData>>> getGroupIgnoreAddRequest(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return reqList(botQQ, "get_group_ignore_add_request", p, GroupIgnoreAddRequestData.class);
    }

    // ================================================================
    // ApiMessage 实现
    // ================================================================

    /**
     * 设置群待办。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param messageId 【可选】消息ID
     * @param messageSeq 【可选】消息Seq (可选)
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupTodo(long botQQ, Long groupId, String messageId, String messageSeq) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (messageId != null) p.put("message_id", messageId);
        if (messageSeq != null) p.put("message_seq", messageSeq);
        return req(botQQ, "set_group_todo", p, VoidData.class);
    }

    /**
     * 完成群待办。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param messageId 【可选】消息ID
     * @param messageSeq 【可选】消息Seq (可选)
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> completeGroupTodo(long botQQ, Long groupId, String messageId, String messageSeq) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (messageId != null) p.put("message_id", messageId);
        if (messageSeq != null) p.put("message_seq", messageSeq);
        return req(botQQ, "complete_group_todo", p, VoidData.class);
    }

    /**
     * 取消群待办。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param messageId 【可选】消息ID
     * @param messageSeq 【可选】消息Seq (可选)
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> cancelGroupTodo(long botQQ, Long groupId, String messageId, String messageSeq) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        if (messageId != null) p.put("message_id", messageId);
        if (messageSeq != null) p.put("message_seq", messageSeq);
        return req(botQQ, "cancel_group_todo", p, VoidData.class);
    }

    /**
     * 发送戳一戳。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【可选】群号
     * @param userId 【必填】用户QQ
     * @param targetId 【可选】目标QQ
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> groupPoke(long botQQ, Long groupId, Long userId, String targetId) {
        ObjectNode p = mapper.createObjectNode();
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        p.put("user_id", Long.toString(userId));
        if (targetId != null) p.put("target_id", targetId);
        return req(botQQ, "group_poke", p, VoidData.class);
    }

    /**
     * 发送戳一戳。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【可选】群号
     * @param userId 【必填】用户QQ
     * @param targetId 【可选】目标QQ
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> friendPoke(long botQQ, Long groupId, Long userId, String targetId) {
        ObjectNode p = mapper.createObjectNode();
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        p.put("user_id", Long.toString(userId));
        if (targetId != null) p.put("target_id", targetId);
        return req(botQQ, "friend_poke", p, VoidData.class);
    }

    /**
     * 发送戳一戳。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【可选】群号
     * @param userId 【必填】用户QQ
     * @param targetId 【可选】目标QQ
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendPoke(long botQQ, Long groupId, Long userId, String targetId) {
        ObjectNode p = mapper.createObjectNode();
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        p.put("user_id", Long.toString(userId));
        if (targetId != null) p.put("target_id", targetId);
        return req(botQQ, "send_poke", p, VoidData.class);
    }

    /**
     * 获取表情点赞详情。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @param emojiId 【必填】表情ID
     * @param emojiType 【必填】表情类型
     * @param count 【必填】获取数量
     * @param cookie 【必填】分页Cookie
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<EmojiLikeData>> fetchEmojiLike(long botQQ, String messageId, String emojiId, String emojiType, String count, String cookie) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        p.put("emojiId", emojiId);
        p.put("emojiType", emojiType);
        p.put("count", count);
        p.put("cookie", cookie);
        return req(botQQ, "fetch_emoji_like", p, EmojiLikeData.class);
    }

    /**
     * 获取消息表情点赞列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【可选】群号，短ID可不传
     * @param messageId 【必填】消息ID，可以传递长ID或短ID
     * @param emojiId 【必填】表情ID
     * @param emojiType 【可选】表情类型
     * @param count 【必填】数量，0代表全部
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<EmojiLikesData>> getEmojiLikes(long botQQ, Long groupId, String messageId, String emojiId, String emojiType, Integer count) {
        ObjectNode p = mapper.createObjectNode();
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        p.put("message_id", messageId);
        p.put("emoji_id", emojiId);
        if (emojiType != null) p.put("emoji_type", emojiType);
        p.put("count", count);
        return req(botQQ, "get_emoji_likes", p, EmojiLikesData.class);
    }

    /**
     * 获取语音转文字结果。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<PttTextData>> fetchPttText(long botQQ, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        return req(botQQ, "fetch_ptt_text", p, PttTextData.class);
    }

    /**
     * 分享群 (Ark)。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<String>> arksharegroup(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return req(botQQ, "ArkShareGroup", p, String.class);
    }

    /**
     * 分享用户 (Ark)。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【可选】QQ号
     * @param groupId 【可选】群号
     * @param phoneNumber 【必填】手机号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> arksharepeer(long botQQ, Long userId, Long groupId, String phoneNumber) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", Long.toString(userId));
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        p.put("phone_number", phoneNumber);
        return req(botQQ, "ArkSharePeer", p, VoidData.class);
    }

    /**
     * 分享群 (Ark)。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<String>> sendGroupArkShare(long botQQ, Long groupId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        return req(botQQ, "send_group_ark_share", p, String.class);
    }

    /**
     * 分享用户 (Ark)。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【可选】QQ号
     * @param groupId 【可选】群号
     * @param phoneNumber 【必填】手机号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendArkShare(long botQQ, Long userId, Long groupId, String phoneNumber) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", Long.toString(userId));
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        p.put("phone_number", phoneNumber);
        return req(botQQ, "send_ark_share", p, VoidData.class);
    }

    /**
     * 设置消息表情点赞。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @param emojiId 【必填】表情ID
     * @param set 【可选】是否设置
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setMsgEmojiLike(long botQQ, String messageId, String emojiId, String set) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        p.put("emoji_id", emojiId);
        if (set != null) p.put("set", set);
        return req(botQQ, "set_msg_emoji_like", p, VoidData.class);
    }

    /**
     * 点击内联键盘按钮。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param botAppid 【必填】机器人AppID
     * @param buttonId 【必填】按钮ID
     * @param callbackData 【必填】回调数据
     * @param msgSeq 【必填】消息序列号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> clickInlineKeyboardButton(long botQQ, Long groupId, String botAppid, String buttonId, String callbackData, String msgSeq) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupId));
        p.put("bot_appid", botAppid);
        p.put("button_id", buttonId);
        p.put("callback_data", callbackData);
        p.put("msg_seq", msgSeq);
        return req(botQQ, "click_inline_keyboard_button", p, VoidData.class);
    }

    /**
     * 转发单条消息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @param groupId 【可选】目标群号
     * @param userId 【可选】目标用户QQ
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> forwardFriendSingleMsg(long botQQ, String messageId, Long groupId, Long userId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        if (userId != null) p.put("user_id", Long.toString(userId));
        return req(botQQ, "forward_friend_single_msg", p, VoidData.class);
    }

    /**
     * 转发单条消息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @param groupId 【可选】目标群号
     * @param userId 【可选】目标用户QQ
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> forwardGroupSingleMsg(long botQQ, String messageId, Long groupId, Long userId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        if (userId != null) p.put("user_id", Long.toString(userId));
        return req(botQQ, "forward_group_single_msg", p, VoidData.class);
    }

    /**
     * 标记群聊已读。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【可选】用户QQ
     * @param groupId 【可选】群号
     * @param messageId 【可选】消息ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> markGroupMsgAsRead(long botQQ, Long userId, Long groupId, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", Long.toString(userId));
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        if (messageId != null) p.put("message_id", messageId);
        return req(botQQ, "mark_group_msg_as_read", p, VoidData.class);
    }

    /**
     * 标记私聊已读。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【可选】用户QQ
     * @param groupId 【可选】群号
     * @param messageId 【可选】消息ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> markPrivateMsgAsRead(long botQQ, Long userId, Long groupId, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", Long.toString(userId));
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        if (messageId != null) p.put("message_id", messageId);
        return req(botQQ, "mark_private_msg_as_read", p, VoidData.class);
    }

    /**
     * 撤回消息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【必填】消息ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteMsg(long botQQ, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        return req(botQQ, "delete_msg", p, VoidData.class);
    }

    /**
     * 标记消息已读 (Go-CQHTTP)。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【可选】用户QQ
     * @param groupId 【可选】群号
     * @param messageId 【可选】消息ID
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> markMsgAsRead(long botQQ, Long userId, Long groupId, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", Long.toString(userId));
        if (groupId != null) p.put("group_id", Long.toString(groupId));
        if (messageId != null) p.put("message_id", messageId);
        return req(botQQ, "mark_msg_as_read", p, VoidData.class);
    }

    /**
     * 标记所有消息已读。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<String>> markAllAsRead(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "_mark_all_as_read", p, String.class);
    }

    // ================================================================
    // ApiSystem 实现
    // ================================================================

    /**
     * 处理可疑好友申请。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param flag 【必填】请求 flag
     * @param approve 【必填】是否同意 (强制为 true)
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<String>> setDoubtFriendsAddRequest(long botQQ, String flag, Boolean approve) {
        ObjectNode p = mapper.createObjectNode();
        p.put("flag", flag);
        p.put("approve", approve);
        return req(botQQ, "set_doubt_friends_add_request", p, String.class);
    }

    /**
     * 获取可疑好友申请。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 【必填】获取数量
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> getDoubtFriendsAddRequest(long botQQ, Integer count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("count", count);
        return req(botQQ, "get_doubt_friends_add_request", p, VoidData.class);
    }

    /**
     * 获取登录号信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<String>> getLoginInfo(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "get_login_info", p, String.class);
    }

    /**
     * 获取版本信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VersionInfoData>> getVersionInfo(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "get_version_info", p, VersionInfoData.class);
    }

    /**
     * 是否可以发送语音。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<RecordData>> canSendRecord(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "can_send_record", p, RecordData.class);
    }

    /**
     * 是否可以发送图片。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<RecordData>> canSendImage(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "can_send_image", p, RecordData.class);
    }

    /**
     * 获取运行状态。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<StatusData>> getStatus(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "get_status", p, StatusData.class);
    }

    /**
     * 获取 CSRF Token。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<CsrfTokenData>> getCsrfToken(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "get_csrf_token", p, CsrfTokenData.class);
    }

    /**
     * 获取登录凭证。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param domain 【必填】需要获取 cookies 的域名
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<CredentialsData>> getCredentials(long botQQ, String domain) {
        ObjectNode p = mapper.createObjectNode();
        p.put("domain", domain);
        return req(botQQ, "get_credentials", p, CredentialsData.class);
    }

    /**
     * 获取Packet状态。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> ncGetPacketStatus(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "nc_get_packet_status", p, VoidData.class);
    }

    /**
     * 重启服务。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setRestart(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "set_restart", p, VoidData.class);
    }

    /**
     * 获取群系统消息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 【必填】获取的消息数量
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<GroupIgnoredNotifiesData>> getGroupSystemMsg(long botQQ, String count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("count", count);
        return req(botQQ, "get_group_system_msg", p, GroupIgnoredNotifiesData.class);
    }

    /**
     * 清理缓存。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> cleanCache(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "clean_cache", p, VoidData.class);
    }

    /**
     * 获取扩展 RKey。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<RkeyData>>> getRkey(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return reqList(botQQ, "get_rkey", p, RkeyData.class);
    }

    /**
     * 获取 RKey 服务器。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<RkeyServerData>> getRkeyServer(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "get_rkey_server", p, RkeyServerData.class);
    }

    /**
     * 设置在线状态。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param status 【必填】在线状态
     * @param extStatus 【必填】扩展状态
     * @param batteryStatus 【必填】电量状态
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setOnlineStatus(long botQQ, String status, String extStatus, String batteryStatus) {
        ObjectNode p = mapper.createObjectNode();
        p.put("status", status);
        p.put("ext_status", extStatus);
        p.put("battery_status", batteryStatus);
        return req(botQQ, "set_online_status", p, VoidData.class);
    }

    /**
     * 获取机器人 UIN 范围。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<String>>> getRobotUinRange(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return reqList(botQQ, "get_robot_uin_range", p, String.class);
    }

    /**
     * 获取自定义表情。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 【必填】获取数量
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<String>>> fetchCustomFace(long botQQ, String count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("count", count);
        return reqList(botQQ, "fetch_custom_face", p, String.class);
    }

    /**
     * 获取自定义表情详情。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 【必填】获取数量
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> fetchCustomFaceDetail(long botQQ, String count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("count", count);
        return req(botQQ, "fetch_custom_face_detail", p, VoidData.class);
    }

    /**
     * 添加自定义表情。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【必填】本地表情文件路径
     * @param emojiId 【可选】表情ID，未提供时传空字符串
     * @param packageId 【可选】表情包ID，未提供时传0
     * @param fileName 【可选】文件名，未提供时从file路径取basename
     * @param fileSize 【可选】文件大小，未提供时读取本地文件
     * @param md5 【可选】文件MD5，未提供时读取本地文件计算
     * @param isMarkFace 【可选】是否商城表情
     * @param isOrigin 【可选】是否原图
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> addCustomFace(long botQQ, String file, String emojiId, String packageId, String fileName, String fileSize, String md5, Boolean isMarkFace, Boolean isOrigin) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file", file);
        if (emojiId != null) p.put("emoji_id", emojiId);
        if (packageId != null) p.put("package_id", packageId);
        if (fileName != null) p.put("file_name", fileName);
        if (fileSize != null) p.put("file_size", fileSize);
        if (md5 != null) p.put("md5", md5);
        if (isMarkFace != null && isMarkFace) p.put("is_mark_face", true);
        if (isOrigin != null && isOrigin) p.put("is_origin", true);
        return req(botQQ, "add_custom_face", p, VoidData.class);
    }

    /**
     * 删除自定义表情。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param resId 【可选】fetch_custom_face_detail返回的resId
     * @param id 【可选】native deleteFavEmoji字符串ID，通常为resId
     * @param ids 【可选】native deleteFavEmoji字符串ID列表，通常为resId列表
     * @param md5 【可选】表情MD5，不能直接删除，请先通过fetch_custom_face_detail获取resId
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteCustomFace(long botQQ, String resId, String id, List<String> ids, String md5) {
        ObjectNode p = mapper.createObjectNode();
        if (resId != null) p.put("res_id", resId);
        if (id != null) p.put("id", id);
        if (ids != null) p.set("ids", mapper.valueToTree(ids));
        if (md5 != null) p.put("md5", md5);
        return req(botQQ, "delete_custom_face", p, VoidData.class);
    }

    /**
     * 修改自定义表情描述。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param emojiId 【必填】表情ID
     * @param resId 【必填】资源ID
     * @param md5 【必填】表情MD5
     * @param desc 【必填】新的表情描述
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setCustomFaceDesc(long botQQ, String emojiId, String resId, String md5, String desc) {
        ObjectNode p = mapper.createObjectNode();
        p.put("emoji_id", emojiId);
        p.put("res_id", resId);
        p.put("md5", md5);
        p.put("desc", desc);
        return req(botQQ, "set_custom_face_desc", p, VoidData.class);
    }

    /**
     * 设置输入状态。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】QQ号
     * @param eventType 【必填】事件类型
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> setInputStatus(long botQQ, Long userId, Long eventType) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", Long.toString(userId));
        p.put("event_type", eventType);
        return req(botQQ, "set_input_status", p, VoidData.class);
    }

    /**
     * 获取用户在线状态。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】QQ号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<UserStatusData>> ncGetUserStatus(long botQQ, Long userId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", Long.toString(userId));
        return req(botQQ, "nc_get_user_status", p, UserStatusData.class);
    }

    /**
     * 获取 RKey。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<List<String>>> ncGetRkey(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return reqList(botQQ, "nc_get_rkey", p, String.class);
    }

    /**
     * 获取小程序 Ark。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<MiniAppArkData>> getMiniAppArk(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "get_mini_app_ark", p, MiniAppArkData.class);
    }

    /**
     * 发送原始数据包。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param cmd 【必填】命令字
     * @param data 【必填】十六进制数据
     * @param rsp 【必填】是否等待响应
     * @return 异步响应，成功时 data 包含业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<String>> sendPacket(long botQQ, String cmd, String data, String rsp) {
        ObjectNode p = mapper.createObjectNode();
        p.put("cmd", cmd);
        p.put("data", data);
        p.put("rsp", rsp);
        return req(botQQ, "send_packet", p, String.class);
    }

    /**
     * 退出登录。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> botExit(long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        return req(botQQ, "bot_exit", p, VoidData.class);
    }

    /**
     * 获取收藏列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param category 【必填】分类ID
     * @param count 【必填】获取数量
     * @return 异步响应，无业务数据
     *
     * 可能的错误 retcode: 1400, 1401, 1404；详见返回的 ApiResponse.message
     */
    @Override
    public CompletableFuture<ApiResponse<VoidData>> getCollectionList(long botQQ, String category, String count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("category", category);
        p.put("count", count);
        return req(botQQ, "get_collection_list", p, VoidData.class);
    }
}