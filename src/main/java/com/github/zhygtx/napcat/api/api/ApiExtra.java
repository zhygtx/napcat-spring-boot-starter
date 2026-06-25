package com.github.zhygtx.napcat.api.api;

import com.fasterxml.jackson.databind.JsonNode;
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
 * 扩展 API 接口。
 * <p>
 * 涵盖合并转发、历史消息、戳一戳、表情点赞、群相册、流式传输、OCR/翻译、收藏、频道、AI 等进阶功能。
 * 所有方法通过指定 {@code botQQ} 区分目标 Bot 连接。
 */
@SuppressWarnings("unused")
public interface ApiExtra {

    /**
     * 清理流式传输临时文件。
     * <p>
     * 对应 NapCat API: {@code clean_stream_temp_file}
     * <p>
     * 分类：流式传输扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> cleanStreamTempFile(long botQQ);

    /**
     * 下载文件流。
     * <p>
     * 以流式方式从网络或本地下载文件
     * <p>
     * 对应 NapCat API: {@code download_file_stream}
     * <p>
     * 分类：流式接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【可选】文件路径或 URL
     * @param fileId 【可选】文件 ID
     * @param chunkSize 【可选】分块大小 (字节)（默认 65536）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<FileStreamData>> downloadFileStream(long botQQ, String file, String fileId, Integer chunkSize);

    /**
     * 下载语音文件流。
     * <p>
     * 对应 NapCat API: {@code download_file_record_stream}
     * <p>
     * 分类：流式传输扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【可选】文件路径或 URL
     * @param fileId 【可选】文件 ID
     * @param chunkSize 【可选】分块大小 (字节)（默认 65536）
     * @param outFormat 【可选】输出格式
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<FileRecordStreamData>> downloadFileRecordStream(long botQQ, String file, String fileId, Integer chunkSize, String outFormat);

    /**
     * 下载图片文件流。
     * <p>
     * 对应 NapCat API: {@code download_file_image_stream}
     * <p>
     * 分类：流式传输扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【可选】文件路径或 URL
     * @param fileId 【可选】文件 ID
     * @param chunkSize 【可选】分块大小 (字节)（默认 65536）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<FileRecordStreamData>> downloadFileImageStream(long botQQ, String file, String fileId, Integer chunkSize);

    /**
     * 测试下载流。
     * <p>
     * 对应 NapCat API: {@code test_download_stream}
     * <p>
     * 分类：流式传输扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param error 【可选】是否触发测试错误（默认 False）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<DownloadStreamData>> testDownloadStream(long botQQ, Boolean error);

    /**
     * 上传文件流。
     * <p>
     * 以流式方式上传文件数据到机器人
     * <p>
     * 对应 NapCat API: {@code upload_file_stream}
     * <p>
     * 分类：流式接口
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
     * @param fileRetention 【必填】文件保留时间 (毫秒)（默认 300000）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<FileStreamData>> uploadFileStream(long botQQ, String streamId, String chunkData, Long chunkIndex, Long totalChunks, Integer fileSize, String expectedSha256, Boolean isComplete, String filename, Boolean reset, Boolean verifyOnly, Long fileRetention);

    /**
     * 批量踢出群成员。
     * <p>
     * 从指定群聊中批量踢出多个成员
     * <p>
     * 对应 NapCat API: {@code set_group_kick_members}
     * <p>
     * 分类：扩展接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param userId 【必填】QQ号列表
     * @param rejectAddRequest 【可选】是否拒绝加群请求
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupKickMembers(long botQQ, Long groupId, List<Long> userId, Boolean rejectAddRequest);

    /**
     * 设置QQ资料。
     * <p>
     * 修改当前账号的昵称、个性签名等资料
     * <p>
     * 对应 NapCat API: {@code set_qq_profile}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param nickname 【必填】昵称
     * @param personalNote 【可选】个性签名
     * @param sex 【可选】性别 (0: 未知, 1: 男, 2: 女)
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setQqProfile(long botQQ, String nickname, String personalNote, Long sex);

    /**
     * 创建收藏。
     * <p>
     * 对应 NapCat API: {@code create_collection}
     * <p>
     * 分类：扩展接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param rawData 【必填】原始数据
     * @param brief 【必填】简要描述
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<CollectionData>> createCollection(long botQQ, String rawData, String brief);

    /**
     * 设置个性签名。
     * <p>
     * 修改当前登录帐号的个性签名
     * <p>
     * 对应 NapCat API: {@code set_self_longnick}
     * <p>
     * 分类：扩展接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param longNick 【必填】签名内容
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setSelfLongnick(long botQQ, String longNick);

    /**
     * 设置QQ头像。
     * <p>
     * 修改当前账号的QQ头像
     * <p>
     * 对应 NapCat API: {@code set_qq_avatar}
     * <p>
     * 分类：扩展接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【必填】图片路径、URL或Base64
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setQqAvatar(long botQQ, String file);

    /**
     * 英文单词翻译。
     * <p>
     * 将英文单词列表翻译为中文
     * <p>
     * 对应 NapCat API: {@code translate_en2zh}
     * <p>
     * 分类：扩展接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param words 【必填】待翻译单词列表
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<En2zhData>> translateEn2zh(long botQQ, List<String> words);

    /**
     * 获取群根目录文件列表。
     * <p>
     * 获取群文件根目录下的所有文件和文件夹
     * <p>
     * 对应 NapCat API: {@code get_group_root_files}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param fileCount 【必填】文件数量（默认 50）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupRootFilesData>> getGroupRootFiles(long botQQ, Long groupId, Integer fileCount);

    /**
     * 获取ClientKey。
     * <p>
     * 获取当前登录帐号的ClientKey
     * <p>
     * 对应 NapCat API: {@code get_clientkey}
     * <p>
     * 分类：扩展接口
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
    CompletableFuture<ApiResponse<ClientkeyData>> getClientkey(long botQQ);

    /**
     * 删除好友。
     * <p>
     * 从好友列表中删除指定用户
     * <p>
     * 对应 NapCat API: {@code delete_friend}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param friendId 【可选】好友 QQ 号
     * @param userId 【可选】用户 QQ 号
     * @param tempBlock 【可选】是否加入黑名单
     * @param tempBothDel 【可选】是否双向删除
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<String>> deleteFriend(long botQQ, String friendId, Long userId, Boolean tempBlock, Boolean tempBothDel);

    /**
     * 检查URL安全性。
     * <p>
     * 检查指定URL的安全等级
     * <p>
     * 对应 NapCat API: {@code check_url_safely}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param url 【必填】要检查的 URL
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<UrlSafelyData>> checkUrlSafely(long botQQ, String url);

    /**
     * 获取在线客户端。
     * <p>
     * 获取当前登录账号的在线客户端列表
     * <p>
     * 对应 NapCat API: {@code get_online_clients}
     * <p>
     * 分类：Go-CQHTTP
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
    CompletableFuture<ApiResponse<List<String>>> getOnlineClients(long botQQ);

    /**
     * 图片 OCR 识别。
     * <p>
     * 识别图片中的文字内容(仅Windows端支持)
     * <p>
     * 对应 NapCat API: {@code ocr_image}
     * <p>
     * 分类：扩展接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param image 【必填】图片路径、URL或Base64
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<ImageData>> ocrImage(long botQQ, String image);

    /**
     * 图片 OCR 识别 (内部)。
     * <p>
     * 识别图片中的文字内容(仅Windows端支持)
     * <p>
     * 对应 NapCat API: {@code ocr_image}
     * <p>
     * 分类：扩展接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param image 【必填】图片路径、URL或Base64
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<ImageData>> ocrImageInternal(long botQQ, String image);

    /**
     * 获取群荣誉信息。
     * <p>
     * 获取指定群聊的荣誉信息，如龙王等
     * <p>
     * 对应 NapCat API: {@code get_group_honor_info}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param type 【可选】荣誉类型
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupHonorInfoData>> getGroupHonorInfo(long botQQ, Long groupId, String type);

    /**
     * 发送群公告。
     * <p>
     * 在指定群聊中发布新的公告
     * <p>
     * 对应 NapCat API: {@code _send_group_notice}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param content 【必填】公告内容
     * @param image 【可选】公告图片路径或 URL
     * @param pinned 【必填】是否置顶 (0/1)（默认 0）
     * @param type 【必填】类型 (默认为 1)（默认 1）
     * @param confirmRequired 【必填】是否需要确认 (0/1)（默认 1）
     * @param isShowEditCard 【必填】是否显示修改群名片引导 (0/1)（默认 0）
     * @param tipWindowType 【必填】弹窗类型 (默认为 0)（默认 0）
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> sendGroupNotice(long botQQ, Long groupId, String content, String image, Long pinned, Long type, Long confirmRequired, Long isShowEditCard, Long tipWindowType);

    /**
     * 获取群艾特全体剩余次数。
     * <p>
     * 获取指定群聊中艾特全体成员的剩余次数
     * <p>
     * 对应 NapCat API: {@code get_group_at_all_remain}
     * <p>
     * 分类：Go-CQHTTP
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
    CompletableFuture<ApiResponse<GroupAtAllRemainData>> getGroupAtAllRemain(long botQQ, Long groupId);

    /**
     * 发送合并转发消息。
     * <p>
     * 发送合并转发消息
     * <p>
     * 对应 NapCat API: {@code send_forward_msg}
     * <p>
     * 分类：Go-CQHTTP
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
    CompletableFuture<ApiResponse<GroupMsgData>> sendForwardMsg(long botQQ, String messageType, Long userId, Long groupId, String message, Boolean autoEscape, String source, List<JsonNode> news, String summary, String prompt, Long timeout);

    /**
     * 发送群合并转发消息。
     * <p>
     * 对应 NapCat API: {@code send_group_forward_msg}
     * <p>
     * 分类：Go-CQHTTP
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
    CompletableFuture<ApiResponse<GroupMsgData>> sendGroupForwardMsg(long botQQ, String messageType, Long userId, Long groupId, String message, Boolean autoEscape, String source, List<JsonNode> news, String summary, String prompt, Long timeout);

    /**
     * 发送私聊合并转发消息。
     * <p>
     * 对应 NapCat API: {@code send_private_forward_msg}
     * <p>
     * 分类：Go-CQHTTP
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
    CompletableFuture<ApiResponse<GroupMsgData>> sendPrivateForwardMsg(long botQQ, String messageType, Long userId, Long groupId, String message, Boolean autoEscape, String source, List<JsonNode> news, String summary, String prompt, Long timeout);

    /**
     * 获取陌生人信息。
     * <p>
     * 获取指定非好友用户的信息
     * <p>
     * 对应 NapCat API: {@code get_stranger_info}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户QQ
     * @param noCache 【必填】是否不使用缓存（默认 False）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<StrangerInfoData>> getStrangerInfo(long botQQ, Long userId, Boolean noCache);

    /**
     * 下载文件。
     * <p>
     * 下载网络文件到本地临时目录
     * <p>
     * 对应 NapCat API: {@code download_file}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param url 【可选】下载链接
     * @param base64 【可选】base64数据
     * @param name 【可选】文件名
     * @param headers 【可选】请求头
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<FileRecordStreamData>> downloadFile(long botQQ, String url, String base64, String name, String headers);

    /**
     * 获取频道列表。
     * <p>
     * 获取当前帐号已加入的频道列表
     * <p>
     * 对应 NapCat API: {@code get_guild_list}
     * <p>
     * 分类：频道接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> getGuildList(long botQQ);

    /**
     * 上传群文件。
     * <p>
     * 上传资源路径或URL指定的文件到指定群聊的文件系统中
     * <p>
     * 对应 NapCat API: {@code upload_group_file}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param file 【必填】资源路径或URL
     * @param name 【必填】文件名
     * @param folder 【可选】父目录 ID
     * @param folderId 【可选】父目录 ID (兼容性字段)
     * @param uploadFile 【必填】是否执行上传（默认 True）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupFileData>> uploadGroupFile(long botQQ, Long groupId, String file, String name, String folder, String folderId, Boolean uploadFile);

    /**
     * 获取群历史消息。
     * <p>
     * 获取指定群聊的历史聊天记录
     * <p>
     * 对应 NapCat API: {@code get_group_msg_history}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param messageSeq 【可选】起始消息序号
     * @param count 【必填】获取消息数量（默认 20）
     * @param reverseOrder 【必填】是否反向排序（默认 False）
     * @param disableGetUrl 【必填】是否禁用获取URL（默认 False）
     * @param parseMultMsg 【必填】是否解析合并消息（默认 True）
     * @param quickReply 【必填】是否快速回复（默认 False）
     * @param reverseOrder2 【必填】是否反向排序(旧版本兼容)（默认 False）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupMsgHistoryData>> getGroupMsgHistory(long botQQ, Long groupId, String messageSeq, Integer count, Boolean reverseOrder, Boolean disableGetUrl, Boolean parseMultMsg, Boolean quickReply, Boolean reverseOrder2);

    /**
     * 获取合并转发消息。
     * <p>
     * 获取合并转发消息的具体内容
     * <p>
     * 对应 NapCat API: {@code get_forward_msg}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param messageId 【可选】消息ID
     * @param id 【可选】消息ID
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupMsgHistoryData>> getForwardMsg(long botQQ, String messageId, String id);

    /**
     * 获取好友历史消息。
     * <p>
     * 获取指定好友的历史聊天记录
     * <p>
     * 对应 NapCat API: {@code get_friend_msg_history}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户QQ
     * @param messageSeq 【可选】起始消息序号
     * @param count 【必填】获取消息数量（默认 20）
     * @param reverseOrder 【必填】是否反向排序（默认 False）
     * @param disableGetUrl 【必填】是否禁用获取URL（默认 False）
     * @param parseMultMsg 【必填】是否解析合并消息（默认 True）
     * @param quickReply 【必填】是否快速回复（默认 False）
     * @param reverseOrder2 【必填】是否反向排序(旧版本兼容)（默认 False）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupMsgHistoryData>> getFriendMsgHistory(long botQQ, Long userId, String messageSeq, Integer count, Boolean reverseOrder, Boolean disableGetUrl, Boolean parseMultMsg, Boolean quickReply, Boolean reverseOrder2);

    /**
     * 处理快速操作。
     * <p>
     * 处理来自事件上报的快速操作请求
     * <p>
     * 对应 NapCat API: {@code handle_quick_operation}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param context 【必填】事件上下文
     * @param operation 【必填】快速操作内容
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> handleQuickOperationInternal(long botQQ, JsonNode context, JsonNode operation);

    /**
     * 设置群头像。
     * <p>
     * 修改指定群聊的头像
     * <p>
     * 对应 NapCat API: {@code set_group_portrait}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【必填】头像文件路径或 URL
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
    CompletableFuture<ApiResponse<CollectionData>> setGroupPortrait(long botQQ, String file, Long groupId);

    /**
     * 上传私聊文件。
     * <p>
     * 上传本地文件到指定私聊会话中
     * <p>
     * 对应 NapCat API: {@code upload_private_file}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @param file 【必填】资源路径或URL
     * @param name 【必填】文件名
     * @param uploadFile 【必填】是否执行上传（默认 True）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupFileData>> uploadPrivateFile(long botQQ, Long userId, String file, String name, Boolean uploadFile);

    /**
     * 获取频道个人信息。
     * <p>
     * 获取当前帐号在频道中的个人资料
     * <p>
     * 对应 NapCat API: {@code get_guild_service_profile}
     * <p>
     * 分类：频道接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> getGuildServiceProfile(long botQQ);

    /**
     * 获取机型显示。
     * <p>
     * 获取当前账号可用的设备机型显示名称列表
     * <p>
     * 对应 NapCat API: {@code _get_model_show}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param model 【可选】模型名称
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<List<ModelShowData>>> getModelShow(long botQQ, String model);

    /**
     * 设置机型。
     * <p>
     * 设置当前账号的设备机型名称
     * <p>
     * 对应 NapCat API: {@code _set_model_show}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setModelShow(long botQQ);

    /**
     * 删除群文件。
     * <p>
     * 在群文件系统中删除指定的文件
     * <p>
     * 对应 NapCat API: {@code delete_group_file}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param fileId 【必填】文件ID
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> deleteGroupFile(long botQQ, Long groupId, String fileId);

    /**
     * 创建群文件目录。
     * <p>
     * 在群文件系统中创建新的文件夹
     * <p>
     * 对应 NapCat API: {@code create_group_file_folder}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param folderName 【可选】文件夹名称
     * @param name 【可选】文件夹名称
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupFileFolderData>> createGroupFileFolder(long botQQ, Long groupId, String folderName, String name);

    /**
     * 删除群文件目录。
     * <p>
     * 在群文件系统中删除指定的文件夹
     * <p>
     * 对应 NapCat API: {@code delete_group_folder}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param folderId 【可选】文件夹ID
     * @param folder 【可选】文件夹ID
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> deleteGroupFolder(long botQQ, Long groupId, String folderId, String folder);

    /**
     * 获取群文件系统信息。
     * <p>
     * 获取群聊文件系统的空间及状态信息
     * <p>
     * 对应 NapCat API: {@code get_group_file_system_info}
     * <p>
     * 分类：Go-CQHTTP
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
    CompletableFuture<ApiResponse<GroupFileSystemInfoData>> getGroupFileSystemInfo(long botQQ, Long groupId);

    /**
     * 获取群文件夹文件列表。
     * <p>
     * 获取指定群文件夹下的文件及子文件夹列表
     * <p>
     * 对应 NapCat API: {@code get_group_files_by_folder}
     * <p>
     * 分类：Go-CQHTTP
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param folderId 【可选】文件夹ID
     * @param folder 【可选】文件夹ID
     * @param fileCount 【必填】文件数量（默认 50）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupRootFilesData>> getGroupFilesByFolder(long botQQ, Long groupId, String folderId, String folder, Integer fileCount);

    /**
     * 设置专属头衔。
     * <p>
     * 设置群聊中指定成员的专属头衔
     * <p>
     * 对应 NapCat API: {@code set_group_special_title}
     * <p>
     * 分类：扩展接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param userId 【必填】QQ号
     * @param specialTitle 【必填】专属头衔（默认 ）
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupSpecialTitle(long botQQ, Long groupId, Long userId, String specialTitle);

    /**
     * 获取 AI 语音。
     * <p>
     * 通过 AI 语音引擎获取指定文本的语音 URL
     * <p>
     * 对应 NapCat API: {@code get_ai_record}
     * <p>
     * 分类：AI 扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param character 【必填】角色ID
     * @param groupId 【必填】群号
     * @param text 【必填】语音文本内容
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<String>> getAiRecord(long botQQ, String character, Long groupId, String text);

    /**
     * 发送群 AI 语音。
     * <p>
     * 发送 AI 生成的语音到指定群聊
     * <p>
     * 对应 NapCat API: {@code send_group_ai_record}
     * <p>
     * 分类：AI 扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param character 【必填】角色ID
     * @param groupId 【必填】群号
     * @param text 【必填】语音文本内容
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupAiRecordData>> sendGroupAiRecord(long botQQ, String character, Long groupId, String text);

    /**
     * 获取AI角色列表。
     * <p>
     * 获取群聊中的AI角色列表
     * <p>
     * 对应 NapCat API: {@code get_ai_characters}
     * <p>
     * 分类：扩展接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param chatType 【必填】聊天类型（默认 1）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<List<AiCharactersData>>> getAiCharacters(long botQQ, Long groupId, Long chatType);
}