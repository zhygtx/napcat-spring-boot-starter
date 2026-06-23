package com.github.zhygtx.napcat.api.api;

import com.github.zhygtx.napcat.api.response.*;
import com.github.zhygtx.napcat.api.response.friend.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 好友/用户相关 API 接口。
 * <p>
 * 提供好友列表获取、陌生人信息查询、好友申请处理、QQ 资料设置等操作。
 * 所有方法通过指定 {@code botQQ} 区分目标 Bot 连接。
 */
@SuppressWarnings("unused")
public interface ApiFriend {

    /**
     * 获取好友列表。
     * <p>
     * 返回当前机器人账号的所有好友信息，包含 QQ 号、昵称、备注、分组等。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 为好友列表（数组），每项含 userId/nickname/remark 等
     */
    CompletableFuture<ApiResponse<List<FriendListData>>> getFriendList(long botQQ);

    /**
     * 获取带分组的好友列表。
     * <p>
     * 与 {@link #getFriendList(long)} 不同，此接口按分组（分类）组织好友，
     * 返回结构为「分组 → 好友列表」的树形数据。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 为分组列表，每项含 categoryName + buddyList
     */
    CompletableFuture<ApiResponse<List<FriendCategoryData>>> getFriendsWithCategory(long botQQ);

    /**
     * 获取单向好友列表。
     * <p>
     * 单向好友是指对方已将机器人添加为好友，但机器人尚未添加对方。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 为单向好友列表
     */
    CompletableFuture<ApiResponse<List<FriendListData>>> getUnidirectionalFriendList(long botQQ);

    /**
     * 获取陌生人信息。
     * <p>
     * 查询非好友用户的基本信息，包括昵称、年龄、性别、QID、等级等。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param userId  目标用户 QQ
     * @param noCache 是否不使用缓存（{@code true} 时强制刷新数据）
     * @return 异步响应，data 包含用户详细信息
     */
    CompletableFuture<ApiResponse<StrangerInfoData>> getStrangerInfo(long botQQ, String userId, boolean noCache);

    /**
     * 处理加好友请求。
     * <p>
     * 同意或拒绝来自其他用户的好友申请。请求标识 {@code flag} 来自
     * 好友请求事件（{@code request_type = "friend"}）。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param flag    请求标识（来自事件上报）
     * @param approve 是否同意（{@code true} 同意 / {@code false} 拒绝）
     * @param remark  好友备注（同意时可选，拒绝时无效）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setFriendAddRequest(long botQQ, String flag, boolean approve, String remark);

    /**
     * 删除好友。
     * <p>
     * 将指定用户从机器人的好友列表中删除。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param userId 要删除的好友 QQ
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> deleteFriend(long botQQ, String userId);

    /**
     * 设置好友备注。
     * <p>
     * 修改指定好友的备注名称。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param userId 好友 QQ
     * @param remark 新的备注名称
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setFriendRemark(long botQQ, String userId, String remark);

    /**
     * 获取可疑好友申请列表。
     * <p>
     * 获取被系统标记为可疑的好友申请列表，用于审核处理。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，包含可疑好友申请信息
     */
    CompletableFuture<ApiResponse<VoidData>> getDoubFriendsAddRequest(long botQQ);

    /**
     * 处理可疑好友申请。
     * <p>
     * 对可疑好友申请进行通过或拒绝操作。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param uin     申请者的 UIN（来自可疑申请列表）
     * @param operate 操作类型（{@code true} 通过 / {@code false} 拒绝）
     * @param message 操作附言（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setDoubFriendsAddRequest(long botQQ, String uin, boolean operate, String message);

    /**
     * 获取资料点赞列表。
     * <p>
     * 获取机器人账号收到的点赞（赞）总数和近期点赞列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 包含点赞总数和近期列表
     */
    CompletableFuture<ApiResponse<ProfileLikeData>> getProfileLike(long botQQ);

    /**
     * 设置 QQ 头像。
     * <p>
     * 修改机器人的 QQ 头像。支持本地文件路径或在线图片 URL。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param file  头像图片路径（本地路径或 URL）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setQqAvatar(long botQQ, String file);

    /**
     * 设置 QQ 资料。
     * <p>
     * 修改机器人的个人资料，包括昵称、公司、邮箱、学校和个性签名。
     * 传入 {@code null} 的字段不会修改。
     *
     * @param botQQ       目标 Bot 的 QQ 号
     * @param nickname    新昵称（{@code null} 不修改）
     * @param company     公司/企业（{@code null} 不修改）
     * @param email       邮箱（{@code null} 不修改）
     * @param college     学校（{@code null} 不修改）
     * @param personalNote 个性签名（{@code null} 不修改）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setQqProfile(long botQQ, String nickname, String company, String email, String college, String personalNote);

    /**
     * 设置个性签名。
     * <p>
     * 修改机器人账号的个性签名（long_nick）。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param longNick 新的个性签名内容
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setSelfLongnick(long botQQ, String longNick);
}
