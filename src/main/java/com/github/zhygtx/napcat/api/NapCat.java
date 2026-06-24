package com.github.zhygtx.napcat.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.zhygtx.napcat.api.api.*;
import com.github.zhygtx.napcat.api.response.*;
import com.github.zhygtx.napcat.api.response.message.*;
import com.github.zhygtx.napcat.api.response.group.*;
import com.github.zhygtx.napcat.api.response.friend.*;
import com.github.zhygtx.napcat.api.response.system.*;
import com.github.zhygtx.napcat.api.response.file.*;
import com.github.zhygtx.napcat.api.response.extra.*;
import com.github.zhygtx.napcat.util.NapCatObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * NapCat Bot 主操作类。
 * <p>
 * 实现所有 API 接口，是 NapCat SDK 最核心的用户入口。每个 Bot 实例对应
 * 一个已连接的 NapCat QQ 号，通过指定 {@code botQQ} 来区分目标 Bot。
 * <p>
 * 使用方式：
 * <pre>{@code
 * @Autowired private NapCat napCatBot;
 * // 同步等待
 * SendMsgData result = napCatBot.sendGroupMsg(123456L, "789", "hello", false)
 *         .get(30, TimeUnit.SECONDS).getData();
 * // 异步回调
 * napCatBot.getGroupList(123456L).thenAccept(r -> r.getData().forEach(System.out::println));
 * }</pre>
 */
@Service
public class NapCat implements ApiMessage, ApiGroup, ApiFriend, ApiSystem, ApiFile, ApiExtra {

    private final NapCatApiClient engine;
    private final NapCatObjectMapper mapper;

    public NapCat(NapCatApiClient engine, NapCatObjectMapper mapper) {
        this.engine = engine;
        this.mapper = mapper;
    }

    // ===== 快捷方法 =====

    private <T> CompletableFuture<ApiResponse<T>> req(long botQQ, String action, ObjectNode params, Class<T> dataClass) {
        return engine.sendRequest(botQQ, action, params, engine.type(dataClass));
    }

    private <T> CompletableFuture<ApiResponse<List<T>>> reqList(long botQQ, String action, ObjectNode params, Class<T> elemClass) {
        return engine.sendRequest(botQQ, action, params, engine.listType(elemClass));
    }

    // ================================================================
    // ApiMessage 实现
    // ================================================================

    @Override
    public CompletableFuture<ApiResponse<SendMsgData>> sendGroupMsg(
            long botQQ, long groupQQ, String message, boolean autoEscape) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("message", message);
        if (autoEscape) p.put("auto_escape", true);
        return req(botQQ, "send_group_msg", p, SendMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<SendMsgData>> sendPrivateMsg(
            long botQQ, String userId, String message, boolean autoEscape) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("message", message);
        if (autoEscape) p.put("auto_escape", true);
        return req(botQQ, "send_private_msg", p, SendMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<SendMsgData>> sendMsg(
            long botQQ, String messageType, String userId, long groupQQ, String message, boolean autoEscape) {
        ObjectNode p = mapper.createObjectNode();
        if (messageType != null) p.put("message_type", messageType);
        if (userId != null) p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        p.put("message", message);
        if (autoEscape) p.put("auto_escape", true);
        return req(botQQ, "send_msg", p, SendMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteMsg(long botQQ, long messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        return req(botQQ, "delete_msg", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GetMsgData>> getMsg(long botQQ, long messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        return req(botQQ, "get_msg", p, GetMsgData.class);
    }

    // ================================================================
    // ApiGroup 实现
    // ================================================================

    @Override
    public CompletableFuture<ApiResponse<List<GroupInfoData>>> getGroupList(long botQQ) {
        return reqList(botQQ, "get_group_list", mapper.createObjectNode(), GroupInfoData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupInfoData>> getGroupInfo(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "get_group_info", p, GroupInfoData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupDetailInfoData>> getGroupDetailInfo(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "get_group_detail_info", p, GroupDetailInfoData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupInfoExData>> getGroupInfoEx(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "get_group_info_ex", p, GroupInfoExData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupMemberData>> getGroupMemberInfo(
            long botQQ, long groupQQ, String userId, boolean noCache) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("user_id", userId);
        if (noCache) p.put("no_cache", true);
        return req(botQQ, "get_group_member_info", p, GroupMemberData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<GroupMemberData>>> getGroupMemberList(
            long botQQ, long groupQQ, boolean noCache) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        if (noCache) p.put("no_cache", true);
        return reqList(botQQ, "get_group_member_list", p, GroupMemberData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupBan(long botQQ, long groupQQ, String userId, long duration) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("user_id", userId);
        p.put("duration", duration);
        return req(botQQ, "set_group_ban", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupWholeBan(long botQQ, long groupQQ, boolean enable) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("enable", enable);
        return req(botQQ, "set_group_whole_ban", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupKick(long botQQ, long groupQQ, String userId, boolean rejectAddRequest) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("user_id", userId);
        if (rejectAddRequest) p.put("reject_add_request", true);
        return req(botQQ, "set_group_kick", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupKickMembers(long botQQ, long groupQQ, List<String> userIds, boolean rejectAddRequest) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        ArrayNode arr = p.putArray("user_ids");
        userIds.forEach(arr::add);
        if (rejectAddRequest) p.put("reject_add_request", true);
        return req(botQQ, "set_group_kick_members", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupAdmin(long botQQ, long groupQQ, String userId, boolean enable) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("user_id", userId);
        p.put("enable", enable);
        return req(botQQ, "set_group_admin", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupCard(long botQQ, long groupQQ, String userId, String card) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("user_id", userId);
        if (card != null) p.put("card", card);
        return req(botQQ, "set_group_card", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupName(long botQQ, long groupQQ, String groupName) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("group_name", groupName);
        return req(botQQ, "set_group_name", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupRemark(long botQQ, long groupQQ, String remark) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("remark", remark);
        return req(botQQ, "set_group_remark", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupPortrait(long botQQ, long groupQQ, String file, int cache) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file", file);
        p.put("cache", cache);
        return req(botQQ, "set_group_portrait", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<GroupShutMemberData>>> getGroupShutList(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return reqList(botQQ, "get_group_shut_list", p, GroupShutMemberData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupAddOption(long botQQ, long groupQQ, String addType, String groupQuestion, String groupAnswer) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("add_type", addType);
        if (groupQuestion != null) p.put("group_question", groupQuestion);
        if (groupAnswer != null) p.put("group_answer", groupAnswer);
        return req(botQQ, "set_group_add_option", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupRobotAddOption(long botQQ, long groupQQ, String robotMemberSwitch, String robotMemberExamine) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("robot_member_switch", robotMemberSwitch);
        if (robotMemberExamine != null) p.put("robot_member_examine", robotMemberExamine);
        return req(botQQ, "set_group_robot_add_option", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupSearch(long botQQ, long groupQQ, String noCodeFingerOpen, String noFingerOpen) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("no_code_finger_open", noCodeFingerOpen);
        if (noFingerOpen != null) p.put("no_finger_open", noFingerOpen);
        return req(botQQ, "set_group_search", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupTodo(long botQQ, long groupQQ, long messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("message_id", messageId);
        return req(botQQ, "set_group_todo", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupSpecialTitle(long botQQ, long groupQQ, String userId, String specialTitle, long duration) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("user_id", userId);
        p.put("special_title", specialTitle);
        p.put("duration", duration);
        return req(botQQ, "set_group_special_title", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupLeave(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "set_group_leave", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupAddRequest(long botQQ, String flag, boolean approve, String reason) {
        ObjectNode p = mapper.createObjectNode();
        p.put("flag", flag);
        p.put("approve", approve);
        if (reason != null && !reason.isEmpty()) p.put("reason", reason);
        return req(botQQ, "set_group_add_request", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupHonorInfoData>> getGroupHonorInfo(long botQQ, long groupQQ, String type) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        if (type != null) p.put("type", type);
        return req(botQQ, "get_group_honor_info", p, GroupHonorInfoData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupSystemMsgData>> getGroupSystemMsg(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "get_group_system_msg", p, GroupSystemMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupAtAllRemainData>> getGroupAtAllRemain(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "get_group_at_all_remain", p, GroupAtAllRemainData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<EssenceMsgData>>> getEssenceMsgList(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return reqList(botQQ, "get_essence_msg_list", p, EssenceMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setEssenceMsg(long botQQ, long messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        return req(botQQ, "set_essence_msg", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteEssenceMsg(long botQQ, long messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        return req(botQQ, "delete_essence_msg", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupIgnoredNotifiesData>> getGroupIgnoredNotifies(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "get_group_ignored_notifies", p, GroupIgnoredNotifiesData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<GroupIgnoreAddRequestData>>> getGroupIgnoreAddRequest(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return reqList(botQQ, "get_group_ignore_add_request", p, GroupIgnoreAddRequestData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendGroupNotice(long botQQ, long groupQQ, String title, String content, boolean pinned) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("title", title);
        p.put("content", content);
        if (pinned) p.put("pinned", true);
        return req(botQQ, "_send_group_notice", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<GroupNoticeData>>> getGroupNotice(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return reqList(botQQ, "_get_group_notice", p, GroupNoticeData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteGroupNotice(long botQQ, long groupQQ, String noticeId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("notice_id", noticeId);
        return req(botQQ, "_del_group_notice", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupSign(long botQQ, long groupQQ, String title, String content) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        if (title != null) p.put("title", title);
        if (content != null) p.put("content", content);
        return req(botQQ, "set_group_sign", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendGroupSign(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "send_group_sign", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> cancelGroupTodo(long botQQ, long groupQQ, long messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("message_id", messageId);
        return req(botQQ, "cancel_group_todo", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> completeGroupTodo(long botQQ, long groupQQ, long messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("message_id", messageId);
        return req(botQQ, "complete_group_todo", p, VoidData.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CompletableFuture<ApiResponse<List<?>>> getGroupSignedList(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return (CompletableFuture<ApiResponse<List<?>>>) (CompletableFuture<?>) req(botQQ, "get_group_signed_list", p, Object.class);
    }

    // ================================================================
    // ApiFriend 实现
    // ================================================================

    @Override
    public CompletableFuture<ApiResponse<List<FriendListData>>> getFriendList(long botQQ) {
        return reqList(botQQ, "get_friend_list", mapper.createObjectNode(), FriendListData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<FriendCategoryData>>> getFriendsWithCategory(long botQQ) {
        return reqList(botQQ, "get_friends_with_category", mapper.createObjectNode(), FriendCategoryData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<FriendListData>>> getUnidirectionalFriendList(long botQQ) {
        return reqList(botQQ, "get_unidirectional_friend_list", mapper.createObjectNode(), FriendListData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<StrangerInfoData>> getStrangerInfo(long botQQ, String userId, boolean noCache) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        if (noCache) p.put("no_cache", true);
        return req(botQQ, "get_stranger_info", p, StrangerInfoData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setFriendAddRequest(long botQQ, String flag, boolean approve, String remark) {
        ObjectNode p = mapper.createObjectNode();
        p.put("flag", flag);
        p.put("approve", approve);
        if (remark != null && !remark.isEmpty()) p.put("remark", remark);
        return req(botQQ, "set_friend_add_request", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteFriend(long botQQ, String userId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        return req(botQQ, "delete_friend", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setFriendRemark(long botQQ, String userId, String remark) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("remark", remark);
        return req(botQQ, "set_friend_remark", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<DoubtFriendAddRequestData>>> getDoubtFriendsAddRequest(long botQQ) {
        return reqList(botQQ, "get_doubt_friends_add_request", mapper.createObjectNode(), DoubtFriendAddRequestData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setDoubtFriendsAddRequest(long botQQ, String uin, boolean operate, String message) {
        ObjectNode p = mapper.createObjectNode();
        p.put("uin", uin);
        p.put("operate", operate ? 1 : 0);
        if (message != null) p.put("message", message);
        return req(botQQ, "set_doubt_friends_add_request", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<ProfileLikeData>> getProfileLike(String userId, int start, int count, long botQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("start", start);
        p.put("count", count);
        return req(botQQ, "get_profile_like", p, ProfileLikeData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setQqAvatar(long botQQ, String file) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file", file);
        return req(botQQ, "set_qq_avatar", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setQqProfile(long botQQ, String nickname, String company, String email, String college, String personalNote) {
        ObjectNode p = mapper.createObjectNode();
        if (nickname != null) p.put("nickname", nickname);
        if (company != null) p.put("company", company);
        if (email != null) p.put("email", email);
        if (college != null) p.put("college", college);
        if (personalNote != null) p.put("personal_note", personalNote);
        return req(botQQ, "set_qq_profile", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setSelfLongnick(long botQQ, String longNick) {
        ObjectNode p = mapper.createObjectNode();
        p.put("long_nick", longNick);
        return req(botQQ, "set_self_longnick", p, VoidData.class);
    }

    // ================================================================
    // ApiSystem 实现
    // ================================================================

    @Override
    public CompletableFuture<ApiResponse<LoginInfoData>> getLoginInfo(long botQQ) {
        return req(botQQ, "get_login_info", mapper.createObjectNode(), LoginInfoData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VersionInfoData>> getVersionInfo(long botQQ) {
        return req(botQQ, "get_version_info", mapper.createObjectNode(), VersionInfoData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<StatusData>> getStatus(long botQQ) {
        return req(botQQ, "get_status", mapper.createObjectNode(), StatusData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<RobotUinRangeData>>> getRobotUinRange(long botQQ) {
        return reqList(botQQ, "get_robot_uin_range", mapper.createObjectNode(), RobotUinRangeData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setRestart(long botQQ) {
        return req(botQQ, "set_restart", mapper.createObjectNode(), VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> botExit(long botQQ) {
        return req(botQQ, "bot_exit", mapper.createObjectNode(), VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> cleanCache(long botQQ) {
        return req(botQQ, "clean_cache", mapper.createObjectNode(), VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<UrlSafetyData>> checkUrlSafely(long botQQ, String url) {
        ObjectNode p = mapper.createObjectNode();
        p.put("url", url);
        return req(botQQ, "check_url_safely", p, UrlSafetyData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<CookiesData>> getCookies(long botQQ, String domain) {
        ObjectNode p = mapper.createObjectNode();
        if (domain != null) p.put("domain", domain);
        return req(botQQ, "get_cookies", p, CookiesData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<CsrfTokenData>> getCsrfToken(long botQQ) {
        return req(botQQ, "get_csrf_token", mapper.createObjectNode(), CsrfTokenData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<CredentialsData>> getCredentials(long botQQ) {
        return req(botQQ, "get_credentials", mapper.createObjectNode(), CredentialsData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<RKeyData>> getRkey(long botQQ, String domain, String type) {
        ObjectNode p = mapper.createObjectNode();
        if (domain != null) p.put("domain", domain);
        if (type != null) p.put("type", type);
        return req(botQQ, "get_rkey", p, RKeyData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<RKeyServerData>> getRkeyServer(long botQQ, String domain) {
        ObjectNode p = mapper.createObjectNode();
        if (domain != null) p.put("domain", domain);
        return req(botQQ, "get_rkey_server", p, RKeyServerData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<ClientKeyData>> getClientkey(long botQQ, String domain) {
        ObjectNode p = mapper.createObjectNode();
        if (domain != null) p.put("domain", domain);
        return req(botQQ, "get_clientkey", p, ClientKeyData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<RKeyData>>> ncGetRkey(long botQQ) {
        return reqList(botQQ, "nc_get_rkey", mapper.createObjectNode(), RKeyData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<UserStatusData>> ncGetUserStatus(long botQQ, String userId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        return req(botQQ, "nc_get_user_status", p, UserStatusData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<PacketStatusData>> ncGetPacketStatus(long botQQ) {
        return req(botQQ, "nc_get_packet_status", mapper.createObjectNode(), PacketStatusData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<CanSendData>> canSendRecord(long botQQ) {
        return req(botQQ, "can_send_record", mapper.createObjectNode(), CanSendData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<CanSendData>> canSendImage(long botQQ) {
        return req(botQQ, "can_send_image", mapper.createObjectNode(), CanSendData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setOnlineStatus(long botQQ, long status, long extStatus, long batteryStatus) {
        ObjectNode p = mapper.createObjectNode();
        p.put("status", status);
        p.put("ext_status", extStatus);
        p.put("battery_status", batteryStatus);
        return req(botQQ, "set_online_status", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setDiyOnlineStatus(long botQQ, String faceId, String faceType, String wording) {
        ObjectNode p = mapper.createObjectNode();
        p.put("face_id", faceId);
        p.put("face_type", faceType);
        p.put("wording", wording);
        return req(botQQ, "set_diy_online_status", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<OnlineClientData>>> getOnlineClients(long botQQ) {
        return reqList(botQQ, "get_online_clients", mapper.createObjectNode(), OnlineClientData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<ShareLinkData>> getShareLink(long botQQ, String filesetId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("fileset_id", filesetId);
        return req(botQQ, "get_share_link", p, ShareLinkData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendPacket(long botQQ, String cmd, String body) {
        ObjectNode p = mapper.createObjectNode();
        p.put("cmd", cmd);
        p.put("body", body);
        return req(botQQ, "send_packet", p, VoidData.class);
    }

    // ================================================================
    // ApiFile 实现
    // ================================================================

    @Override
    public CompletableFuture<ApiResponse<FileData>> getImage(long botQQ, String file) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file", file);
        return req(botQQ, "get_image", p, FileData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FileData>> getRecord(long botQQ, String file) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file", file);
        return req(botQQ, "get_record", p, FileData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FileData>> getFile(long botQQ, String file) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file", file);
        return req(botQQ, "get_file", p, FileData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FileDownloadData>> downloadFile(long botQQ, int threadCount, String headers, String base64, String name, String url) {
        ObjectNode p = mapper.createObjectNode();
        p.put("thread_count", threadCount);
        if (headers != null) p.put("headers", headers);
        if (base64 != null) p.put("base64", base64);
        if (name != null) p.put("name", name);
        p.put("url", url);
        return req(botQQ, "download_file", p, FileDownloadData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> uploadGroupFile(long botQQ, long groupQQ, String file, String name, String folder) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file", file);
        if (name != null) p.put("name", name);
        if (folder != null) p.put("folder", folder);
        return req(botQQ, "upload_group_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> uploadPrivateFile(long botQQ, String userId, String file, String name) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("file", file);
        if (name != null) p.put("name", name);
        return req(botQQ, "upload_private_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FileUrlData>> getGroupFileUrl(long botQQ, long groupQQ, String fileId, long busid) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file_id", fileId);
        p.put("busid", busid);
        return req(botQQ, "get_group_file_url", p, FileUrlData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FileUrlData>> getPrivateFileUrl(long botQQ, String userId, String fileId, long busid) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("file_id", fileId);
        p.put("busid", busid);
        return req(botQQ, "get_private_file_url", p, FileUrlData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteGroupFile(long botQQ, long groupQQ, String fileId, long busid, String folderId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file_id", fileId);
        p.put("busid", busid);
        if (folderId != null) p.put("folder_id", folderId);
        return req(botQQ, "delete_group_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> createGroupFileFolder(long botQQ, long groupQQ, String name, String folderId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("name", name);
        if (folderId != null) p.put("folder_id", folderId);
        return req(botQQ, "create_group_file_folder", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteGroupFolder(long botQQ, long groupQQ, String folderId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("folder_id", folderId);
        return req(botQQ, "delete_group_folder", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupFileSystemInfoData>> getGroupFileSystemInfo(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "get_group_file_system_info", p, GroupFileSystemInfoData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupRootFilesData>> getGroupRootFiles(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "get_group_root_files", p, GroupRootFilesData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupRootFilesData>> getGroupFilesByFolder(long botQQ, long groupQQ, String folderId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("folder_id", folderId);
        return req(botQQ, "get_group_files_by_folder", p, GroupRootFilesData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> moveGroupFile(long botQQ, long groupQQ, String fileId, long busid, String targetFolderId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file_id", fileId);
        p.put("busid", busid);
        p.put("target_folder_id", targetFolderId);
        return req(botQQ, "move_group_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> renameGroupFile(long botQQ, long groupQQ, String fileId, long busid, String newName, String folderId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file_id", fileId);
        p.put("busid", busid);
        p.put("new_name", newName);
        if (folderId != null) p.put("folder_id", folderId);
        return req(botQQ, "rename_group_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> transGroupFile(long botQQ, long groupQQ, String fileId, long busid, long targetGroupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file_id", fileId);
        p.put("busid", busid);
        p.put("target_group_id", Long.toString(targetGroupQQ));
        return req(botQQ, "trans_group_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FilesetInfoData>> getFilesetInfo(long botQQ, String filesetId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("fileset_id", filesetId);
        return req(botQQ, "get_fileset_info", p, FilesetInfoData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FilesetIdData>> getFilesetId(long botQQ, String shareCode) {
        ObjectNode p = mapper.createObjectNode();
        p.put("share_code", shareCode);
        return req(botQQ, "get_fileset_id", p, FilesetIdData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> downloadFileset(long botQQ, String filesetId, String destPath) {
        ObjectNode p = mapper.createObjectNode();
        p.put("fileset_id", filesetId);
        if (destPath != null) p.put("dest_path", destPath);
        return req(botQQ, "download_fileset", p, VoidData.class);
    }

    // ================================================================
    // ApiExtra 实现
    // ================================================================

    @Override
    public CompletableFuture<ApiResponse<SendMsgData>> sendForwardMsg(long botQQ, JsonNode messages, String messageType, String userId, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.set("messages", messages);
        if (messageType != null) p.put("message_type", messageType);
        if (userId != null) p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "send_forward_msg", p, SendMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<SendMsgData>> sendGroupForwardMsg(long botQQ, long groupQQ, JsonNode messages) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.set("messages", messages);
        return req(botQQ, "send_group_forward_msg", p, SendMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<SendMsgData>> sendPrivateForwardMsg(long botQQ, String userId, JsonNode messages) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.set("messages", messages);
        return req(botQQ, "send_private_forward_msg", p, SendMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<ForwardMsgData>> getForwardMsg(long botQQ, String messageId, String id) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        if (id != null) p.put("id", id);
        return req(botQQ, "get_forward_msg", p, ForwardMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> forwardFriendSingleMsg(long botQQ, String userId, long messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("message_id", messageId);
        return req(botQQ, "forward_friend_single_msg", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> forwardGroupSingleMsg(long botQQ, long groupQQ, long messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("message_id", messageId);
        return req(botQQ, "forward_group_single_msg", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<HistoryMsgData>> getGroupMsgHistory(long botQQ, long groupQQ, long messageSeq, int count, boolean reverseOrder) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("message_seq", messageSeq);
        p.put("count", count);
        if (reverseOrder) p.put("reverse_order", true);
        return req(botQQ, "get_group_msg_history", p, HistoryMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<HistoryMsgData>> getFriendMsgHistory(long botQQ, String userId, long messageSeq, int count, boolean reverseOrder) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("message_seq", messageSeq);
        p.put("count", count);
        if (reverseOrder) p.put("reverse_order", true);
        return req(botQQ, "get_friend_msg_history", p, HistoryMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> markMsgAsRead(long botQQ, String messageType, long groupQQ, String userId, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        if (messageType != null) p.put("message_type", messageType);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        if (userId != null) p.put("user_id", userId);
        if (messageId != null) p.put("message_id", messageId);
        return req(botQQ, "mark_msg_as_read", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> markGroupMsgAsRead(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "mark_group_msg_as_read", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> markPrivateMsgAsRead(long botQQ, String userId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        return req(botQQ, "mark_private_msg_as_read", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> markAllAsRead(long botQQ) {
        return req(botQQ, "_mark_all_as_read", mapper.createObjectNode(), VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> groupPoke(long botQQ, long groupQQ, String userId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("user_id", userId);
        return req(botQQ, "group_poke", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> friendPoke(long botQQ, String userId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        return req(botQQ, "friend_poke", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendPoke(long botQQ, String userId, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "send_poke", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<EmojiLikeDetailData>> fetchEmojiLike(long botQQ, String messageId, String emojiId, String emojiType, long groupQQ, String userId, String count, String cookie) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        p.put("emoji_id", emojiId);
        p.put("emoji_type", emojiType);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        if (userId != null) p.put("user_id", userId);
        if (count != null) p.put("count", count);
        if (cookie != null) p.put("cookie", cookie);
        return req(botQQ, "fetch_emoji_like", p, EmojiLikeDetailData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<EmojiLikesData>> getEmojiLikes(long botQQ, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        return req(botQQ, "get_emoji_likes", p, EmojiLikesData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setMsgEmojiLike(long botQQ, long messageId, String emojiId, boolean set) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        p.put("emoji_id", emojiId);
        p.put("set", set);
        return req(botQQ, "set_msg_emoji_like", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<CustomFaceData>>> fetchCustomFace(long botQQ, int count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("count", count);
        return reqList(botQQ, "fetch_custom_face", p, CustomFaceData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendLike(long botQQ, String userId, int times) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("times", times);
        return req(botQQ, "send_like", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<QunAlbumListData>> getQunAlbumList(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "get_qun_album_list", p, QunAlbumListData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupAlbumMediaListData>> getGroupAlbumMediaList(long botQQ, long groupQQ, String albumId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("album_id", albumId);
        return req(botQQ, "get_group_album_media_list", p, GroupAlbumMediaListData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteGroupAlbumMedia(long botQQ, long groupQQ, String albumId, String lloc) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("album_id", albumId);
        p.put("lloc", lloc);
        return req(botQQ, "del_group_album_media", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupAlbumMediaLike(long botQQ, long groupQQ, String albumId, String lloc, String id, boolean set) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("album_id", albumId);
        p.put("lloc", lloc);
        p.put("id", id);
        p.put("set", set);
        return req(botQQ, "set_group_album_media_like", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> doGroupAlbumComment(long botQQ, long groupQQ, String albumId, String lloc, String content, String replyUid) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("album_id", albumId);
        p.put("lloc", lloc);
        p.put("content", content);
        if (replyUid != null) p.put("reply_uid", replyUid);
        return req(botQQ, "do_group_album_comment", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> uploadImageToQunAlbum(long botQQ, long groupQQ, String file) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file", file);
        return req(botQQ, "upload_image_to_qun_album", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> uploadFileStream(long botQQ, String streamId, String chunkData, int chunkIndex, int totalChunks, long fileSize, String filename) {
        ObjectNode p = mapper.createObjectNode();
        p.put("stream_id", streamId);
        p.put("chunk_data", chunkData);
        p.put("chunk_index", chunkIndex);
        p.put("total_chunks", totalChunks);
        p.put("file_size", fileSize);
        if (filename != null) p.put("filename", filename);
        return req(botQQ, "upload_file_stream", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> downloadFileStream(long botQQ, String file, String fileId, int chunkSize) {
        ObjectNode p = mapper.createObjectNode();
        if (file != null) p.put("file", file);
        if (fileId != null) p.put("file_id", fileId);
        if (chunkSize > 0) p.put("chunk_size", chunkSize);
        return req(botQQ, "download_file_stream", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> downloadFileRecordStream(long botQQ, String file, String fileId, int chunkSize, String outFormat) {
        ObjectNode p = mapper.createObjectNode();
        if (file != null) p.put("file", file);
        if (fileId != null) p.put("file_id", fileId);
        if (chunkSize > 0) p.put("chunk_size", chunkSize);
        if (outFormat != null) p.put("out_format", outFormat);
        return req(botQQ, "download_file_record_stream", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> downloadFileImageStream(long botQQ, String file, String fileId, int chunkSize) {
        ObjectNode p = mapper.createObjectNode();
        if (file != null) p.put("file", file);
        if (fileId != null) p.put("file_id", fileId);
        if (chunkSize > 0) p.put("chunk_size", chunkSize);
        return req(botQQ, "download_file_image_stream", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> cleanStreamTempFile(long botQQ) {
        return req(botQQ, "clean_stream_temp_file", mapper.createObjectNode(), VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> testDownloadStream(long botQQ, String url, boolean triggerError) {
        ObjectNode p = mapper.createObjectNode();
        p.put("url", url);
        if (triggerError) p.put("error", true);
        return req(botQQ, "test_download_stream", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<OcrResultData>> ocrImage(long botQQ, String image) {
        ObjectNode p = mapper.createObjectNode();
        p.put("image", image);
        return req(botQQ, "ocr_image", p, OcrResultData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<TranslateResultData>> translateEn2zh(long botQQ, List<String> words) {
        ObjectNode p = mapper.createObjectNode();
        ArrayNode arr = p.putArray("words");
        words.forEach(arr::add);
        return req(botQQ, "translate_en2zh", p, TranslateResultData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<CreateCollectionData>> createCollection(long botQQ, String rawData, int type) {
        ObjectNode p = mapper.createObjectNode();
        p.put("raw_data", rawData);
        p.put("type", type);
        return req(botQQ, "create_collection", p, CreateCollectionData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<CollectionListData>> getCollectionList(long botQQ, int category, String startPos, int count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("category", category);
        p.put("start_pos", startPos);
        p.put("count", count);
        return req(botQQ, "get_collection_list", p, CollectionListData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<GuildListData>>> getGuildList(long botQQ) {
        return reqList(botQQ, "get_guild_list", mapper.createObjectNode(), GuildListData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GuildServiceProfileData>> getGuildServiceProfile(long botQQ, String guildId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("guild_id", guildId);
        return req(botQQ, "get_guild_service_profile", p, GuildServiceProfileData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<AiRecordData>> getAiRecord(long botQQ, String character, String text, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("character", character);
        p.put("text", text);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "get_ai_record", p, AiRecordData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendGroupAiRecord(long botQQ, long groupQQ, String character, String text) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("character", character);
        p.put("text", text);
        return req(botQQ, "send_group_ai_record", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<AiCharactersData>>> getAiCharacters(long botQQ, long groupQQ, String chatType) {
        ObjectNode p = mapper.createObjectNode();
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        if (chatType != null) p.put("chat_type", chatType);
        return reqList(botQQ, "get_ai_characters", p, AiCharactersData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> createFlashTask(long botQQ, String files, String name, String thumbPath) {
        ObjectNode p = mapper.createObjectNode();
        p.put("files", files);
        if (name != null) p.put("name", name);
        if (thumbPath != null) p.put("thumb_path", thumbPath);
        return req(botQQ, "create_flash_task", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> getFlashFileList(long botQQ, String filesetId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("fileset_id", filesetId);
        return req(botQQ, "get_flash_file_list", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> getFlashFileUrl(long botQQ, String taskId, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("task_id", taskId);
        p.put("file_id", fileId);
        return req(botQQ, "get_flash_file_url", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendFlashMsg(long botQQ, String userId, long groupQQ, String filesetId) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        p.put("fileset_id", filesetId);
        return req(botQQ, "send_flash_msg", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<OnlineFileMsgData>> getOnlineFileMsg(long botQQ, String messageId, String selfId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        p.put("self_id", selfId);
        return req(botQQ, "get_online_file_msg", p, OnlineFileMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendOnlineFile(long botQQ, String userId, long groupQQ, String filePath, String fileName, long fileSize) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        p.put("file_path", filePath);
        p.put("file_name", fileName);
        p.put("file_size", fileSize);
        return req(botQQ, "send_online_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendOnlineFolder(long botQQ, String userId, long groupQQ, String folderId) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        p.put("folder_id", folderId);
        return req(botQQ, "send_online_folder", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> receiveOnlineFile(long botQQ, String taskId, String destPath) {
        ObjectNode p = mapper.createObjectNode();
        p.put("task_id", taskId);
        p.put("dest_path", destPath);
        return req(botQQ, "receive_online_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> refuseOnlineFile(long botQQ, String taskId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("task_id", taskId);
        return req(botQQ, "refuse_online_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> cancelOnlineFile(long botQQ, String taskId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("task_id", taskId);
        return req(botQQ, "cancel_online_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setInputStatus(long botQQ, String userId, long groupQQ, int eventType) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        p.put("event_type", eventType);
        return req(botQQ, "set_input_status", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<RecentContactData>>> getRecentContact(long botQQ, int count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("count", count);
        return reqList(botQQ, "get_recent_contact", p, RecentContactData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<ModelShowData>>> getModelShow(long botQQ, String model) {
        ObjectNode p = mapper.createObjectNode();
        p.put("model", model);
        return reqList(botQQ, "_get_model_show", p, ModelShowData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setModelShow(long botQQ, String model, String modelShow) {
        ObjectNode p = mapper.createObjectNode();
        p.put("model", model);
        p.put("model_show", modelShow);
        return req(botQQ, "_set_model_show", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<MiniAppArkData>> getMiniAppArk(long botQQ, String appId, String appPath, String appVersion, String appQq, String appType) {
        ObjectNode p = mapper.createObjectNode();
        p.put("app_id", appId);
        p.put("app_path", appPath);
        p.put("app_version", appVersion);
        if (appQq != null) p.put("app_qq", appQq);
        if (appType != null) p.put("app_type", appType);
        return req(botQQ, "get_mini_app_ark", p, MiniAppArkData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> ArkShareGroup(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "ArkShareGroup", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> ArkSharePeer(long botQQ, String userId, String phoneNumber) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", userId);
        if (phoneNumber != null) p.put("phone_number", phoneNumber);
        return req(botQQ, "ArkSharePeer", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendGroupArkShare(long botQQ, long groupQQ, String text, String title, String desc, String jumpUrl, String preview, String tag, String tagIcon) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        if (text != null) p.put("text", text);
        if (title != null) p.put("title", title);
        if (desc != null) p.put("desc", desc);
        if (jumpUrl != null) p.put("jumpUrl", jumpUrl);
        if (preview != null) p.put("preview", preview);
        if (tag != null) p.put("tag", tag);
        if (tagIcon != null) p.put("tagIcon", tagIcon);
        return req(botQQ, "send_group_ark_share", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendArkShare(long botQQ, String userId, String text, String title, String desc, String jumpUrl, String preview, String tag, String tagIcon) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", userId);
        if (text != null) p.put("text", text);
        if (title != null) p.put("title", title);
        if (desc != null) p.put("desc", desc);
        if (jumpUrl != null) p.put("jumpUrl", jumpUrl);
        if (preview != null) p.put("preview", preview);
        if (tag != null) p.put("tag", tag);
        if (tagIcon != null) p.put("tagIcon", tagIcon);
        return req(botQQ, "send_ark_share", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> clickInlineKeyboardButton(long botQQ, String messageId, String buttonId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        p.put("button_id", buttonId);
        return req(botQQ, "click_inline_keyboard_button", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> handleQuickOperation(long botQQ, JsonNode context, JsonNode operation) {
        ObjectNode p = mapper.createObjectNode();
        p.set("context", context);
        p.set("operation", operation);
        return req(botQQ, ".handle_quick_operation", p, VoidData.class);
    }

    // ==================== 新增缺失端点 ====================

    @Override
    public CompletableFuture<ApiResponse<VoidData>> cancelGroupAlbumMediaLike(long botQQ, long groupQQ, String albumId, String batchId, String lloc) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("album_id", albumId);
        p.put("batch_id", batchId);
        p.put("lloc", lloc);
        return req(botQQ, "cancel_group_album_media_like", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<PttTextData>> fetchPttText(long botQQ, long messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        return req(botQQ, "fetch_ptt_text", p, PttTextData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> addCustomFace(long botQQ, String file, String emojiId, String packageId, String fileName, long fileSize, String md5, boolean isMarkFace, boolean isOrigin) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file", file);
        if (emojiId != null) p.put("emoji_id", emojiId);
        if (packageId != null) p.put("package_id", packageId);
        if (fileName != null) p.put("file_name", fileName);
        if (fileSize > 0) p.put("file_size", fileSize);
        if (md5 != null) p.put("md5", md5);
        if (isMarkFace) p.put("is_mark_face", true);
        if (isOrigin) p.put("is_origin", true);
        return req(botQQ, "add_custom_face", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteCustomFace(long botQQ, String resId, String ids, String md5) {
        ObjectNode p = mapper.createObjectNode();
        if (resId != null) p.put("res_id", resId);
        if (ids != null) p.put("ids", ids);
        if (md5 != null) p.put("md5", md5);
        return req(botQQ, "delete_custom_face", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<CustomFaceDetailData>> fetchCustomFaceDetail(long botQQ, int count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("count", count);
        return req(botQQ, "fetch_custom_face_detail", p, CustomFaceDetailData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setCustomFaceDesc(long botQQ, String emojiId, String resId, String md5, String desc) {
        ObjectNode p = mapper.createObjectNode();
        p.put("emoji_id", emojiId);
        p.put("res_id", resId);
        p.put("md5", md5);
        p.put("desc", desc);
        return req(botQQ, "set_custom_face_desc", p, VoidData.class);
    }
}
