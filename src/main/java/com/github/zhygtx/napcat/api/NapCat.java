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
            long botQQ, long groupQQ, String message, boolean autoEscape,
            String source, JsonNode news, String summary, String prompt, Long timeout) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("message", message);
        if (autoEscape) p.put("auto_escape", true);
        if (source != null) p.put("source", source);
        if (news != null) p.set("news", news);
        if (summary != null) p.put("summary", summary);
        if (prompt != null) p.put("prompt", prompt);
        if (timeout != null) p.put("timeout", timeout);
        return req(botQQ, "send_group_msg", p, SendMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<SendMsgData>> sendPrivateMsg(
            long botQQ, String userId, String message, boolean autoEscape,
            String source, JsonNode news, String summary, String prompt, Long timeout) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("message", message);
        if (autoEscape) p.put("auto_escape", true);
        if (source != null) p.put("source", source);
        if (news != null) p.set("news", news);
        if (summary != null) p.put("summary", summary);
        if (prompt != null) p.put("prompt", prompt);
        if (timeout != null) p.put("timeout", timeout);
        return req(botQQ, "send_private_msg", p, SendMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<SendMsgData>> sendMsg(
            long botQQ, String messageType, String userId, long groupQQ, String message, boolean autoEscape,
            String source, JsonNode news, String summary, String prompt, Long timeout) {
        ObjectNode p = mapper.createObjectNode();
        if (messageType != null) p.put("message_type", messageType);
        if (userId != null) p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        p.put("message", message);
        if (autoEscape) p.put("auto_escape", true);
        if (source != null) p.put("source", source);
        if (news != null) p.set("news", news);
        if (summary != null) p.put("summary", summary);
        if (prompt != null) p.put("prompt", prompt);
        if (timeout != null) p.put("timeout", timeout);
        return req(botQQ, "send_msg", p, SendMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteMsg(long botQQ, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        return req(botQQ, "delete_msg", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GetMsgData>> getMsg(long botQQ, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        return req(botQQ, "get_msg", p, GetMsgData.class);
    }

    // ================================================================
    // ApiGroup 实现
    // ================================================================

    @Override
    public CompletableFuture<ApiResponse<List<GroupInfoData>>> getGroupList(long botQQ, boolean noCache) {
        ObjectNode p = mapper.createObjectNode();
        if (noCache) p.put("no_cache", true);
        return reqList(botQQ, "get_group_list", p, GroupInfoData.class);
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
        ArrayNode arr = p.putArray("user_id");
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
    public CompletableFuture<ApiResponse<GroupPortraitResult>> setGroupPortrait(long botQQ, long groupQQ, String file, int cache) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file", file);
        p.put("cache", cache);
        return req(botQQ, "set_group_portrait", p, GroupPortraitResult.class);
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
    public CompletableFuture<ApiResponse<VoidData>> setGroupTodo(long botQQ, long groupQQ, String messageId, String messageSeq) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("message_id", messageId);
        if (messageSeq != null) p.put("message_seq", messageSeq);
        return req(botQQ, "set_group_todo", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupSpecialTitle(long botQQ, long groupQQ, String userId, String specialTitle) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("user_id", userId);
        p.put("special_title", specialTitle);
        return req(botQQ, "set_group_special_title", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupLeave(long botQQ, long groupQQ, boolean isDismiss) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        if (isDismiss) p.put("is_dismiss", true);
        return req(botQQ, "set_group_leave", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setGroupAddRequest(long botQQ, String flag, boolean approve, String reason, int count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("flag", flag);
        p.put("approve", approve);
        if (reason != null && !reason.isEmpty()) p.put("reason", reason);
        p.put("count", count);
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
    public CompletableFuture<ApiResponse<GroupSystemMsgData>> getGroupSystemMsg(long botQQ, int count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("count", count);
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
    public CompletableFuture<ApiResponse<VoidData>> deleteEssenceMsg(long botQQ, long messageId, String msgSeq, String msgRandom, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        p.put("msg_seq", msgSeq);
        p.put("msg_random", msgRandom);
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "delete_essence_msg", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupIgnoredNotifiesData>> getGroupIgnoredNotifies(long botQQ) {
        return req(botQQ, "get_group_ignored_notifies", mapper.createObjectNode(), GroupIgnoredNotifiesData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<GroupIgnoreAddRequestData>>> getGroupIgnoreAddRequest(long botQQ) {
        return reqList(botQQ, "get_group_ignore_add_request", mapper.createObjectNode(), GroupIgnoreAddRequestData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendGroupNotice(long botQQ, long groupQQ, String content, String image, int pinned, int type, int confirmRequired, int isShowEditCard, int tipWindowType) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("content", content);
        if (image != null) p.put("image", image);
        p.put("pinned", pinned);
        p.put("type", type);
        p.put("confirm_required", confirmRequired);
        p.put("is_show_edit_card", isShowEditCard);
        p.put("tip_window_type", tipWindowType);
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
    public CompletableFuture<ApiResponse<VoidData>> setGroupSign(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "set_group_sign", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendGroupSign(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "send_group_sign", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> cancelGroupTodo(long botQQ, long groupQQ, String messageId, String messageSeq) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("message_id", messageId);
        if (messageSeq != null) p.put("message_seq", messageSeq);
        return req(botQQ, "cancel_group_todo", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> completeGroupTodo(long botQQ, long groupQQ, String messageId, String messageSeq) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("message_id", messageId);
        if (messageSeq != null) p.put("message_seq", messageSeq);
        return req(botQQ, "complete_group_todo", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<GroupSignedData>>> getGroupSignedList(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return reqList(botQQ, "get_group_signed_list", p, GroupSignedData.class);
    }

    // ================================================================
    // ApiFriend 实现
    // ================================================================

    @Override
    public CompletableFuture<ApiResponse<List<FriendListData>>> getFriendList(long botQQ, boolean noCache) {
        ObjectNode p = mapper.createObjectNode();
        if (noCache) p.put("no_cache", true);
        return reqList(botQQ, "get_friend_list", p, FriendListData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<FriendCategoryData>>> getFriendsWithCategory(long botQQ) {
        return reqList(botQQ, "get_friends_with_category", mapper.createObjectNode(), FriendCategoryData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<UnidirectionalFriendData>>> getUnidirectionalFriendList(long botQQ) {
        return reqList(botQQ, "get_unidirectional_friend_list", mapper.createObjectNode(), UnidirectionalFriendData.class);
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
    public CompletableFuture<ApiResponse<VoidData>> deleteFriend(long botQQ, String userId, String friendId, boolean tempBlock, boolean tempBothDel) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        if (friendId != null) p.put("friend_id", friendId);
        if (tempBlock) p.put("temp_block", true);
        if (tempBothDel) p.put("temp_both_del", true);
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
    public CompletableFuture<ApiResponse<List<DoubtFriendAddRequestData>>> getDoubtFriendsAddRequest(long botQQ, int count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("count", count);
        return reqList(botQQ, "get_doubt_friends_add_request", p, DoubtFriendAddRequestData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setDoubtFriendsAddRequest(long botQQ, String flag, boolean approve) {
        ObjectNode p = mapper.createObjectNode();
        p.put("flag", flag);
        p.put("approve", approve);
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
    public CompletableFuture<ApiResponse<VoidData>> setQqProfile(long botQQ, String nickname, String personalNote, String sex) {
        ObjectNode p = mapper.createObjectNode();
        if (nickname != null) p.put("nickname", nickname);
        if (personalNote != null) p.put("personal_note", personalNote);
        if (sex != null) p.put("sex", sex);
        return req(botQQ, "set_qq_profile", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setSelfLongnick(long botQQ, String longNick) {
        ObjectNode p = mapper.createObjectNode();
        p.put("longNick", longNick);
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
    public CompletableFuture<ApiResponse<CredentialsData>> getCredentials(long botQQ, String domain) {
        ObjectNode p = mapper.createObjectNode();
        p.put("domain", domain);
        return req(botQQ, "get_credentials", p, CredentialsData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<List<RKeyData>>> getRkey(long botQQ) {
        return reqList(botQQ, "get_rkey", mapper.createObjectNode(), RKeyData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<RKeyServerData>> getRkeyServer(long botQQ) {
        return req(botQQ, "get_rkey_server", mapper.createObjectNode(), RKeyServerData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<ClientKeyData>> getClientkey(long botQQ) {
        return req(botQQ, "get_clientkey", mapper.createObjectNode(), ClientKeyData.class);
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
    public CompletableFuture<ApiResponse<String>> setDiyOnlineStatus(long botQQ, String faceId, String faceType, String wording) {
        ObjectNode p = mapper.createObjectNode();
        p.put("face_id", faceId);
        p.put("face_type", faceType);
        p.put("wording", wording);
        return req(botQQ, "set_diy_online_status", p, String.class);
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
    public CompletableFuture<ApiResponse<VoidData>> sendPacket(long botQQ, String cmd, String data, boolean rsp) {
        ObjectNode p = mapper.createObjectNode();
        p.put("cmd", cmd);
        p.put("data", data);
        p.put("rsp", rsp);
        return req(botQQ, "send_packet", p, VoidData.class);
    }

    // ================================================================
    // ApiFile 实现
    // ================================================================

    @Override
    public CompletableFuture<ApiResponse<FileData>> getImage(long botQQ, String file, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file", file);
        if (fileId != null) p.put("file_id", fileId);
        return req(botQQ, "get_image", p, FileData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FileData>> getRecord(long botQQ, String file, String fileId, String outFormat) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file", file);
        if (fileId != null) p.put("file_id", fileId);
        if (outFormat != null) p.put("out_format", outFormat);
        return req(botQQ, "get_record", p, FileData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FileData>> getFile(long botQQ, String file, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file", file);
        if (fileId != null) p.put("file_id", fileId);
        return req(botQQ, "get_file", p, FileData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FileDownloadData>> downloadFile(long botQQ, String headers, String base64, String name, String url) {
        ObjectNode p = mapper.createObjectNode();
        if (headers != null) p.put("headers", headers);
        if (base64 != null) p.put("base64", base64);
        if (name != null) p.put("name", name);
        p.put("url", url);
        return req(botQQ, "download_file", p, FileDownloadData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FileUploadData>> uploadGroupFile(long botQQ, long groupQQ, String file, String name, String folder, String folderId, boolean uploadFile) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file", file);
        p.put("name", name);
        if (folder != null) p.put("folder", folder);
        if (folderId != null) p.put("folder_id", folderId);
        p.put("upload_file", uploadFile);
        return req(botQQ, "upload_group_file", p, FileUploadData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FileUploadData>> uploadPrivateFile(long botQQ, String userId, String file, String name, boolean uploadFile) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("file", file);
        p.put("name", name);
        p.put("upload_file", uploadFile);
        return req(botQQ, "upload_private_file", p, FileUploadData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FileUrlData>> getGroupFileUrl(long botQQ, long groupQQ, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file_id", fileId);
        return req(botQQ, "get_group_file_url", p, FileUrlData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<FileUrlData>> getPrivateFileUrl(long botQQ, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("file_id", fileId);
        return req(botQQ, "get_private_file_url", p, FileUrlData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> deleteGroupFile(long botQQ, long groupQQ, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file_id", fileId);
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
    public CompletableFuture<ApiResponse<VoidData>> deleteGroupFolder(long botQQ, long groupQQ, String folderId, String folder) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("folder_id", folderId);
        if (folder != null) p.put("folder", folder);
        return req(botQQ, "delete_group_folder", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupFileSystemInfoData>> getGroupFileSystemInfo(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "get_group_file_system_info", p, GroupFileSystemInfoData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupRootFilesData>> getGroupRootFiles(long botQQ, long groupQQ, int fileCount) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file_count", fileCount);
        return req(botQQ, "get_group_root_files", p, GroupRootFilesData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupRootFilesData>> getGroupFilesByFolder(long botQQ, long groupQQ, String folderId, String folder, int fileCount) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("folder_id", folderId);
        if (folder != null) p.put("folder", folder);
        p.put("file_count", fileCount);
        return req(botQQ, "get_group_files_by_folder", p, GroupRootFilesData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> moveGroupFile(long botQQ, long groupQQ, String fileId, String currentParentDirectory, String targetParentDirectory) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file_id", fileId);
        p.put("current_parent_directory", currentParentDirectory);
        p.put("target_parent_directory", targetParentDirectory);
        return req(botQQ, "move_group_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> renameGroupFile(long botQQ, long groupQQ, String fileId, String currentParentDirectory, String newName) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file_id", fileId);
        p.put("current_parent_directory", currentParentDirectory);
        p.put("new_name", newName);
        return req(botQQ, "rename_group_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> transGroupFile(long botQQ, long groupQQ, String fileId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("file_id", fileId);
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
    public CompletableFuture<ApiResponse<SendMsgData>> sendForwardMsg(long botQQ, JsonNode message, String messageType, String userId, long groupQQ,
                                                                       String source, JsonNode news, String summary, String prompt, Long timeout) {
        ObjectNode p = mapper.createObjectNode();
        p.set("message", message);
        if (messageType != null) p.put("message_type", messageType);
        if (userId != null) p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        if (source != null) p.put("source", source);
        if (news != null) p.set("news", news);
        if (summary != null) p.put("summary", summary);
        if (prompt != null) p.put("prompt", prompt);
        if (timeout != null) p.put("timeout", timeout);
        return req(botQQ, "send_forward_msg", p, SendMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<SendMsgData>> sendGroupForwardMsg(long botQQ, long groupQQ, JsonNode message,
                                                                            String source, JsonNode news, String summary, String prompt, Long timeout) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.set("message", message);
        if (source != null) p.put("source", source);
        if (news != null) p.set("news", news);
        if (summary != null) p.put("summary", summary);
        if (prompt != null) p.put("prompt", prompt);
        if (timeout != null) p.put("timeout", timeout);
        return req(botQQ, "send_group_forward_msg", p, SendMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<SendMsgData>> sendPrivateForwardMsg(long botQQ, String userId, JsonNode message,
                                                                              String source, JsonNode news, String summary, String prompt, Long timeout) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.set("message", message);
        if (source != null) p.put("source", source);
        if (news != null) p.set("news", news);
        if (summary != null) p.put("summary", summary);
        if (prompt != null) p.put("prompt", prompt);
        if (timeout != null) p.put("timeout", timeout);
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
    public CompletableFuture<ApiResponse<VoidData>> forwardFriendSingleMsg(long botQQ, String userId, long groupQQ, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        p.put("message_id", messageId);
        return req(botQQ, "forward_friend_single_msg", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> forwardGroupSingleMsg(long botQQ, long groupQQ, String userId, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        if (userId != null && !userId.isEmpty()) p.put("user_id", userId);
        p.put("message_id", messageId);
        return req(botQQ, "forward_group_single_msg", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<HistoryMsgData>> getGroupMsgHistory(long botQQ, long groupQQ, long messageSeq, int count, boolean reverseOrder, boolean disableGetUrl, boolean parseMultMsg, boolean quickReply, boolean reverseOrderCompat) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("message_seq", messageSeq);
        p.put("count", count);
        if (reverseOrder) p.put("reverse_order", true);
        if (disableGetUrl) p.put("disable_get_url", true);
        if (parseMultMsg) p.put("parse_mult_msg", true);
        if (quickReply) p.put("quick_reply", true);
        if (reverseOrderCompat) p.put("reverseOrder", true);
        return req(botQQ, "get_group_msg_history", p, HistoryMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<HistoryMsgData>> getFriendMsgHistory(long botQQ, String userId, long messageSeq, int count, boolean reverseOrder, boolean disableGetUrl, boolean parseMultMsg, boolean quickReply, boolean reverseOrderCompat) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("message_seq", messageSeq);
        p.put("count", count);
        if (reverseOrder) p.put("reverse_order", true);
        if (disableGetUrl) p.put("disable_get_url", true);
        if (parseMultMsg) p.put("parse_mult_msg", true);
        if (quickReply) p.put("quick_reply", true);
        if (reverseOrderCompat) p.put("reverseOrder", true);
        return req(botQQ, "get_friend_msg_history", p, HistoryMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> markMsgAsRead(long botQQ, long groupQQ, String userId, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        if (userId != null) p.put("user_id", userId);
        if (messageId != null) p.put("message_id", messageId);
        return req(botQQ, "mark_msg_as_read", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> markGroupMsgAsRead(long botQQ, long groupQQ, String userId, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        if (userId != null) p.put("user_id", userId);
        if (messageId != null) p.put("message_id", messageId);
        return req(botQQ, "mark_group_msg_as_read", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> markPrivateMsgAsRead(long botQQ, String userId, long groupQQ, String messageId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        if (messageId != null) p.put("message_id", messageId);
        return req(botQQ, "mark_private_msg_as_read", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> markAllAsRead(long botQQ) {
        return req(botQQ, "_mark_all_as_read", mapper.createObjectNode(), VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> groupPoke(long botQQ, long groupQQ, String userId, String targetId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("user_id", userId);
        p.put("target_id", targetId);
        return req(botQQ, "group_poke", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> friendPoke(long botQQ, String userId, long groupQQ, String targetId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        p.put("target_id", targetId);
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
    public CompletableFuture<ApiResponse<EmojiLikeDetailData>> fetchEmojiLike(long botQQ, String messageId, String emojiId, String emojiType, String count, String cookie) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        p.put("emojiId", emojiId);
        p.put("emojiType", emojiType);
        if (count != null) p.put("count", count);
        if (cookie != null) p.put("cookie", cookie);
        return req(botQQ, "fetch_emoji_like", p, EmojiLikeDetailData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<EmojiLikesData>> getEmojiLikes(long botQQ, String messageId, String emojiId, String emojiType, long groupQQ, int count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("message_id", messageId);
        p.put("emoji_id", emojiId);
        if (emojiType != null) p.put("emoji_type", emojiType);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        if (count > 0) p.put("count", count);
        return req(botQQ, "get_emoji_likes", p, EmojiLikesData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setMsgEmojiLike(long botQQ, String messageId, String emojiId, boolean set) {
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
    public CompletableFuture<ApiResponse<QunAlbumListData>> getQunAlbumList(long botQQ, long groupQQ, String attachInfo) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        if (attachInfo != null && !attachInfo.isEmpty()) p.put("attach_info", attachInfo);
        return req(botQQ, "get_qun_album_list", p, QunAlbumListData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<GroupAlbumMediaListData>> getGroupAlbumMediaList(long botQQ, long groupQQ, String albumId, String attachInfo) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("album_id", albumId);
        if (attachInfo != null && !attachInfo.isEmpty()) p.put("attach_info", attachInfo);
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
    public CompletableFuture<ApiResponse<VoidData>> setGroupAlbumMediaLike(long botQQ, long groupQQ, String albumId, String lloc, String batchId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("album_id", albumId);
        p.put("lloc", lloc);
        p.put("batch_id", batchId);
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
    public CompletableFuture<ApiResponse<VoidData>> uploadImageToQunAlbum(long botQQ, long groupQQ, String albumId, String albumName, String file) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("album_id", albumId);
        p.put("album_name", albumName);
        p.put("file", file);
        return req(botQQ, "upload_image_to_qun_album", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> uploadFileStream(long botQQ, String streamId, String chunkData, int chunkIndex, int totalChunks, long fileSize, String expectedSha256, boolean isComplete, String filename, boolean reset, boolean verifyOnly, long fileRetention) {
        ObjectNode p = mapper.createObjectNode();
        p.put("stream_id", streamId);
        if (chunkData != null) p.put("chunk_data", chunkData);
        p.put("chunk_index", chunkIndex);
        p.put("total_chunks", totalChunks);
        p.put("file_size", fileSize);
        if (expectedSha256 != null) p.put("expected_sha256", expectedSha256);
        if (isComplete) p.put("is_complete", true);
        if (filename != null) p.put("filename", filename);
        if (reset) p.put("reset", true);
        if (verifyOnly) p.put("verify_only", true);
        p.put("file_retention", fileRetention);
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
    public CompletableFuture<ApiResponse<VoidData>> testDownloadStream(long botQQ, boolean error) {
        ObjectNode p = mapper.createObjectNode();
        if (error) p.put("error", true);
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
    public CompletableFuture<ApiResponse<CreateCollectionData>> createCollection(long botQQ, String rawData, String brief) {
        ObjectNode p = mapper.createObjectNode();
        p.put("rawData", rawData);
        p.put("brief", brief);
        return req(botQQ, "create_collection", p, CreateCollectionData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<CollectionListData>> getCollectionList(long botQQ, String category, String count) {
        ObjectNode p = mapper.createObjectNode();
        p.put("category", category);
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
    public CompletableFuture<ApiResponse<String>> getAiRecord(long botQQ, String character, String text, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("character", character);
        p.put("text", text);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "get_ai_record", p, String.class);
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
    public CompletableFuture<ApiResponse<FlashTaskData>> createFlashTask(long botQQ, String files, String name, String thumbPath) {
        ObjectNode p = mapper.createObjectNode();
        p.put("files", files);
        if (name != null) p.put("name", name);
        if (thumbPath != null) p.put("thumb_path", thumbPath);
        return req(botQQ, "create_flash_task", p, FlashTaskData.class);
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
    public CompletableFuture<ApiResponse<OnlineFileMsgData>> getOnlineFileMsg(long botQQ, String userId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        return req(botQQ, "get_online_file_msg", p, OnlineFileMsgData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendOnlineFile(long botQQ, String userId, String filePath, String fileName) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("file_path", filePath);
        if (fileName != null) p.put("file_name", fileName);
        return req(botQQ, "send_online_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> sendOnlineFolder(long botQQ, String userId, String folderPath, String folderName) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("folder_path", folderPath);
        if (folderName != null) p.put("folder_name", folderName);
        return req(botQQ, "send_online_folder", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> receiveOnlineFile(long botQQ, String userId, String msgId, String elementId, String destPath) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("msg_id", msgId);
        p.put("element_id", elementId);
        if (destPath != null) p.put("dest_path", destPath);
        return req(botQQ, "receive_online_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> refuseOnlineFile(long botQQ, String userId, String msgId, String elementId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("msg_id", msgId);
        p.put("element_id", elementId);
        return req(botQQ, "refuse_online_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> cancelOnlineFile(long botQQ, String userId, String msgId, String elementId) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
        p.put("msg_id", msgId);
        p.put("element_id", elementId);
        return req(botQQ, "cancel_online_file", p, VoidData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> setInputStatus(long botQQ, String userId, int eventType) {
        ObjectNode p = mapper.createObjectNode();
        p.put("user_id", userId);
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
    public CompletableFuture<ApiResponse<String>> ArkShareGroup(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "ArkShareGroup", p, String.class);
    }

    @Override
    public CompletableFuture<ApiResponse<ArkShareData>> ArkSharePeer(long botQQ, String userId, String phoneNumber) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", userId);
        if (phoneNumber != null) p.put("phone_number", phoneNumber);
        return req(botQQ, "ArkSharePeer", p, ArkShareData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<String>> sendGroupArkShare(long botQQ, long groupQQ) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        return req(botQQ, "send_group_ark_share", p, String.class);
    }

    @Override
    public CompletableFuture<ApiResponse<ArkShareData>> sendArkShare(long botQQ, String userId, long groupQQ, String phoneNumber) {
        ObjectNode p = mapper.createObjectNode();
        if (userId != null) p.put("user_id", userId);
        if (groupQQ != 0) p.put("group_id", Long.toString(groupQQ));
        p.put("phone_number", phoneNumber != null ? phoneNumber : "");
        return req(botQQ, "send_ark_share", p, ArkShareData.class);
    }

    @Override
    public CompletableFuture<ApiResponse<VoidData>> clickInlineKeyboardButton(long botQQ, long groupQQ, String botAppid, String buttonId, String callbackData, String msgSeq) {
        ObjectNode p = mapper.createObjectNode();
        p.put("group_id", Long.toString(groupQQ));
        p.put("bot_appid", botAppid);
        p.put("button_id", buttonId);
        p.put("callback_data", callbackData);
        p.put("msg_seq", msgSeq);
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
    public CompletableFuture<ApiResponse<PttTextData>> fetchPttText(long botQQ, String messageId) {
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
