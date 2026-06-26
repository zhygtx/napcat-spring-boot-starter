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
 * 群组相关 API 接口。
 * <p>
 * 提供群信息查询、成员管理、禁言/踢人、管理员设置、群公告等群组全量操作。
 * 所有方法通过指定 {@code botQQ} 区分目标 Bot 连接。
 */
@SuppressWarnings("unused")
public interface ApiGroup {

    /**
     * 删除群相册媒体。
     * <p>
     * 对应 NapCat API: {@code del_group_album_media}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param albumId 【必填】相册ID
     * @param lloc 【必填】媒体ID (lloc)
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<DelGroupAlbumMediaData>> delGroupAlbumMedia(long botQQ, Long groupId, String albumId, String lloc);

    /**
     * 点赞群相册媒体。
     * <p>
     * 对应 NapCat API: {@code set_group_album_media_like}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param albumId 【必填】相册ID
     * @param batchId 【必填】batch_id
     * @param lloc 【可选】lloc，若对整个上传操作则不填
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<DelGroupAlbumMediaData>> setGroupAlbumMediaLike(long botQQ, Long groupId, String albumId, String batchId, String lloc);

    /**
     * 取消点赞群相册媒体。
     * <p>
     * 对应 NapCat API: {@code cancel_group_album_media_like}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param albumId 【必填】相册ID
     * @param batchId 【必填】batch_id
     * @param lloc 【可选】lloc，若对整个上传操作则不填
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<DelGroupAlbumMediaData>> cancelGroupAlbumMediaLike(long botQQ, Long groupId, String albumId, String batchId, String lloc);

    /**
     * 发表群相册评论。
     * <p>
     * 对应 NapCat API: {@code do_group_album_comment}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param albumId 【必填】相册 ID
     * @param lloc 【必填】图片 ID
     * @param content 【必填】评论内容
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<DelGroupAlbumMediaData>> doGroupAlbumComment(long botQQ, Long groupId, String albumId, String lloc, String content);

    /**
     * 获取群相册媒体列表。
     * <p>
     * 对应 NapCat API: {@code get_group_album_media_list}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param albumId 【必填】相册ID
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupAlbumMediaData>> getGroupAlbumMediaList(long botQQ, Long groupId, String albumId);

    /**
     * 获取群相册列表。
     * <p>
     * 对应 NapCat API: {@code get_qun_album_list}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param attachInfo 【可选】附加信息（用于分页，从上一次返回结果中获取）（默认 ）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<QunAlbumData>> getQunAlbumList(long botQQ, Long groupId, String attachInfo);

    /**
     * 上传图片到群相册。
     * <p>
     * 对应 NapCat API: {@code upload_image_to_qun_album}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param albumId 【必填】相册ID
     * @param albumName 【必填】相册名称
     * @param file 【必填】图片路径、URL或Base64
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<ImageToQunAlbumData>> uploadImageToQunAlbum(long botQQ, Long groupId, String albumId, String albumName, String file);

    /**
     * 获取群详细信息。
     * <p>
     * 获取群聊的详细信息，包括成员数、最大成员数等
     * <p>
     * 对应 NapCat API: {@code get_group_detail_info}
     * <p>
     * 分类：群组接口
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
    CompletableFuture<ApiResponse<GroupDetailInfoData>> getGroupDetailInfo(long botQQ, Long groupId);

    /**
     * 设置群加群选项。
     * <p>
     * 对应 NapCat API: {@code set_group_add_option}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param addType 【必填】加群方式
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupAddOption(long botQQ, Long groupId, Long addType);

    /**
     * 设置群机器人加群选项。
     * <p>
     * 对应 NapCat API: {@code set_group_robot_add_option}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupRobotAddOption(long botQQ, Long groupId);

    /**
     * 设置群搜索选项。
     * <p>
     * 对应 NapCat API: {@code set_group_search}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupSearch(long botQQ, Long groupId);

    /**
     * 设置群备注。
     * <p>
     * 设置群备注
     * <p>
     * 对应 NapCat API: {@code set_group_remark}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param remark 【必填】备注
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupRemark(long botQQ, Long groupId, String remark);

    /**
     * 获取群详细信息 (扩展)。
     * <p>
     * 对应 NapCat API: {@code get_group_info_ex}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> getGroupInfoEx(long botQQ, Long groupId);

    /**
     * 群打卡。
     * <p>
     * 对应 NapCat API: {@code set_group_sign}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupSign(long botQQ, Long groupId);

    /**
     * 群打卡。
     * <p>
     * 对应 NapCat API: {@code send_group_sign}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> sendGroupSign(long botQQ, Long groupId);

    /**
     * 获取群组今日打卡列表。
     * <p>
     * 对应 NapCat API: {@code get_group_signed_list}
     * <p>
     * 分类：群组扩展
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
    CompletableFuture<ApiResponse<List<GroupSignedData>>> getGroupSignedList(long botQQ, Long groupId);

    /**
     * 获取群列表。
     * <p>
     * 获取当前帐号的群聊列表
     * <p>
     * 对应 NapCat API: {@code get_group_list}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param noCache 【可选】是否不使用缓存
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<List<String>>> getGroupList(long botQQ, Boolean noCache);

    /**
     * 获取群信息。
     * <p>
     * 获取群聊的基本信息
     * <p>
     * 对应 NapCat API: {@code get_group_info}
     * <p>
     * 分类：群组接口
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
    CompletableFuture<ApiResponse<GroupInfoData>> getGroupInfo(long botQQ, Long groupId);

    /**
     * 获取群成员列表。
     * <p>
     * 获取群聊中的所有成员列表
     * <p>
     * 对应 NapCat API: {@code get_group_member_list}
     * <p>
     * 分类：群组接口
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
    CompletableFuture<ApiResponse<List<GroupMemberData>>> getGroupMemberList(long botQQ, Long groupId);

    /**
     * 获取群成员信息。
     * <p>
     * 获取群聊中指定成员的信息
     * <p>
     * 对应 NapCat API: {@code get_group_member_info}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param userId 【必填】QQ号
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupMemberInfoData>> getGroupMemberInfo(long botQQ, Long groupId, Long userId);

    /**
     * 发送群消息。
     * <p>
     * 发送群消息
     * <p>
     * 对应 NapCat API: {@code send_group_msg}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【可选】群号
     * @param message 【必填】OneBot 11 消息混合类型
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupMsgData>> sendGroupMsg(long botQQ, Long groupId, String message);

    /**
     * 处理加群请求。
     * <p>
     * 同意或拒绝加群请求或邀请
     * <p>
     * 对应 NapCat API: {@code set_group_add_request}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param flag 【必填】请求flag
     * @param approve 【可选】是否同意
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupAddRequest(long botQQ, String flag, Boolean approve);

    /**
     * 退出群组。
     * <p>
     * 退出或解散指定群聊
     * <p>
     * 对应 NapCat API: {@code set_group_leave}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param isDismiss 【可选】是否解散
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupLeave(long botQQ, Long groupId, Boolean isDismiss);

    /**
     * 全员禁言。
     * <p>
     * 开启或关闭指定群聊的全员禁言
     * <p>
     * 对应 NapCat API: {@code set_group_whole_ban}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param enable 【可选】是否开启全员禁言
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupWholeBan(long botQQ, Long groupId, Boolean enable);

    /**
     * 群组禁言。
     * <p>
     * 禁言群聊中的指定成员
     * <p>
     * 对应 NapCat API: {@code set_group_ban}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param userId 【必填】用户QQ
     * @param duration 【必填】禁言时长(秒)（默认 0）
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupBan(long botQQ, Long groupId, Long userId, Long duration);

    /**
     * 群组踢人。
     * <p>
     * 将指定成员踢出群聊
     * <p>
     * 对应 NapCat API: {@code set_group_kick}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param userId 【必填】用户QQ
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
    CompletableFuture<ApiResponse<VoidData>> setGroupKick(long botQQ, Long groupId, Long userId, Boolean rejectAddRequest);

    /**
     * 设置群管理员。
     * <p>
     * 设置或取消群聊中的管理员
     * <p>
     * 对应 NapCat API: {@code set_group_admin}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param userId 【必填】用户QQ
     * @param enable 【可选】是否设置为管理员
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupAdmin(long botQQ, Long groupId, Long userId, Boolean enable);

    /**
     * 设置群名称。
     * <p>
     * 修改指定群聊的名称
     * <p>
     * 对应 NapCat API: {@code set_group_name}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param groupName 【必填】群名称
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupName(long botQQ, Long groupId, String groupName);

    /**
     * 设置群名片。
     * <p>
     * 设置群聊中指定成员的群名片
     * <p>
     * 对应 NapCat API: {@code set_group_card}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param userId 【必填】用户QQ
     * @param card 【可选】群名片
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupCard(long botQQ, Long groupId, Long userId, String card);

    /**
     * 获取群公告。
     * <p>
     * 获取指定群聊中的公告列表
     * <p>
     * 对应 NapCat API: {@code _get_group_notice}
     * <p>
     * 分类：群组接口
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
    CompletableFuture<ApiResponse<List<GroupNoticeData>>> getGroupNotice(long botQQ, Long groupId);

    /**
     * 获取群精华消息。
     * <p>
     * 获取指定群聊中的精华消息列表
     * <p>
     * 对应 NapCat API: {@code get_essence_msg_list}
     * <p>
     * 分类：群组接口
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
    CompletableFuture<ApiResponse<List<EssenceMsgData>>> getEssenceMsgList(long botQQ, Long groupId);

    /**
     * 获取群忽略通知。
     * <p>
     * 获取被忽略的入群申请和邀请通知
     * <p>
     * 对应 NapCat API: {@code get_group_ignored_notifies}
     * <p>
     * 分类：群组接口
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
    CompletableFuture<ApiResponse<GroupIgnoredNotifiesData>> getGroupIgnoredNotifies(long botQQ);

    /**
     * 移出精华消息。
     * <p>
     * 将一条消息从群精华消息列表中移出
     * <p>
     * 对应 NapCat API: {@code delete_essence_msg}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
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
    CompletableFuture<ApiResponse<VoidData>> deleteEssenceMsg(long botQQ, Long messageId);

    /**
     * 设置精华消息。
     * <p>
     * 将一条消息设置为群精华消息
     * <p>
     * 对应 NapCat API: {@code set_essence_msg}
     * <p>
     * 分类：群组接口
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
    CompletableFuture<ApiResponse<VoidData>> setEssenceMsg(long botQQ, Long messageId);

    /**
     * 删除群公告。
     * <p>
     * 删除群聊中的公告
     * <p>
     * 对应 NapCat API: {@code _del_group_notice}
     * <p>
     * 分类：群组接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param noticeId 【必填】公告ID
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> delGroupNotice(long botQQ, Long groupId, String noticeId);

    /**
     * 获取群禁言列表。
     * <p>
     * 对应 NapCat API: {@code get_group_shut_list}
     * <p>
     * 分类：群组接口
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
    CompletableFuture<ApiResponse<List<GroupShutData>>> getGroupShutList(long botQQ, Long groupId);

    /**
     * 获取群被忽略的加群请求。
     * <p>
     * 对应 NapCat API: {@code get_group_ignore_add_request}
     * <p>
     * 分类：群组接口
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
    CompletableFuture<ApiResponse<List<GroupIgnoreAddRequestData>>> getGroupIgnoreAddRequest(long botQQ);
}