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
 * 系统相关 API 接口。
 * <p>
 * 提供登录信息、版本状态、缓存清理、Cookies/RKey/CSRF Token 获取等系统级操作。
 * 所有方法通过指定 {@code botQQ} 区分目标 Bot 连接。
 */
@SuppressWarnings("unused")
public interface ApiSystem {

    /**
     * 处理可疑好友申请。
     * <p>
     * 同意或拒绝系统的可疑好友申请
     * <p>
     * 对应 NapCat API: {@code set_doubt_friends_add_request}
     * <p>
     * 分类：系统接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param flag 【必填】请求 flag
     * @param approve 【必填】是否同意 (强制为 true)（默认 True）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<String>> setDoubtFriendsAddRequest(long botQQ, String flag, Boolean approve);

    /**
     * 获取可疑好友申请。
     * <p>
     * 获取系统的可疑好友申请列表
     * <p>
     * 对应 NapCat API: {@code get_doubt_friends_add_request}
     * <p>
     * 分类：系统接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 【必填】获取数量（默认 50）
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> getDoubtFriendsAddRequest(long botQQ, Integer count);

    /**
     * 获取登录号信息。
     * <p>
     * 获取当前登录帐号的信息
     * <p>
     * 对应 NapCat API: {@code get_login_info}
     * <p>
     * 分类：系统接口
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
    CompletableFuture<ApiResponse<String>> getLoginInfo(long botQQ);

    /**
     * 获取版本信息。
     * <p>
     * 获取版本信息
     * <p>
     * 对应 NapCat API: {@code get_version_info}
     * <p>
     * 分类：系统接口
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
    CompletableFuture<ApiResponse<VersionInfoData>> getVersionInfo(long botQQ);

    /**
     * 是否可以发送语音。
     * <p>
     * 检查是否可以发送语音
     * <p>
     * 对应 NapCat API: {@code can_send_record}
     * <p>
     * 分类：系统接口
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
    CompletableFuture<ApiResponse<RecordData>> canSendRecord(long botQQ);

    /**
     * 是否可以发送图片。
     * <p>
     * 检查是否可以发送图片
     * <p>
     * 对应 NapCat API: {@code can_send_image}
     * <p>
     * 分类：系统接口
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
    CompletableFuture<ApiResponse<RecordData>> canSendImage(long botQQ);

    /**
     * 获取运行状态。
     * <p>
     * 获取运行状态
     * <p>
     * 对应 NapCat API: {@code get_status}
     * <p>
     * 分类：系统接口
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
    CompletableFuture<ApiResponse<StatusData>> getStatus(long botQQ);

    /**
     * 获取 CSRF Token。
     * <p>
     * 获取 CSRF Token
     * <p>
     * 对应 NapCat API: {@code get_csrf_token}
     * <p>
     * 分类：系统接口
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
    CompletableFuture<ApiResponse<CsrfTokenData>> getCsrfToken(long botQQ);

    /**
     * 获取登录凭证。
     * <p>
     * 获取登录凭证
     * <p>
     * 对应 NapCat API: {@code get_credentials}
     * <p>
     * 分类：系统接口
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
    CompletableFuture<ApiResponse<CredentialsData>> getCredentials(long botQQ, String domain);

    /**
     * 获取Packet状态。
     * <p>
     * 获取底层Packet服务的运行状态
     * <p>
     * 对应 NapCat API: {@code nc_get_packet_status}
     * <p>
     * 分类：系统接口
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
    CompletableFuture<ApiResponse<VoidData>> ncGetPacketStatus(long botQQ);

    /**
     * 重启服务。
     * <p>
     * 重启服务
     * <p>
     * 对应 NapCat API: {@code set_restart}
     * <p>
     * 分类：系统接口
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
    CompletableFuture<ApiResponse<VoidData>> setRestart(long botQQ);

    /**
     * 获取群系统消息。
     * <p>
     * 获取群系统消息
     * <p>
     * 对应 NapCat API: {@code get_group_system_msg}
     * <p>
     * 分类：系统接口
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 【必填】获取的消息数量（默认 50）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<GroupIgnoredNotifiesData>> getGroupSystemMsg(long botQQ, String count);

    /**
     * 清理缓存。
     * <p>
     * 清理缓存
     * <p>
     * 对应 NapCat API: {@code clean_cache}
     * <p>
     * 分类：系统接口
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
    CompletableFuture<ApiResponse<VoidData>> cleanCache(long botQQ);

    /**
     * 获取扩展 RKey。
     * <p>
     * 对应 NapCat API: {@code get_rkey}
     * <p>
     * 分类：系统扩展
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
    CompletableFuture<ApiResponse<List<RkeyData>>> getRkey(long botQQ);

    /**
     * 获取 RKey 服务器。
     * <p>
     * 对应 NapCat API: {@code get_rkey_server}
     * <p>
     * 分类：系统扩展
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
    CompletableFuture<ApiResponse<RkeyServerData>> getRkeyServer(long botQQ);

    /**
     * 设置在线状态。
     * <p>
     * ## 状态列表
     * ### 在线
     * ```json5;
     * { "status": 10, "ext_status": 0, "battery_status": 0; }
     * ```
     * ### Q我吧
     * ```json5;
     * { "status": 60, "ext_status": 0, "battery_status": 0; }
     * ```
     * ### 离开
     * ```json5;
     * { "status": 30, "ext_status": 0, "battery_status": 0; }
     * ```
     * ### 忙碌
     * ```json5;
     * { "status": 50, "ext_status": 0, "battery_status": 0; }
     * ```
     * ### 请勿打扰
     * ```json5;
     * { "status": 70, "ext_status": 0, "battery_status": 0; }
     * ```
     * ### 隐身
     * ```json5;
     * { "status": 40, "ext_status": 0, "battery_status": 0; }
     * ```
     * ### 听歌中
     * ```json5;
     * { "status": 10, "ext_status": 1028, "battery_status": 0; }
     * ```
     * ### 春日限定
     * ```json5;
     * { "status": 10, "ext_status": 2037, "battery_status": 0; }
     * ```
     * ### 一起元梦
     * ```json5;
     * { "status": 10, "ext_status": 2025, "battery_status": 0; }
     * ```
     * ### 求星搭子
     * ```json5;
     * { "status": 10, "ext_status": 2026, "battery_status": 0; }
     * ```
     * ### 被掏空
     * ```json5;
     * { "status": 10, "ext_status": 2014, "battery_status": 0; }
     * ```
     * ### 今日天气
     * ```json5;
     * { "status": 10, "ext_status": 1030, "battery_status": 0; }
     * ```
     * ### 我crash了
     * ```json5;
     * { "status": 10, "ext_status": 2019, "battery_status": 0; }
     * ```
     * ### 爱你
     * ```json5;
     * { "status": 10, "ext_status": 2006, "battery_status": 0; }
     * ```
     * ### 恋爱中
     * ```json5;
     * { "status": 10, "ext_status": 1051, "battery_status": 0; }
     * ```
     * ### 好运锦鲤
     * ```json5;
     * { "status": 10, "ext_status": 1071, "battery_status": 0; }
     * ```
     * ### 水逆退散
     * ```json5;
     * { "status": 10, "ext_status": 1201, "battery_status": 0; }
     * ```
     * ### 嗨到飞起
     * ```json5;
     * { "status": 10, "ext_status": 1056, "battery_status": 0; }
     * ```
     * ### 元气满满
     * ```json5;
     * { "status": 10, "ext_status": 1058, "battery_status": 0; }
     * ```
     * ### 宝宝认证
     * ```json5;
     * { "status": 10, "ext_status": 1070, "battery_status": 0; }
     * ```
     * ### 一言难尽
     * ```json5;
     * { "status": 10, "ext_status": 1063, "battery_status": 0; }
     * ```
     * ### 难得糊涂
     * ```json5;
     * { "status": 10, "ext_status": 2001, "battery_status": 0; }
     * ```
     * ### emo中
     * ```json5;
     * { "status": 10, "ext_status": 1401, "battery_status": 0; }
     * ```
     * ### 我太难了
     * ```json5;
     * { "status": 10, "ext_status": 1062, "battery_status": 0; }
     * ```
     * ### 我想开了
     * ```json5;
     * { "status": 10, "ext_status": 2013, "battery_status": 0; }
     * ```
     * ### 我没事
     * ```json5;
     * { "status": 10, "ext_status": 1052, "battery_status": 0; }
     * ```
     * ### 想静静
     * ```json5;
     * { "status": 10, "ext_status": 1061, "battery_status": 0; }
     * ```
     * ### 悠哉哉
     * ```json5;
     * { "status": 10, "ext_status": 1059, "battery_status": 0; }
     * ```
     * ### 去旅行
     * ```json5;
     * { "status": 10, "ext_status": 2015, "battery_status": 0; }
     * ```
     * ### 信号弱
     * ```json5;
     * { "status": 10, "ext_status": 1011, "battery_status": 0; }
     * ```
     * ### 出去浪
     * ```json5;
     * { "status": 10, "ext_status": 2003, "battery_status": 0; }
     * ```
     * ### 肝作业
     * ```json5;
     * { "status": 10, "ext_status": 2012, "battery_status": 0; }
     * ```
     * ### 学习中
     * ```json5;
     * { "status": 10, "ext_status": 1018, "battery_status": 0; }
     * ```
     * ### 搬砖中
     * ```json5;
     * { "status": 10, "ext_status": 2023, "battery_status": 0; }
     * ```
     * ### 摸鱼中
     * ```json5;
     * { "status": 10, "ext_status": 1300, "battery_status": 0; }
     * ```
     * ### 无聊中
     * ```json5;
     * { "status": 10, "ext_status": 1060, "battery_status": 0; }
     * ```
     * ### timi中
     * ```json5;
     * { "status": 10, "ext_status": 1027, "battery_status": 0; }
     * ```
     * ### 睡觉中
     * ```json5;
     * { "status": 10, "ext_status": 1016, "battery_status": 0; }
     * ```
     * ### 熬夜中
     * ```json5;
     * { "status": 10, "ext_status": 1032, "battery_status": 0; }
     * ```
     * ### 追剧中
     * ```json5;
     * { "status": 10, "ext_status": 1021, "battery_status": 0; }
     * ```
     * ### 我的电量
     * ```json5;
     * {
     * "status": 10,
     * "ext_status": 1000,
     * "battery_status": 0;
     * }
     * ```
     * <p>
     * 对应 NapCat API: {@code set_online_status}
     * <p>
     * 分类：系统扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param status 【必填】在线状态
     * @param extStatus 【必填】扩展状态
     * @param batteryStatus 【必填】电量状态
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setOnlineStatus(long botQQ, String status, String extStatus, String batteryStatus);

    /**
     * 获取机器人 UIN 范围。
     * <p>
     * 对应 NapCat API: {@code get_robot_uin_range}
     * <p>
     * 分类：系统扩展
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
    CompletableFuture<ApiResponse<List<String>>> getRobotUinRange(long botQQ);

    /**
     * 获取自定义表情。
     * <p>
     * 对应 NapCat API: {@code fetch_custom_face}
     * <p>
     * 分类：系统扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 【必填】获取数量（默认 48）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<List<String>>> fetchCustomFace(long botQQ, String count);

    /**
     * 获取自定义表情详情。
     * <p>
     * 对应 NapCat API: {@code fetch_custom_face_detail}
     * <p>
     * 分类：系统扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 【必填】获取数量（默认 48）
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> fetchCustomFaceDetail(long botQQ, String count);

    /**
     * 添加自定义表情。
     * <p>
     * 对应 NapCat API: {@code add_custom_face}
     * <p>
     * 分类：系统扩展
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
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> addCustomFace(long botQQ, String file, String emojiId, String packageId, String fileName, String fileSize, String md5, Boolean isMarkFace, Boolean isOrigin);

    /**
     * 删除自定义表情。
     * <p>
     * 对应 NapCat API: {@code delete_custom_face}
     * <p>
     * 分类：系统扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param resId 【可选】fetch_custom_face_detail返回的resId
     * @param id 【可选】native deleteFavEmoji字符串ID，通常为resId
     * @param ids 【可选】native deleteFavEmoji字符串ID列表，通常为resId列表
     * @param md5 【可选】表情MD5，不能直接删除，请先通过fetch_custom_face_detail获取resId
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> deleteCustomFace(long botQQ, String resId, String id, List<String> ids, String md5);

    /**
     * 修改自定义表情描述。
     * <p>
     * 对应 NapCat API: {@code set_custom_face_desc}
     * <p>
     * 分类：系统扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param emojiId 【必填】表情ID
     * @param resId 【必填】资源ID
     * @param md5 【必填】表情MD5
     * @param desc 【必填】新的表情描述
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setCustomFaceDesc(long botQQ, String emojiId, String resId, String md5, String desc);

    /**
     * 设置输入状态。
     * <p>
     * 对应 NapCat API: {@code set_input_status}
     * <p>
     * 分类：系统扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param userId 【必填】QQ号
     * @param eventType 【必填】事件类型
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> setInputStatus(long botQQ, Long userId, Long eventType);

    /**
     * 获取用户在线状态。
     * <p>
     * 对应 NapCat API: {@code nc_get_user_status}
     * <p>
     * 分类：系统扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
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
    CompletableFuture<ApiResponse<UserStatusData>> ncGetUserStatus(long botQQ, Long userId);

    /**
     * 获取 RKey。
     * <p>
     * 对应 NapCat API: {@code nc_get_rkey}
     * <p>
     * 分类：系统扩展
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
    CompletableFuture<ApiResponse<List<String>>> ncGetRkey(long botQQ);

    /**
     * 获取小程序 Ark。
     * <p>
     * 对应 NapCat API: {@code get_mini_app_ark}
     * <p>
     * 分类：系统扩展
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
    CompletableFuture<ApiResponse<MiniAppArkData>> getMiniAppArk(long botQQ);

    /**
     * 发送原始数据包。
     * <p>
     * 对应 NapCat API: {@code send_packet}
     * <p>
     * 分类：系统扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param cmd 【必填】命令字
     * @param data 【必填】十六进制数据
     * @param rsp 【必填】是否等待响应（默认 True）
     * @return 异步响应，成功时 data 包含业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<String>> sendPacket(long botQQ, String cmd, String data, String rsp);

    /**
     * 退出登录。
     * <p>
     * 对应 NapCat API: {@code bot_exit}
     * <p>
     * 分类：系统扩展
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
    CompletableFuture<ApiResponse<VoidData>> botExit(long botQQ);

    /**
     * 获取收藏列表。
     * <p>
     * 对应 NapCat API: {@code get_collection_list}
     * <p>
     * 分类：系统扩展
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param category 【必填】分类ID
     * @param count 【必填】获取数量（默认 50）
     * @return 异步响应，无业务数据
     * <p>
     * <b>可能的错误情况：</b>
     * <ul>
     *   <li>{@code retcode=1400: 请求参数错误或业务逻辑执行失败}</li>
     *   <li>{@code retcode=1401: 权限不足}</li>
     *   <li>{@code retcode=1404: 资源不存在}</li>
     * </ul>
     */
    CompletableFuture<ApiResponse<VoidData>> getCollectionList(long botQQ, String category, String count);
}