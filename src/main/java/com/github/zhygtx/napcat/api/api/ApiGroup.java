package com.github.zhygtx.napcat.api.api;

import com.github.zhygtx.napcat.api.response.*;
import com.github.zhygtx.napcat.api.response.group.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 群组相关 API 接口。
 * <p>
 * 提供群信息查询、成员管理、禁言/踢人、管理员设置、群公告/群打卡、
 * 群文件管理、群荣誉/精华消息等群组全量操作。
 * 所有方法通过指定 {@code botQQ} 区分目标 Bot 连接。
 */
@SuppressWarnings("unused")
public interface ApiGroup {

    /**
     * 获取群列表。
     * <p>
     * 返回机器人加入的所有群聊的基本信息（群号、群名、成员数等）。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param noCache 是否不使用缓存（{@code true} 时强制刷新）
     * @return 异步响应，data 为群列表（数组），每项含 groupId/groupName/memberCount 等
     */
    CompletableFuture<ApiResponse<List<GroupInfoData>>> getGroupList(long botQQ, boolean noCache);

    /**
     * 获取群信息。
     * <p>
     * 获取指定群聊的基本信息，包括群号、群名、成员数、最大成员数、全员禁言状态等。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，data 包含群基本信息
     */
    CompletableFuture<ApiResponse<GroupInfoData>> getGroupInfo(long botQQ, long groupQQ);

    /**
     * 获取群详细信息。
     * <p>
     * 获取指定群聊的详细信息，包括群主、创建时间、群分类、群标签、群描述等。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，data 包含群详细信息
     */
    CompletableFuture<ApiResponse<GroupDetailInfoData>> getGroupDetailInfo(long botQQ, long groupQQ);

    /**
     * 获取群信息（扩展）。
     * <p>
     * NapCat 扩展接口，返回群额外信息（如群主 QQ、群备注、管理员等）。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，data 包含群扩展信息
     */
    CompletableFuture<ApiResponse<GroupInfoExData>> getGroupInfoEx(long botQQ, long groupQQ);

    /**
     * 获取群成员信息。
     * <p>
     * 获取指定群中某位成员的详细信息，包括昵称、群名片、角色、等级、入群时间、最后发言时间等。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param userId  目标成员 QQ
     * @param noCache 是否不使用缓存（{@code true} 时强制刷新）
     * @return 异步响应，data 包含成员详细信息
     */
    CompletableFuture<ApiResponse<GroupMemberData>> getGroupMemberInfo(long botQQ, long groupQQ, String userId, boolean noCache);

    /**
     * 获取群成员列表。
     * <p>
     * 获取指定群聊中所有成员的简要信息列表。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param noCache 是否不使用缓存
     * @return 异步响应，data 为群成员列表
     */
    CompletableFuture<ApiResponse<List<GroupMemberData>>> getGroupMemberList(long botQQ, long groupQQ, boolean noCache);

    /**
     * 群单人禁言。
     * <p>
     * 对指定群成员进行禁言或解除禁言。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param userId  目标成员 QQ
     * @param duration 禁言时长（秒），{@code 0} 表示解除禁言
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupBan(long botQQ, long groupQQ, String userId, long duration);

    /**
     * 群全员禁言。
     * <p>
     * 开启或关闭指定群聊的全员禁言模式。需要机器人是群管理员或群主。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param enable  {@code true} 开启全员禁言 / {@code false} 关闭
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupWholeBan(long botQQ, long groupQQ, boolean enable);

    /**
     * 群组踢人。
     * <p>
     * 将指定成员踢出群聊。需要机器人是群管理员或群主。
     *
     * @param botQQ           目标 Bot 的 QQ 号
     * @param groupQQ         目标群号
     * @param userId          目标成员 QQ
     * @param rejectAddRequest 是否拒绝该成员再次申请加群
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupKick(long botQQ, long groupQQ, String userId, boolean rejectAddRequest);

    /**
     * 批量踢出群成员。
     * <p>
     * 一次性将多位成员踢出群聊。
     *
     * @param botQQ           目标 Bot 的 QQ 号
     * @param groupQQ         目标群号
     * @param userIds         目标成员 QQ 列表
     * @param rejectAddRequest 是否拒绝再次申请
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupKickMembers(long botQQ, long groupQQ, List<String> userIds, boolean rejectAddRequest);

    /**
     * 设置群管理员。
     * <p>
     * 设置或取消群管理员。需要机器人是群主。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param userId  目标成员 QQ
     * @param enable  {@code true} 设为管理员 / {@code false} 取消管理员
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupAdmin(long botQQ, long groupQQ, String userId, boolean enable);

    /**
     * 设置群名片。
     * <p>
     * 修改指定成员在群中的群名片（群昵称）。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param userId  目标成员 QQ
     * @param card    新的群名片（{@code null} 或空字符串表示删除名片）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupCard(long botQQ, long groupQQ, String userId, String card);

    /**
     * 设置群名称。
     * <p>
     * 修改群聊的名称。需要机器人是群管理员或群主。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param groupQQ  目标群号
     * @param groupName 新群名称
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupName(long botQQ, long groupQQ, String groupName);

    /**
     * 设置群备注。
     * <p>
     * 修改机器人对该群的备注名称（仅自己可见）。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param remark  新备注
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupRemark(long botQQ, long groupQQ, String remark);

    /**
     * 设置群头像。
     * <p>
     * 修改群聊的头像（群图标）。需要机器人是群主。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param file   头像文件路径（支持本地路径或 URL）
     * @param cache  是否使用缓存（{@code 1} 使用 / {@code 0} 不使用）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<GroupPortraitResult>> setGroupPortrait(long botQQ, long groupQQ, String file, int cache);

    /**
     * 获取群禁言列表。
     * <p>
     * 获取指定群聊中当前被禁言的成员列表。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，data 为禁言成员列表（含 userId 和禁言截止时间）
     */
    CompletableFuture<ApiResponse<List<GroupShutMemberData>>> getGroupShutList(long botQQ, long groupQQ);

    /**
     * 设置群加群选项。
     * <p>
     * 修改群的加群验证方式（如需要验证消息、允许任何人加入等）。
     *
     * @param botQQ        目标 Bot 的 QQ 号
     * @param groupQQ      目标群号
     * @param addType      加群类型
     * @param groupQuestion 加群问题
     * @param groupAnswer  加群答案
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupAddOption(long botQQ, long groupQQ, String addType, String groupQuestion, String groupAnswer);

    /**
     * 设置群机器人加群选项。
     * <p>
     * 修改群聊是否允许机器人（第三方 Bot）加入。
     *
     * @param botQQ              目标 Bot 的 QQ 号
     * @param groupQQ            目标群号
     * @param robotMemberSwitch  机器人成员开关
     * @param robotMemberExamine 机器人成员审核
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupRobotAddOption(long botQQ, long groupQQ, String robotMemberSwitch, String robotMemberExamine);

    /**
     * 设置群搜索选项。
     * <p>
     * 修改群聊是否可通过搜索被找到。
     *
     * @param botQQ           目标 Bot 的 QQ 号
     * @param groupQQ         目标群号
     * @param noCodeFingerOpen 免码指纹开关
     * @param noFingerOpen    免指纹开关
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupSearch(long botQQ, long groupQQ, String noCodeFingerOpen, String noFingerOpen);

    /**
     * 设置群待办。
     * <p>
     * 在群聊中创建一个群待办事项（需引用一条消息）。
     *
     * @param botQQ      目标 Bot 的 QQ 号
     * @param groupQQ    目标群号
     * @param messageId  引用的消息 ID
     * @param messageSeq 消息序号（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupTodo(long botQQ, long groupQQ, String messageId, String messageSeq);

    /**
     * 取消群待办。
     * <p>
     * 取消指定的群待办事项。
     *
     * @param botQQ      目标 Bot 的 QQ 号
     * @param groupQQ    目标群号
     * @param messageId  消息 ID
     * @param messageSeq 消息序号（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> cancelGroupTodo(long botQQ, long groupQQ, String messageId, String messageSeq);

    /**
     * 完成群待办。
     * <p>
     * 将指定的群待办事项标记为已完成。
     *
     * @param botQQ      目标 Bot 的 QQ 号
     * @param groupQQ    目标群号
     * @param messageId  消息 ID
     * @param messageSeq 消息序号（可选）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> completeGroupTodo(long botQQ, long groupQQ, String messageId, String messageSeq);

    /**
     * 设置群专属头衔。
     * <p>
     * 为指定群成员设置群头衔（群称号）。需要机器人是群主。
     *
     * @param botQQ        目标 Bot 的 QQ 号
     * @param groupQQ      目标群号
     * @param userId       目标成员 QQ
     * @param specialTitle 头衔名称
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupSpecialTitle(long botQQ, long groupQQ, String userId, String specialTitle);

    /**
     * 退出群组。
     * <p>
     * 令机器人主动退出指定群聊。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param groupQQ   目标群号
     * @param isDismiss 是否解散群（仅群主可用，{@code true} 时解散群）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupLeave(long botQQ, long groupQQ, boolean isDismiss);

    /**
     * 处理加群请求。
     * <p>
     * 同意或拒绝群加群请求/邀请。请求标识 {@code flag} 来自加群请求事件。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param flag    请求标识（来自事件上报的 flag 字段）
     * @param approve 是否同意（{@code true} 同意 / {@code false} 拒绝）
     * @param reason  拒绝理由（拒绝时可选）
     * @param count   批量处理数量（默认 100）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupAddRequest(long botQQ, String flag, boolean approve, String reason, int count);

    /**
     * 获取群荣誉信息。
     * <p>
     * 获取群聊的荣誉榜单，包括龙王、聊天达人、表情达人等。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @param type   荣誉类型（{@code "talkative"} 龙王 / {@code "performer"} 达人 / {@code "emotion"} 表情等）
     * @return 异步响应，data 包含荣誉信息
     */
    CompletableFuture<ApiResponse<GroupHonorInfoData>> getGroupHonorInfo(long botQQ, long groupQQ, String type);

    /**
     * 获取群系统消息。
     * <p>
     * 获取群聊的系统消息，包括待处理的加群请求和邀请。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @param count 获取数量（默认 50）
     * @return 异步响应，data 包含加群请求和邀请列表
     */
    CompletableFuture<ApiResponse<GroupSystemMsgData>> getGroupSystemMsg(long botQQ, int count);

    /**
     * 获取群艾特全体剩余次数。
     * <p>
     * 查询指定群聊中机器人 @全体成员 的剩余次数。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，data 包含剩余次数信息
     */
    CompletableFuture<ApiResponse<GroupAtAllRemainData>> getGroupAtAllRemain(long botQQ, long groupQQ);

    /**
     * 获取群精华消息列表。
     * <p>
     * 获取指定群聊的所有精华消息列表。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，data 为精华消息列表
     */
    CompletableFuture<ApiResponse<List<EssenceMsgData>>> getEssenceMsgList(long botQQ, long groupQQ);

    /**
     * 设置精华消息。
     * <p>
     * 将指定消息设置为群精华消息。需要机器人是群管理员或群主。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param messageId 要设置为精华的消息 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setEssenceMsg(long botQQ, long messageId);

    /**
     * 删除精华消息。
     * <p>
     * 将一条精华消息从精华列表中移除。
     *
     * @param botQQ     目标 Bot 的 QQ 号
     * @param messageId 要移除的精华消息 ID
     * @param msgSeq    消息序号
     * @param msgRandom 消息随机数
     * @param groupQQ   目标群号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> deleteEssenceMsg(long botQQ, long messageId, String msgSeq, String msgRandom, long groupQQ);

    /**
     * 获取群忽略通知列表。
     * <p>
     * 获取群聊中被忽略的通知信息。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，包含忽略通知信息
     */
    CompletableFuture<ApiResponse<GroupIgnoredNotifiesData>> getGroupIgnoredNotifies(long botQQ);

    /**
     * 获取群忽略加群请求。
     * <p>
     * 获取已被忽略/已过期的加群请求列表。
     *
     * @param botQQ 目标 Bot 的 QQ 号
     * @return 异步响应，包含忽略的加群请求信息
     */
    CompletableFuture<ApiResponse<List<GroupIgnoreAddRequestData>>> getGroupIgnoreAddRequest(long botQQ);

    // ---- 群公告 ----

    /**
     * 发送群公告。
     * <p>
     * 在指定群聊中发布一条群公告。需要机器人是群管理员或群主。
     *
     * @param botQQ             目标 Bot 的 QQ 号
     * @param groupQQ           目标群号
     * @param content           公告内容
     * @param image             公告图片路径或 URL（可选）
     * @param pinned            是否置顶（0/1）
     * @param type              类型（默认为 1）
     * @param confirmRequired   是否需要确认（0/1）
     * @param isShowEditCard    是否显示修改群名片引导（0/1）
     * @param tipWindowType     弹窗类型（默认为 0）
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendGroupNotice(long botQQ, long groupQQ, String content, String image, int pinned, int type, int confirmRequired, int isShowEditCard, int tipWindowType);

    /**
     * 获取群公告列表。
     * <p>
     * 获取指定群聊的所有公告。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，data 为群公告列表
     */
    CompletableFuture<ApiResponse<List<GroupNoticeData>>> getGroupNotice(long botQQ, long groupQQ);

    /**
     * 删除群公告。
     * <p>
     * 删除指定群聊中的一条公告。需要机器人是群管理员或群主。
     *
     * @param botQQ    目标 Bot 的 QQ 号
     * @param groupQQ  目标群号
     * @param noticeId 公告 ID
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> deleteGroupNotice(long botQQ, long groupQQ, String noticeId);

    // ---- 群打卡/签到 ----

    /**
     * 群打卡（设置群签到）。
     * <p>
     * 在群聊中发起一个群打卡/签到活动。
     *
     * @param botQQ   目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> setGroupSign(long botQQ, long groupQQ);

    /**
     * 群打卡（发送群签到）。
     * <p>
     * 在群聊中发送签到消息。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，无业务数据
     */
    CompletableFuture<ApiResponse<VoidData>> sendGroupSign(long botQQ, long groupQQ);

    /**
     * 获取群组今日打卡列表。
     * <p>
     * 获取指定群聊今日的打卡/签到列表。
     *
     * @param botQQ  目标 Bot 的 QQ 号
     * @param groupQQ 目标群号
     * @return 异步响应，data 为打卡列表
     */
    CompletableFuture<ApiResponse<List<GroupSignedData>>> getGroupSignedList(long botQQ, long groupQQ);
}
