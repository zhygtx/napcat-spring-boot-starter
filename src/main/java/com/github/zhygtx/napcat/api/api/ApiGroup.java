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
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> delGroupAlbumMedia(long botQQ, Long groupId, String albumId, String lloc);

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
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupAlbumMediaLike(long botQQ, Long groupId, String albumId, String batchId, String lloc);

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
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> cancelGroupAlbumMediaLike(long botQQ, Long groupId, String albumId, String batchId, String lloc);

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
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> doGroupAlbumComment(long botQQ, Long groupId, String albumId, String lloc, String content);

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
     * @param attachInfo 【可选】附加信息（用于分页）（默认 ）
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> getGroupAlbumMediaList(long botQQ, Long groupId, String albumId, String attachInfo);

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
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> uploadImageToQunAlbum(long botQQ, Long groupId, String albumId, String albumName, String file);

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
     * @param groupQuestion 【可选】加群问题
     * @param groupAnswer 【可选】加群答案
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupAddOption(long botQQ, Long groupId, Long addType, String groupQuestion, String groupAnswer);

    /**
     * 设置群机器人加群选项。
     * <p>
     * 对应 NapCat API: {@code set_group_robot_add_option}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param robotMemberSwitch 【可选】机器人成员开关
     * @param robotMemberExamine 【可选】机器人成员审核
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupRobotAddOption(long botQQ, Long groupId, Long robotMemberSwitch, Long robotMemberExamine);

    /**
     * 设置群搜索选项。
     * <p>
     * 对应 NapCat API: {@code set_group_search}
     * <p>
     * 分类：群组扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param groupId 【必填】群号
     * @param noCodeFingerOpen 【可选】未知
     * @param noFingerOpen 【可选】未知
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupSearch(long botQQ, Long groupId, Long noCodeFingerOpen, Long noFingerOpen);

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
    CompletableFuture<ApiResponse<List<String>>> getGroupList(long botQQ, String noCache);

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
    CompletableFuture<ApiResponse<String>> getGroupInfo(long botQQ, Long groupId);

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
    CompletableFuture<ApiResponse<List<String>>> getGroupMemberList(long botQQ, Long groupId, String noCache);

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
    CompletableFuture<ApiResponse<String>> getGroupMemberInfo(long botQQ, Long groupId, Long userId, String noCache);

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
     * @param reason 【可选】拒绝理由
     * @param count 【可选】搜索通知数量（默认 100）
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupAddRequest(long botQQ, String flag, String approve, String reason, Integer count);

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
    CompletableFuture<ApiResponse<VoidData>> setGroupLeave(long botQQ, Long groupId, String isDismiss);

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
     * @param msgSeq 【可选】消息序号
     * @param msgRandom 【可选】消息随机数
     * @param groupId 【可选】群号
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> deleteEssenceMsg(long botQQ, String messageId, String msgSeq, String msgRandom, Long groupId);

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
    CompletableFuture<ApiResponse<VoidData>> setEssenceMsg(long botQQ, String messageId);

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
    CompletableFuture<ApiResponse<List<String>>> getGroupShutList(long botQQ, Long groupId);

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