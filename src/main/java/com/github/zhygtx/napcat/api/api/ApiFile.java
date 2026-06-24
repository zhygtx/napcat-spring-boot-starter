package com.github.zhygtx.napcat.api.api;

import com.github.zhygtx.napcat.api.response.*;
import com.github.zhygtx.napcat.api.response.file.*;
import com.github.zhygtx.napcat.api.response.group.*;

import java.util.concurrent.CompletableFuture;

/**
 * 文件相关 API 接口。
 * <p>
 * 提供图片/语音获取、文件下载、群文件/私聊文件上传、
 * 群文件管理（文件夹 CRUD、移动、重命名、传输）等操作。
 * 所有方法通过指定 {@code botQQ} 区分目标 Bot 连接。
 */
@SuppressWarnings("unused")
public interface ApiFile {

    /**
     * 获取图片信息。
     * <p>
     * 根据文件/缓存名称获取图片的本地路径或 URL。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file  图片文件名（来自消息中的 file 字段）
     * @return 异步响应，data 包含图片本地路径等信息
     */
    CompletableFuture<ApiResponse<FileData>> getImage(long botQQ, String file);

    /**
     * 获取语音信息。
     * <p>
     * 根据文件/缓存名称获取语音文件的本地路径或 URL。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file  语音文件名（来自消息中的 file 字段）
     * @return 异步响应，data 包含语音本地路径等信息
     */
    CompletableFuture<ApiResponse<FileData>> getRecord(long botQQ, String file);

    /**
     * 获取文件信息。
     * <p>
     * 根据文件/缓存名称获取文件的本地路径或 URL。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file  文件名
     * @return 异步响应，data 包含文件本地路径等信息
     */
    CompletableFuture<ApiResponse<FileData>> getFile(long botQQ, String file);

    /**
     * 下载文件到本地。
     * <p>
     * 从指定 URL 下载文件到 NapCat 客户端的本地临时目录。
     *
     * @param botQQ       目标 Bot 的 QQ 号
     * @param threadCount 下载线程数
     * @param headers     自定义请求头（JSON 格式字符串）
     * @param base64      文件 base64 编码内容
     * @param name        文件名
     * @param url         文件下载 URL
     * @return 异步响应，data 包含下载后的本地文件路径
     */
    CompletableFuture<ApiResponse<FileDownloadData>> downloadFile(long botQQ, int threadCount, String headers, String base64, String name, String url);

    /**
     * 上传群文件。
     * <p>
     * 将本地文件上传到指定群聊的文件空间中。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param file   本地文件路径
     * @param name   上传后的文件名（可选，默认使用原文件名）
     * @param folder 目标文件夹 ID（可选，上传到根目录时留空）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> uploadGroupFile(long botQQ, long groupQQ, String file, String name, String folder);

    /**
     * 上传私聊文件。
     * <p>
     * 将本地文件发送给指定好友（私聊文件传输）。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param userId 目标用户 QQ
     * @param file   本地文件路径
     * @param name   文件名（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> uploadPrivateFile(long botQQ, String userId, String file, String name);

    /**
     * 获取群文件下载 URL。
     * <p>
     * 获取群文件中指定文件的下载链接。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param fileId 文件 ID
     * @param busid  busid（文件标识）
     * @return 异步响应，data 包含文件下载 URL
     */
    CompletableFuture<ApiResponse<FileUrlData>> getGroupFileUrl(long botQQ, long groupQQ, String fileId, long busid);

    /**
     * 获取私聊文件下载 URL。
     * <p>
     * 获取私聊文件中指定文件的下载链接。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param userId 用户 QQ
     * @param fileId 文件 ID
     * @param busid  busid
     * @return 异步响应，data 包含文件下载 URL
     */
    CompletableFuture<ApiResponse<FileUrlData>> getPrivateFileUrl(long botQQ, String userId, String fileId, long busid);

    /**
     * 删除群文件。
     * <p>
     * 删除群聊中的指定文件。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param fileId  文件 ID
     * @param busid   busid
     * @param folderId 文件夹 ID（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> deleteGroupFile(long botQQ, long groupQQ, String fileId, long busid, String folderId);

    /**
     * 创建群文件夹。
     * <p>
     * 在群文件空间中创建新的文件夹。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param groupQQ  目标群号
     * @param name     文件夹名称
     * @param folderId 父文件夹 ID（根目录时留空或 {@code null}）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> createGroupFileFolder(long botQQ, long groupQQ, String name, String folderId);

    /**
     * 删除群文件夹。
     * <p>
     * 删除群文件空间中的指定文件夹。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param groupQQ  目标群号
     * @param folderId 文件夹 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> deleteGroupFolder(long botQQ, long groupQQ, String folderId);

    /**
     * 获取群文件系统信息。
     * <p>
     * 获取群文件的总容量、已用空间、文件数等信息。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，data 包含群文件系统信息
     */
    CompletableFuture<ApiResponse<GroupFileSystemInfoData>> getGroupFileSystemInfo(long botQQ, long groupQQ);

    /**
     * 获取群根目录文件列表。
     * <p>
     * 获取群文件根目录下的文件与文件夹列表。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，data 包含文件列表和文件夹列表
     */
    CompletableFuture<ApiResponse<GroupRootFilesData>> getGroupRootFiles(long botQQ, long groupQQ);

    /**
     * 获取群文件夹内文件列表。
     * <p>
     * 获取指定群文件夹内的文件与子文件夹列表。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param groupQQ  目标群号
     * @param folderId 文件夹 ID
     * @return 异步响应，data 包含文件列表和文件夹列表
     */
    CompletableFuture<ApiResponse<GroupRootFilesData>> getGroupFilesByFolder(long botQQ, long groupQQ, String folderId);

    /**
     * 移动群文件。
     * <p>
     * 将群文件移动到指定文件夹中。
     *
     * @param botQQ          目标 Bot 的 QQ 号
     * @param groupQQ        目标群号
     * @param fileId         文件 ID
     * @param busid          busid
     * @param targetFolderId 目标文件夹 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> moveGroupFile(long botQQ, long groupQQ, String fileId, long busid, String targetFolderId);

    /**
     * 重命名群文件。
     * <p>
     * 修改群文件的文件名。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param groupQQ  目标群号
     * @param fileId   文件 ID
     * @param busid    busid
     * @param newName  新文件名
     * @param folderId 文件夹 ID（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> renameGroupFile(long botQQ, long groupQQ, String fileId, long busid, String newName, String folderId);

    /**
     * 传输群文件到另一个群。
     * <p>
     * 将群文件从当前群复制传输到另一个群聊。
     *
     * @param botQQ        目标 Bot 的 QQ 号
     * @param groupQQ      源群号
     * @param fileId       文件 ID
     * @param busid        busid
     * @param targetGroupQQ 目标群号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> transGroupFile(long botQQ, long groupQQ, String fileId, long busid, long targetGroupQQ);

    // ---- 文件集 ----

    /**
     * 获取文件集信息。
     * <p>
     * 获取指定文件集的详细信息。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param filesetId 文件集 ID
     * @return 异步响应，包含文件集信息
     */
    CompletableFuture<ApiResponse<FilesetInfoData>> getFilesetInfo(long botQQ, String filesetId);

    /**
     * 获取文件集 ID。
     * <p>
     * 通过分享码获取文件集 ID。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param shareCode 分享码
     * @return 异步响应，包含文件集 ID 信息
     */
    CompletableFuture<ApiResponse<FilesetIdData>> getFilesetId(long botQQ, String shareCode);

    /**
     * 下载文件集。
     * <p>
     * 将文件集下载到本地指定路径。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param filesetId 文件集 ID
     * @param destPath  下载目标路径
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> downloadFileset(long botQQ, String filesetId, String destPath);
}
