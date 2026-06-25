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
 * 好友/用户相关 API 接口。
 * <p>
 * 提供好友列表获取、陌生人信息查询、好友申请处理、QQ 资料设置等操作。
 * 所有方法通过指定 {@code botQQ} 区分目标 Bot 连接。
 */
@SuppressWarnings("unused")
public interface ApiFriend {

    /**
     * 设置好友备注。
     * <p>
     * 设置好友备注
     * <p>
     * 对应 NapCat API: {@code set_friend_remark}
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】对方 QQ 号
     * @param remark 【必填】备注内容
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 备注设置失败（好友不存在或非法输入）}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setFriendRemark(long botQQ, Long userId, String remark);

    /**
     * 获取好友列表。
     * <p>
     * 获取当前帐号的好友列表
     * <p>
     * 对应 NapCat API: {@code get_friend_list}
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
    CompletableFuture<ApiResponse<List<String>>> getFriendList(long botQQ, String noCache);

    /**
     * 处理加好友请求。
     * <p>
     * 同意或拒绝加好友请求
     * <p>
     * 对应 NapCat API: {@code set_friend_add_request}
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param flag 【必填】加好友请求的 flag (需从上报中获取)
     * @param approve 【可选】是否同意请求
     * @param remark 【可选】添加后的好友备注
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setFriendAddRequest(long botQQ, String flag, String approve, String remark);

    /**
     * 获取 Cookies。
     * <p>
     * 获取指定域名的 Cookies
     * <p>
     * 对应 NapCat API: {@code get_cookies}
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param domain 【必填】需要获取 cookies 的域名
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<CookiesData>> getCookies(long botQQ, String domain);

    /**
     * 获取最近会话。
     * <p>
     * 获取最近会话
     * <p>
     * 对应 NapCat API: {@code get_recent_contact}
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 【必填】获取的数量（默认 10）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<List<RecentContactData>>> getRecentContact(long botQQ, String count);

    /**
     * 获取带分组的好友列表。
     * <p>
     * 对应 NapCat API: {@code get_friends_with_category}
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
    CompletableFuture<ApiResponse<List<FriendsWithCategoryData>>> getFriendsWithCategory(long botQQ);

    /**
     * 获取资料点赞。
     * <p>
     * 对应 NapCat API: {@code get_profile_like}
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【可选】QQ号
     * @param start 【必填】起始位置（默认 0）
     * @param count 【必填】获取数量（默认 10）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<ProfileLikeData>> getProfileLike(long botQQ, Long userId, String start, String count);

    /**
     * 设置自定义在线状态。
     * <p>
     * 设置自定义在线状态
     * <p>
     * 对应 NapCat API: {@code set_diy_online_status}
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param faceId 【必填】图标ID
     * @param faceType 【必填】图标类型（默认 1）
     * @param wording 【必填】状态文字内容（默认  ）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<String>> setDiyOnlineStatus(long botQQ, String faceId, String faceType, String wording);

    /**
     * 获取单向好友列表。
     * <p>
     * 对应 NapCat API: {@code get_unidirectional_friend_list}
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
    CompletableFuture<ApiResponse<List<UnidirectionalFriendData>>> getUnidirectionalFriendList(long botQQ);
}