package com.github.zhygtx.napcat.api.api;

import com.github.zhygtx.napcat.api.response.*;
import com.github.zhygtx.napcat.api.response.system.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 系统相关 API 接口。
 * <p>
 * 提供登录信息、版本状态、缓存清理、Cookies/RKey/CSRF Token 获取、
 * 在线客户端查询、URL 安全检测等系统级操作。
 * 所有方法通过指定 {@code botQQ} 区分目标 Bot 连接。
 */
@SuppressWarnings("unused")
public interface ApiSystem {

    /**
     * 获取登录号信息。
     * <p>
     * 返回当前机器人账号的基本信息，包括 QQ 号、昵称、性别、年龄、等级、
     * 手机号、邮箱、QID、登录天数等。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 包含登录号详细信息
     */
    CompletableFuture<ApiResponse<LoginInfoData>> getLoginInfo(long botQQ);

    /**
     * 获取版本信息。
     * <p>
     * 返回 NapCat 客户端/OneBot 协议的版本信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 包含 appName / protocolVersion / appVersion
     */
    CompletableFuture<ApiResponse<VersionInfoData>> getVersionInfo(long botQQ);

    /**
     * 获取运行状态。
     * <p>
     * 返回机器人当前运行状态，包括是否在线、状态是否良好、统计信息等。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 包含 online / good / stat 字段
     */
    CompletableFuture<ApiResponse<StatusData>> getStatus(long botQQ);

    /**
     * 获取机器人 UIN 范围。
     * <p>
     * 返回机器人可用的 UIN（QQ 号）范围，用于某些特殊场景的识别。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 为 UIN 范围列表（minUin ~ maxUin）
     */
    CompletableFuture<ApiResponse<List<RobotUinRangeData>>> getRobotUinRange(long botQQ);

    /**
     * 重启。
     * <p>
     * 重启 NapCat 客户端。重启操作会导致 WebSocket 连接断开，
     * 需要重新建立连接。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据（可能因重启导致 future 异常）
     */
    CompletableFuture<ApiResponse<VoidData>> setRestart(long botQQ);

    /**
     * 退出登录（优雅关闭）。
     * <p>
     * 令机器人账号安全退出。退出后需要重新连接并登录。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> botExit(long botQQ);

    /**
     * 清理缓存。
     * <p>
     * 清理机器人的本地缓存数据（如图片缓存、消息缓存等）。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> cleanCache(long botQQ);

    /**
     * 检查 URL 安全性。
     * <p>
     * 检测指定 URL 是否存在安全风险（如钓鱼、恶意链接等）。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param url   要检查的 URL 地址
     * @return 异步响应，无业务数据（可通过 retcode 判断结果）
     */
    CompletableFuture<ApiResponse<VoidData>> checkUrlSafely(long botQQ, String url);

    /**
     * 获取 Cookies。
     * <p>
     * 获取指定域名的 QQ 登录态 Cookies，用于调用其他腾讯系服务。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param domain 目标域名（如 {@code "qun.qq.com"}），可为 {@code null}
     * @return 异步响应，data 包含 cookies 字符串
     */
    CompletableFuture<ApiResponse<CookiesData>> getCookies(long botQQ, String domain);

    /**
     * 获取 CSRF Token。
     * <p>
     * 获取当前登录态的 CSRF Token（bkn/g_tk），用于安全校验。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 包含 token 值
     */
    CompletableFuture<ApiResponse<CsrfTokenData>> getCsrfToken(long botQQ);

    /**
     * 获取凭证（Cookies + CSRF Token）。
     * <p>
     * 同时返回 Cookies 和 CSRF Token，是 {@link #getCookies} 和
     * {@link #getCsrfToken} 的组合接口。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 同时包含 cookies 和 csrfToken
     */
    CompletableFuture<ApiResponse<CredentialsData>> getCredentials(long botQQ);

    /**
     * 获取扩展 RKey。
     * <p>
     * 获取腾讯系服务所需的 RKey（Request Key）。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param domain 目标域名（可选）
     * @param type   请求类型（可选）
     * @return 异步响应，data 包含 rkey 映射
     */
    CompletableFuture<ApiResponse<RKeyData>> getRkey(long botQQ, String domain, String type);

    /**
     * 获取 RKey 服务器。
     * <p>
     * 获取 RKey 相关的服务器地址列表。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param domain 目标域名（可选）
     * @return 异步响应，data 包含服务器列表信息
     */
    CompletableFuture<ApiResponse<RKeyServerData>> getRkeyServer(long botQQ, String domain);

    /**
     * 获取 ClientKey。
     * <p>
     * 获取当前登录态的 ClientKey，用于某些腾讯服务的身份标识。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param domain 目标域名（可选）
     * @return 异步响应，data 包含 clientKey 字符串
     */
    CompletableFuture<ApiResponse<ClientKeyData>> getClientkey(long botQQ, String domain);

    /**
     * 获取 NC RKey（NapCat 自定义）。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> ncGetRkey(long botQQ);

    /**
     * 获取 NC 用户状态。
     * <p>
     * 查询指定用户的在线状态（NapCat 扩展）。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param userId 目标用户 QQ
     * @return 异步响应，包含用户状态信息
     */
    CompletableFuture<ApiResponse<VoidData>> ncGetUserStatus(long botQQ, String userId);

    /**
     * 获取 NC 发包状态。
     * <p>
     * 查询 NapCat 客户端的发包（消息发送）状态，包括是否可用和速率限制。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 包含 enable 和 interval 信息
     */
    CompletableFuture<ApiResponse<PacketStatusData>> ncGetPacketStatus(long botQQ);

    /**
     * 检查是否可以发送语音。
     * <p>
     * 检测当前机器人是否具备发送语音消息的能力。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 包含 yes（{@code true} 表示可以发送）
     */
    CompletableFuture<ApiResponse<CanSendData>> canSendRecord(long botQQ);

    /**
     * 检查是否可以发送图片。
     * <p>
     * 检测当前机器人是否具备发送图片消息的能力。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 包含 yes（{@code true} 表示可以发送）
     */
    CompletableFuture<ApiResponse<CanSendData>> canSendImage(long botQQ);

    /**
     * 设置在线状态。
     * <p>
     * 修改机器人的在线状态（如在线、离开、隐身等）。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param status 状态值（具体数值对应 OneBot 协议定义）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setOnlineStatus(long botQQ, long status);

    /**
     * 设置自定义在线状态。
     * <p>
     * 使用预定义的 DIY 在线状态模板（NapCat 扩展功能）。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param diyId 自定义状态 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setDiyOnlineStatus(long botQQ, String diyId);

    /**
     * 获取在线客户端列表。
     * <p>
     * 获取机器人账号当前登录的所有客户端设备信息（PC、手机、Pad 等）。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，data 为在线客户端列表（设备类型、名称、平台等）
     */
    CompletableFuture<ApiResponse<List<OnlineClientData>>> getOnlineClients(long botQQ);

    /**
     * 获取分享链接。
     * <p>
     * 生成一个短链接或分享形式的链接。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param url   要处理的原始 URL
     * @return 异步响应，data 包含分享链接、标题、内容等信息
     */
    CompletableFuture<ApiResponse<ShareLinkData>> getShareLink(long botQQ, String url);

    /**
     * 发送数据包。
     * <p>
     * 发送自定义协议数据包（NapCat 扩展，高级功能）。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param cmd   命令名称
     * @param body  数据包正文
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendPacket(long botQQ, String cmd, String body);
}
