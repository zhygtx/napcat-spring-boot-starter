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
 * 文件相关 API 接口。
 * <p>
 * 提供图片/语音获取、文件下载、群文件/私聊文件上传、群文件管理等操作。
 * 所有方法通过指定 {@code botQQ} 区分目标 Bot 连接。
 */
@SuppressWarnings("unused")
public interface ApiFile {

    /**
     * 获取文件。
     * <p>
     * 获取指定文件的详细信息及下载路径
     * <p>
     * 对应 NapCat API: {@code get_file}
     * <p>
     * 分类：文件接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【可选】文件路径、URL或Base64
     * @param fileId 【可选】文件ID
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<FileData>> getFile(long botQQ, String file, String fileId);

    /**
     * 移动群文件。
     * <p>
     * 对应 NapCat API: {@code move_group_file}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param fileId 【必填】文件ID
     * @param currentParentDirectory 【必填】当前父目录
     * @param targetParentDirectory 【必填】目标父目录
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupFileData>> moveGroupFile(long botQQ, Long groupId, String fileId, String currentParentDirectory, String targetParentDirectory);

    /**
     * 重命名群文件。
     * <p>
     * 对应 NapCat API: {@code rename_group_file}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param fileId 【必填】文件ID
     * @param currentParentDirectory 【必填】当前父目录
     * @param newName 【必填】新文件名
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupFileData>> renameGroupFile(long botQQ, Long groupId, String fileId, String currentParentDirectory, String newName);

    /**
     * 传输群文件。
     * <p>
     * 对应 NapCat API: {@code trans_group_file}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param fileId 【必填】文件ID
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupFileData>> transGroupFile(long botQQ, Long groupId, String fileId);

    /**
     * 获取图片。
     * <p>
     * 获取指定图片的信息及路径
     * <p>
     * 对应 NapCat API: {@code get_image}
     * <p>
     * 分类：文件接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【可选】文件路径、URL或Base64
     * @param fileId 【可选】文件ID
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<FileData>> getImage(long botQQ, String file, String fileId);

    /**
     * 获取语音。
     * <p>
     * 获取指定语音文件的信息，并支持格式转换
     * <p>
     * 对应 NapCat API: {@code get_record}
     * <p>
     * 分类：文件接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file 【可选】文件路径、URL或Base64
     * @param fileId 【可选】文件ID
     * @param outFormat 【必填】输出格式
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<FileData>> getRecord(long botQQ, String file, String fileId, String outFormat);

    /**
     * 获取群文件URL。
     * <p>
     * 获取指定群文件的下载链接
     * <p>
     * 对应 NapCat API: {@code get_group_file_url}
     * <p>
     * 分类：文件接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param fileId 【必填】文件ID
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupFileUrlData>> getGroupFileUrl(long botQQ, Long groupId, String fileId);

    /**
     * 获取私聊文件URL。
     * <p>
     * 获取指定私聊文件的下载链接
     * <p>
     * 对应 NapCat API: {@code get_private_file_url}
     * <p>
     * 分类：文件接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param fileId 【必填】文件ID
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupFileUrlData>> getPrivateFileUrl(long botQQ, String fileId);

    /**
     * 创建闪传任务。
     * <p>
     * 对应 NapCat API: {@code create_flash_task}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param files 【必填】文件列表或单个文件路径
     * @param name 【可选】任务名称
     * @param thumbPath 【可选】缩略图路径
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<FlashTaskData>> createFlashTask(long botQQ, List<String> files, String name, String thumbPath);

    /**
     * 获取闪传文件列表。
     * <p>
     * 对应 NapCat API: {@code get_flash_file_list}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param filesetId 【必填】文件集 ID
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<List<FlashFileData>>> getFlashFileList(long botQQ, String filesetId);

    /**
     * 获取闪传文件链接。
     * <p>
     * 对应 NapCat API: {@code get_flash_file_url}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param filesetId 【必填】文件集 ID
     * @param fileName 【可选】文件名
     * @param fileIndex 【可选】文件索引
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupFileUrlData>> getFlashFileUrl(long botQQ, String filesetId, String fileName, Long fileIndex);

    /**
     * 发送闪传消息。
     * <p>
     * 对应 NapCat API: {@code send_flash_msg}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param filesetId 【必填】文件集 ID
     * @param userId 【可选】用户 QQ
     * @param groupId 【可选】群号
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupAiRecordData>> sendFlashMsg(long botQQ, String filesetId, Long userId, Long groupId);

    /**
     * 获取文件分享链接。
     * <p>
     * 对应 NapCat API: {@code get_share_link}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param filesetId 【必填】文件集 ID
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> getShareLink(long botQQ, String filesetId);

    /**
     * 获取文件集信息。
     * <p>
     * 对应 NapCat API: {@code get_fileset_info}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param filesetId 【必填】文件集 ID
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<FilesetInfoData>> getFilesetInfo(long botQQ, String filesetId);

    /**
     * 获取在线文件消息。
     * <p>
     * 对应 NapCat API: {@code get_online_file_msg}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> getOnlineFileMsg(long botQQ, Long userId);

    /**
     * 发送在线文件。
     * <p>
     * 对应 NapCat API: {@code send_online_file}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @param filePath 【必填】本地文件路径
     * @param fileName 【可选】文件名 (可选)
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> sendOnlineFile(long botQQ, Long userId, String filePath, String fileName);

    /**
     * 发送在线文件夹。
     * <p>
     * 对应 NapCat API: {@code send_online_folder}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @param folderPath 【必填】本地文件夹路径
     * @param folderName 【可选】文件夹名称 (可选)
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> sendOnlineFolder(long botQQ, Long userId, String folderPath, String folderName);

    /**
     * 接收在线文件。
     * <p>
     * 对应 NapCat API: {@code receive_online_file}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @param msgId 【必填】消息 ID
     * @param elementId 【必填】元素 ID
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> receiveOnlineFile(long botQQ, Long userId, String msgId, String elementId);

    /**
     * 拒绝在线文件。
     * <p>
     * 对应 NapCat API: {@code refuse_online_file}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @param msgId 【必填】消息 ID
     * @param elementId 【必填】元素 ID
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> refuseOnlineFile(long botQQ, Long userId, String msgId, String elementId);

    /**
     * 取消在线文件。
     * <p>
     * 对应 NapCat API: {@code cancel_online_file}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】用户 QQ
     * @param msgId 【必填】消息 ID
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> cancelOnlineFile(long botQQ, Long userId, String msgId);

    /**
     * 下载文件集。
     * <p>
     * 对应 NapCat API: {@code download_fileset}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param filesetId 【必填】文件集 ID
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> downloadFileset(long botQQ, String filesetId);

    /**
     * 获取文件集 ID。
     * <p>
     * 对应 NapCat API: {@code get_fileset_id}
     * <p>
     * 分类：文件扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param shareCode 【必填】分享码或分享链接
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<FilesetIdData>> getFilesetId(long botQQ, String shareCode);
}