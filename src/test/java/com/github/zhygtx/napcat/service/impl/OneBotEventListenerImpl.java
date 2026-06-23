package com.github.zhygtx.napcat.service.impl;

import com.github.zhygtx.napcat.api.NapCat;
import com.github.zhygtx.napcat.event.OneBotEventListener;
import com.github.zhygtx.napcat.event.message.*;
import com.github.zhygtx.napcat.event.meta.HeartbeatMetaEvent;
import com.github.zhygtx.napcat.event.meta.LifecycleConnectMetaEvent;
import com.github.zhygtx.napcat.event.meta.LifecycleMetaEvent;
import com.github.zhygtx.napcat.event.notice.*;
import com.github.zhygtx.napcat.event.request.FriendRequestEvent;
import com.github.zhygtx.napcat.event.request.GroupAddRequestEvent;
import com.github.zhygtx.napcat.event.request.GroupInviteRequestEvent;
import com.github.zhygtx.napcat.event.request.GroupRequestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("JavadocDeclaration")
@Service
public class OneBotEventListenerImpl implements OneBotEventListener {

    @Autowired
    private NapCat napCat;

    /**
     * 收到生命周期事件时调用。
     * @param botQQ
     * @param event
     */
    @Override
    public void onLifecycle(Long botQQ, LifecycleMetaEvent event) {
        OneBotEventListener.super.onLifecycle(botQQ, event);
    }

    /**
     * 好友添加时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onFriendAdd(Long botQQ, FriendAddNoticeEvent event) {
        OneBotEventListener.super.onFriendAdd(botQQ, event);
    }

    /**
     * 好友消息撤回时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onFriendRecall(Long botQQ, FriendRecallNoticeEvent event) {
        OneBotEventListener.super.onFriendRecall(botQQ, event);
    }

    /**
     * 收到好友请求时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onFriendRequest(Long botQQ, FriendRequestEvent event) {
        OneBotEventListener.super.onFriendRequest(botQQ, event);
    }

    /**
     * 收到加群申请时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupAddRequest(Long botQQ, GroupAddRequestEvent event) {
        OneBotEventListener.super.onGroupAddRequest(botQQ, event);
    }

    /**
     * 群管理员变动时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupAdmin(Long botQQ, GroupAdminNoticeEvent event) {
        OneBotEventListener.super.onGroupAdmin(botQQ, event);
    }

    /**
     * 群管理员被设置时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupAdminSet(Long botQQ, GroupAdminSetNoticeEvent event) {
        OneBotEventListener.super.onGroupAdminSet(botQQ, event);
    }

    /**
     * 群管理员被取消时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupAdminUnset(Long botQQ, GroupAdminUnsetNoticeEvent event) {
        OneBotEventListener.super.onGroupAdminUnset(botQQ, event);
    }

    /**
     * 群禁言/解除禁言时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupBan(Long botQQ, GroupBanNoticeEvent event) {
        OneBotEventListener.super.onGroupBan(botQQ, event);
    }

    /**
     * 群成员被禁言时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupBanBan(Long botQQ, GroupBanBanNoticeEvent event) {
        OneBotEventListener.super.onGroupBanBan(botQQ, event);
    }

    /**
     * 群成员被解除禁言时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupBanLiftBan(Long botQQ, GroupBanLiftBanNoticeEvent event) {
        OneBotEventListener.super.onGroupBanLiftBan(botQQ, event);
    }

    /**
     * 群名片变更时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupCard(Long botQQ, GroupCardNoticeEvent event) {
        OneBotEventListener.super.onGroupCard(botQQ, event);
    }

    /**
     * 群成员减少时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupDecrease(Long botQQ, GroupDecreaseNoticeEvent event) {
        OneBotEventListener.super.onGroupDecrease(botQQ, event);
    }

    /**
     * 群成员被踢出时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupDecreaseKick(Long botQQ, GroupDecreaseKickNoticeEvent event) {
        OneBotEventListener.super.onGroupDecreaseKick(botQQ, event);
    }

    /**
     * 机器人自己被踢出群时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupDecreaseKickMe(Long botQQ, GroupDecreaseKickMeNoticeEvent event) {
        OneBotEventListener.super.onGroupDecreaseKickMe(botQQ, event);
    }

    /**
     * 群成员主动退群时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupDecreaseLeave(Long botQQ, GroupDecreaseLeaveNoticeEvent event) {
        OneBotEventListener.super.onGroupDecreaseLeave(botQQ, event);
    }

    /**
     * 群精华消息变更时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupEssence(Long botQQ, GroupEssenceNoticeEvent event) {
        OneBotEventListener.super.onGroupEssence(botQQ, event);
    }

    /**
     * 群精华消息被添加时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupEssenceAdd(Long botQQ, GroupEssenceAddNoticeEvent event) {
        OneBotEventListener.super.onGroupEssenceAdd(botQQ, event);
    }

    /**
     * 群成员增加时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupIncrease(Long botQQ, GroupIncreaseNoticeEvent event) {
        OneBotEventListener.super.onGroupIncrease(botQQ, event);
    }

    /**
     * 群成员被管理员同意入群时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupIncreaseApprove(Long botQQ, GroupIncreaseApproveNoticeEvent event) {
        OneBotEventListener.super.onGroupIncreaseApprove(botQQ, event);
    }

    /**
     * 群成员被管理员邀请入群时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupIncreaseInvite(Long botQQ, GroupIncreaseInviteNoticeEvent event) {
        OneBotEventListener.super.onGroupIncreaseInvite(botQQ, event);
    }

    /**
     * 机器人被邀请入群时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupInviteRequest(Long botQQ, GroupInviteRequestEvent event) {
        OneBotEventListener.super.onGroupInviteRequest(botQQ, event);
    }

    /**
     * Bot 收到群聊消息时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupMessage(Long botQQ, GroupMessageEvent event) {
        System.out.println(botQQ);
        System.out.println(event.toString());
        napCat.sendGroupMsg(botQQ,event.getGroupId(),event.getRawMessage(),true);
    }

    /**
     * 机器人发送群聊消息时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupMessageSent(Long botQQ, GroupMessageSentEvent event) {
        System.out.println("Bot发送了群消息:"+event.toString());
    }

    /**
     * 群表情回应时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupMsgEmojiLike(Long botQQ, GroupMsgEmojiLikeNoticeEvent event) {
        OneBotEventListener.super.onGroupMsgEmojiLike(botQQ, event);
    }

    /**
     * Bot 收到普通群聊消息时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupNormalMessage(Long botQQ, GroupNormalMessageEvent event) {
        OneBotEventListener.super.onGroupNormalMessage(botQQ, event);
    }

    /**
     * 机器人发送普通群聊消息时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupNormalMessageSent(Long botQQ, GroupNormalMessageSentEvent event) {
        OneBotEventListener.super.onGroupNormalMessageSent(botQQ, event);
    }

    /**
     * 群消息被撤回时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupRecall(Long botQQ, GroupRecallNoticeEvent event) {
        OneBotEventListener.super.onGroupRecall(botQQ, event);
    }

    /**
     * 收到群请求时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupRequest(Long botQQ, GroupRequestEvent event) {
        OneBotEventListener.super.onGroupRequest(botQQ, event);
    }

    /**
     * 群成员头衔变更时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupTitle(Long botQQ, TitleNoticeEvent event) {
        OneBotEventListener.super.onGroupTitle(botQQ, event);
    }

    /**
     * 群文件上传时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onGroupUpload(Long botQQ, GroupUploadNoticeEvent event) {
        OneBotEventListener.super.onGroupUpload(botQQ, event);
    }

    /**
     * 收到心跳事件时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onHeartbeat(Long botQQ, HeartbeatMetaEvent event) {
        OneBotEventListener.super.onHeartbeat(botQQ, event);
    }

    /**
     * 好友输入状态变化时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onInputStatus(Long botQQ, InputStatusNoticeEvent event) {
        OneBotEventListener.super.onInputStatus(botQQ, event);
    }

    /**
     * WebSocket 连接成功时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onLifecycleConnect(Long botQQ, LifecycleConnectMetaEvent event) {
        OneBotEventListener.super.onLifecycleConnect(botQQ, event);
    }

    /**
     * 机器人自身发送消息时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onMessageSent(Long botQQ, MessageSentEvent event) {
        OneBotEventListener.super.onMessageSent(botQQ, event);
    }

    /**
     * 戳一戳时调用（好友或群聊场景均可）。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onPoke(Long botQQ, PokeNoticeEvent event) {
        OneBotEventListener.super.onPoke(botQQ, event);
    }

    /**
     * Bot 收到好友私聊消息时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onPrivateFriendMessage(Long botQQ, PrivateFriendMessageEvent event) {
        OneBotEventListener.super.onPrivateFriendMessage(botQQ, event);
    }

    /**
     * 机器人发送好友私聊消息时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onPrivateFriendMessageSent(Long botQQ, PrivateFriendMessageSentEvent event) {
        OneBotEventListener.super.onPrivateFriendMessageSent(botQQ, event);
    }

    /**
     * Bot 收到群临时会话消息时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onPrivateGroupMessage(Long botQQ, PrivateGroupMessageEvent event) {
        OneBotEventListener.super.onPrivateGroupMessage(botQQ, event);
    }

    /**
     * 机器人发送群临时会话消息时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onPrivateGroupMessageSent(Long botQQ, PrivateGroupMessageSentEvent event) {
        OneBotEventListener.super.onPrivateGroupMessageSent(botQQ, event);
    }

    /**
     * Bot 收到私聊消息时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onPrivateMessage(Long botQQ, PrivateMessageEvent event) {
        OneBotEventListener.super.onPrivateMessage(botQQ, event);
    }

    /**
     * 机器人发送私聊消息时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onPrivateMessageSent(Long botQQ, PrivateMessageSentEvent event) {
        OneBotEventListener.super.onPrivateMessageSent(botQQ, event);
    }

    /**
     * 个人资料被点赞时调用。
     *
     * @param botQQ
     * @param event
     */
    @Override
    public void onProfileLike(Long botQQ, ProfileLikeNoticeEvent event) {
        OneBotEventListener.super.onProfileLike(botQQ, event);
    }
}
