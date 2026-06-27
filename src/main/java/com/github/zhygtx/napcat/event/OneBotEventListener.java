package com.github.zhygtx.napcat.event;

import com.github.zhygtx.napcat.event.message.*;
import com.github.zhygtx.napcat.event.meta.HeartbeatMetaEvent;
import com.github.zhygtx.napcat.event.meta.LifecycleConnectMetaEvent;
import com.github.zhygtx.napcat.event.meta.LifecycleMetaEvent;
import com.github.zhygtx.napcat.event.notice.*;
import com.github.zhygtx.napcat.event.request.FriendRequestEvent;
import com.github.zhygtx.napcat.event.request.GroupAddRequestEvent;
import com.github.zhygtx.napcat.event.request.GroupInviteRequestEvent;
import com.github.zhygtx.napcat.event.request.GroupRequestEvent;

import java.util.List;

/**
 * OneBot 事件统一监听器接口。
 * <p>
 * 用户实现此接口并注册为 Spring Bean，即可接收所有类型的 OneBot 事件。
 * 接口中所有方法均为 default，用户可以仅覆写需要的方法。
 * <p>
 * 使用示例：
 * <pre>{@code
 * @Component
 * public class MyEventListener implements OneBotEventListener {
 *     @Override
 *     public void onGroupMessage(Long botQQ, GroupMessageEvent event) {
 *         log.info("[{}] 群 {}: {}", botQQ, event.getGroupId(), event.getRawMessage());
 *     }
 * }
 * }</pre>
 */
public interface OneBotEventListener {

    // ====================================================
    //  全局事件（所有事件类型均会触发）
    // ====================================================

    /**
     * 收到任何类型的 OneBot 事件时调用。
     * <p>
     * 此方法在具体事件回调（如 {@link #onGroupMessage}）之后触发，
     * 适合用于统一日志记录、事件监控等横切关注点。
     * <p>
     * {@code triggeredTypes} 列出当前事件按类层级会触发的所有回调类型，
     * 从具体子类排列到父类。例如收到 {@code GroupNormalMessageEvent} 时：
     * {@code [GroupNormalMessageEvent, GroupMessageEvent]}，表示会依次调用
     * {@code onGroupNormalMessage} 和 {@code onGroupMessage}。
     * <p>
     * 使用示例：
     * <pre>{@code
     * @Override
     * public void onAnyEvent(Long botQQ, BaseEvent event,
     *                        List<Class<? extends BaseEvent>> triggeredTypes) {
     *     log.info("[Bot:{}] 事件: {} | 将触发: {}",
     *             botQQ,
     *             event.getClass().getSimpleName(),
     *             triggeredTypes.stream()
     *                 .map(Class::getSimpleName)
     *                 .collect(Collectors.joining(" → ")));
     * }
     * }</pre>
     *
     * @param botQQ          收到事件的 Bot QQ 号
     * @param event          事件对象（具体子类）
     * @param triggeredTypes 当前事件会触发的回调类型列表（从具体到泛化），不可变且非空
     */
    default void onAnyEvent(Long botQQ, BaseEvent event,
                            List<Class<? extends BaseEvent>> triggeredTypes) {}

    // ====================================================
    //  元事件 (Meta Event)
    // ====================================================

    /**
     * 收到生命周期事件时调用。
     */
    default void onLifecycle(Long botQQ, LifecycleMetaEvent event) {}

    /**
     * WebSocket 连接成功时调用。
     */
    default void onLifecycleConnect(Long botQQ, LifecycleConnectMetaEvent event) {}

    /**
     * 收到心跳事件时调用。
     */
    default void onHeartbeat(Long botQQ, HeartbeatMetaEvent event) {}

    // ====================================================
    //  消息事件 (Message Event)
    // ====================================================

    /**
     * Bot 收到私聊消息时调用。
     */
    default void onPrivateMessage(Long botQQ, PrivateMessageEvent event) {}

    /**
     * Bot 收到好友私聊消息时调用。
     */
    default void onPrivateFriendMessage(Long botQQ, PrivateFriendMessageEvent event) {}

    /**
     * Bot 收到群临时会话消息时调用。
     */
    default void onPrivateGroupMessage(Long botQQ, PrivateGroupMessageEvent event) {}

    /**
     * Bot 收到群聊消息时调用。
     */
    default void onGroupMessage(Long botQQ, GroupMessageEvent event) {}

    /**
     * Bot 收到普通群聊消息时调用。
     */
    default void onGroupNormalMessage(Long botQQ, GroupNormalMessageEvent event) {}

    // ====================================================
    //  消息发送事件 (Message Sent Event)
    // ====================================================

    /**
     * 机器人自身发送消息时调用。
     */
    default void onMessageSent(Long botQQ, MessageSentEvent event) {}

    /**
     * 机器人发送私聊消息时调用。
     */
    default void onPrivateMessageSent(Long botQQ, PrivateMessageSentEvent event) {}

    /**
     * 机器人发送好友私聊消息时调用。
     */
    default void onPrivateFriendMessageSent(Long botQQ, PrivateFriendMessageSentEvent event) {}

    /**
     * 机器人发送群临时会话消息时调用。
     */
    default void onPrivateGroupMessageSent(Long botQQ, PrivateGroupMessageSentEvent event) {}

    /**
     * 机器人发送群聊消息时调用。
     */
    default void onGroupMessageSent(Long botQQ, GroupMessageSentEvent event) {}

    /**
     * 机器人发送普通群聊消息时调用。
     */
    default void onGroupNormalMessageSent(Long botQQ, GroupNormalMessageSentEvent event) {}

    // ====================================================
    //  通知事件 - 好友 (Notice Event - Friend)
    // ====================================================

    /**
     * 好友添加时调用。
     */
    default void onFriendAdd(Long botQQ, FriendAddNoticeEvent event) {}

    /**
     * 好友消息撤回时调用。
     */
    default void onFriendRecall(Long botQQ, FriendRecallNoticeEvent event) {}

    // ====================================================
    //  通知事件 - 群管理员 (Notice Event - Group Admin)
    // ====================================================

    /**
     * 群管理员变动时调用。
     */
    default void onGroupAdmin(Long botQQ, GroupAdminNoticeEvent event) {}

    /**
     * 群管理员被设置时调用。
     */
    default void onGroupAdminSet(Long botQQ, GroupAdminSetNoticeEvent event) {}

    /**
     * 群管理员被取消时调用。
     */
    default void onGroupAdminUnset(Long botQQ, GroupAdminUnsetNoticeEvent event) {}

    // ====================================================
    //  通知事件 - 群禁言 (Notice Event - Group Ban)
    // ====================================================

    /**
     * 群禁言/解除禁言时调用。
     */
    default void onGroupBan(Long botQQ, GroupBanNoticeEvent event) {}

    /**
     * 群成员被禁言时调用。
     */
    default void onGroupBanBan(Long botQQ, GroupBanBanNoticeEvent event) {}

    /**
     * 群成员被解除禁言时调用。
     */
    default void onGroupBanLiftBan(Long botQQ, GroupBanLiftBanNoticeEvent event) {}

    // ====================================================
    //  通知事件 - 群成员减少 (Notice Event - Group Decrease)
    // ====================================================

    /**
     * 群成员减少时调用。
     */
    default void onGroupDecrease(Long botQQ, GroupDecreaseNoticeEvent event) {}

    /**
     * 群成员主动退群时调用。
     */
    default void onGroupDecreaseLeave(Long botQQ, GroupDecreaseLeaveNoticeEvent event) {}

    /**
     * 群成员被踢出时调用。
     */
    default void onGroupDecreaseKick(Long botQQ, GroupDecreaseKickNoticeEvent event) {}

    /**
     * 机器人自己被踢出群时调用。
     */
    default void onGroupDecreaseKickMe(Long botQQ, GroupDecreaseKickMeNoticeEvent event) {}

    // ====================================================
    //  通知事件 - 群成员增加 (Notice Event - Group Increase)
    // ====================================================

    /**
     * 群成员增加时调用。
     */
    default void onGroupIncrease(Long botQQ, GroupIncreaseNoticeEvent event) {}

    /**
     * 群成员被管理员同意入群时调用。
     */
    default void onGroupIncreaseApprove(Long botQQ, GroupIncreaseApproveNoticeEvent event) {}

    /**
     * 群成员被管理员邀请入群时调用。
     */
    default void onGroupIncreaseInvite(Long botQQ, GroupIncreaseInviteNoticeEvent event) {}

    // ====================================================
    //  通知事件 - 其他群聊 (Notice Event - Other Group)
    // ====================================================

    /**
     * 群消息被撤回时调用。
     */
    default void onGroupRecall(Long botQQ, GroupRecallNoticeEvent event) {}

    /**
     * 群文件上传时调用。
     */
    default void onGroupUpload(Long botQQ, GroupUploadNoticeEvent event) {}

    /**
     * 群名片变更时调用。
     */
    default void onGroupCard(Long botQQ, GroupCardNoticeEvent event) {}

    /**
     * 群精华消息变更时调用。
     */
    default void onGroupEssence(Long botQQ, GroupEssenceNoticeEvent event) {}

    /**
     * 群精华消息被添加时调用。
     */
    default void onGroupEssenceAdd(Long botQQ, GroupEssenceAddNoticeEvent event) {}

    /**
     * 群表情回应时调用。
     */
    default void onGroupMsgEmojiLike(Long botQQ, GroupMsgEmojiLikeNoticeEvent event) {}

    /**
     * 群成员头衔变更时调用。
     */
    default void onGroupTitle(Long botQQ, TitleNoticeEvent event) {}

    // ====================================================
    //  通知事件 - 其他 (Notice Event - Other)
    // ====================================================

    /**
     * 戳一戳时调用（好友或群聊场景均可）。
     */
    default void onPoke(Long botQQ, PokeNoticeEvent event) {}

    /**
     * 好友输入状态变化时调用。
     */
    default void onInputStatus(Long botQQ, InputStatusNoticeEvent event) {}

    /**
     * 个人资料被点赞时调用。
     */
    default void onProfileLike(Long botQQ, ProfileLikeNoticeEvent event) {}

    /**
     * Bot QQ 账号离线时调用。
     * <p>
     * 对应 {@code notice:bot_offline} 事件，当 Bot 的 QQ 账号因被踢下线、
     * 风控等原因离线（但 WebSocket 连接仍保持）时触发。
     * <p>
     * SDK 已自动处理 {@code BotEventListener.botOffline()} 回调，
     * 本方法用于需要获取离线详情（tag、message）的场景。
     */
    default void onBotOffline(Long botQQ, BotOfflineNoticeEvent event) {}

    // ====================================================
    //  请求事件 (Request Event)
    // ====================================================

    /**
     * 收到好友请求时调用。
     */
    default void onFriendRequest(Long botQQ, FriendRequestEvent event) {}

    /**
     * 收到群请求时调用。
     */
    default void onGroupRequest(Long botQQ, GroupRequestEvent event) {}

    /**
     * 收到加群申请时调用。
     */
    default void onGroupAddRequest(Long botQQ, GroupAddRequestEvent event) {}

    /**
     * 机器人被邀请入群时调用。
     */
    default void onGroupInviteRequest(Long botQQ, GroupInviteRequestEvent event) {}
}
