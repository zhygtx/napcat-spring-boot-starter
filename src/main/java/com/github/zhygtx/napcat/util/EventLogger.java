package com.github.zhygtx.napcat.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zhygtx.napcat.config.NapCatProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 事件日志记录器。
 * <p>
 * 接收原始 JSON 事件 payload，自动识别 41 种 OneBot 事件类型，
 * 提取关键字段并格式化为可读的中文日志输出。
 * <p>
 * 使用示例：
 * <pre>{@code
 * EventLogger eventLogger; // 注入
 * eventLogger.logEvent(session.getId(), payload);
 * }</pre>
 */
@Component
public class EventLogger {

    private static final Logger log = LoggerFactory.getLogger(EventLogger.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final LogSanitizer sanitizer;
    private final NapCatProperties properties;

    public EventLogger(LogSanitizer sanitizer, NapCatProperties properties) {
        this.sanitizer = sanitizer;
        this.properties = properties;
    }

    /**
     * 解析并打印事件日志（字符串入口）。
     * <p>
     * 内部会进行一次 JSON 解析，建议优先使用 {@link #logEvent(String, JsonNode)} 避免重复解析。
     */
    public void logEvent(String sessionId, String payload) {
        if (payload == null || payload.isEmpty()) return;
        try {
            JsonNode json = mapper.readTree(payload);
            logEvent(sessionId, json);
        } catch (Exception e) {
            log.trace("事件日志解析失败: {}", e.getMessage());
        }
    }

    /**
     * 解析并打印事件日志（JsonNode 入口）。
     * <p>
     * 先判断 debug 是否开启，关闭则直接返回零开销。
     * 根据 JSON 内容自动检测事件类型，提取关键字段，格式化为中文日志。
     * 如果事件为心跳且 {@code ignoreHeartbeatLog=true} 则不输出。
     *
     * @param sessionId WebSocket 会话 ID
     * @param json      已解析的 JsonNode（避免重复解析）
     */
    public void logEvent(String sessionId, JsonNode json) {
        // debug 关闭 → 零开销
        if (!log.isDebugEnabled()) return;

        String typeDesc = buildEventDescription(json);
        if (typeDesc == null) return;

        // 心跳静默
        if (typeDesc.startsWith("心跳") && properties.getLog().isIgnoreHeartbeatLog()) {
            return;
        }

        log.debug("[{}] 会话: {} | {}", typeDesc, sessionId, sanitizer.sanitize(json.toString()));
    }

    /**
     * 根据 JSON 内容构建事件描述。
     *
     * @return 格式化的描述字符串，无法识别则返回 null
     */
    private String buildEventDescription(JsonNode json) {
        if (!json.has("post_type")) return null;

        String postType = json.get("post_type").asText();
        long selfId = json.has("self_id") ? json.get("self_id").asLong() : 0;

        return switch (postType) {
            case "meta_event" -> buildMetaDesc(json, selfId);
            case "message" -> buildMessageDesc(json, selfId);
            case "message_sent" -> buildMessageSentDesc(json, selfId);
            case "notice" -> buildNoticeDesc(json, selfId);
            case "request" -> buildRequestDesc(json, selfId);
            default -> null;
        };
    }

    // ============================================================
    //  元事件
    // ============================================================

    private String buildMetaDesc(JsonNode json, long selfId) {
        String metaType = json.has("meta_event_type") ? json.get("meta_event_type").asText() : "";
        if ("heartbeat".equals(metaType)) {
            boolean online = json.path("status").path("online").asBoolean(false);
            boolean good = json.path("status").path("good").asBoolean(false);
            long interval = json.has("interval") ? json.get("interval").asLong() : 0;
            return sj("心跳", selfId, "在线:" + online, "良好:" + good, "间隔:" + interval + "s");
        }
        if ("lifecycle".equals(metaType)) {
            String sub = json.has("sub_type") ? json.get("sub_type").asText() : "";
            if ("connect".equals(sub)) {
                return sj("连接成功", selfId, "生命周期:connect");
            }
            return sj("生命周期", selfId, "子类型:" + sub);
        }
        return null;
    }

    // ============================================================
    //  消息事件
    // ============================================================

    private String buildMessageDesc(JsonNode json, long selfId) {
        String msgType = json.has("message_type") ? json.get("message_type").asText() : "";
        long userId = json.has("user_id") ? json.get("user_id").asLong() : 0;
        String rawMsg = json.has("raw_message") ? json.get("raw_message").asText() : "";
        String sub = json.has("sub_type") ? json.get("sub_type").asText() : "";

        if ("private".equals(msgType)) {
            if ("friend".equals(sub)) {
                return sj("好友私聊", selfId, "发送者:" + userId, "内容:" + rawMsg);
            }
            if ("group".equals(sub)) {
                return sj("临时会话", selfId, "发送者:" + userId, "内容:" + rawMsg);
            }
            return sj("私聊", selfId, "发送者:" + userId, "内容:" + rawMsg);
        }
        if ("group".equals(msgType)) {
            long groupId = json.has("group_id") ? json.get("group_id").asLong() : 0;
            if ("normal".equals(sub)) {
                return sj("群聊消息", selfId, "群:" + groupId, "发送者:" + userId, "内容:" + rawMsg);
            }
            return sj("群聊", selfId, "群:" + groupId, "发送者:" + userId, "内容:" + rawMsg);
        }
        return null;
    }

    // ============================================================
    //  消息发送事件
    // ============================================================

    private String buildMessageSentDesc(JsonNode json, long selfId) {
        String msgType = json.has("message_type") ? json.get("message_type").asText() : "";
        long targetId = json.has("target_id") ? json.get("target_id").asLong() : 0;
        String rawMsg = json.has("raw_message") ? json.get("raw_message").asText() : "";
        String sub = json.has("sub_type") ? json.get("sub_type").asText() : "";

        if ("group".equals(msgType)) {
            long groupId = json.has("group_id") ? json.get("group_id").asLong() : 0;
            if ("normal".equals(sub)) {
                return sj("群消息发送", selfId, "群:" + groupId, "内容:" + rawMsg);
            }
            return sj("群聊发送", selfId, "群:" + groupId, "内容:" + rawMsg);
        }
        if ("private".equals(msgType)) {
            if ("friend".equals(sub)) {
                return sj("好友消息发送", selfId, "目标:" + targetId, "内容:" + rawMsg);
            }
            if ("group".equals(sub)) {
                return sj("临时会话发送", selfId, "目标:" + targetId, "内容:" + rawMsg);
            }
            return sj("私聊发送", selfId, "目标:" + targetId, "内容:" + rawMsg);
        }
        return sj("消息发送", selfId, "目标:" + targetId, "内容:" + rawMsg);
    }

    // ============================================================
    //  通知事件
    // ============================================================

    private String buildNoticeDesc(JsonNode json, long selfId) {
        String noticeType = json.has("notice_type") ? json.get("notice_type").asText() : "";
        long userId = json.has("user_id") ? json.get("user_id").asLong() : 0;

        // 先处理 notify 子类型
        if ("notify".equals(noticeType) && json.has("sub_type")) {
            String sub = json.get("sub_type").asText();
            return switch (sub) {
                case "poke" -> {
                    long targetId = json.has("target_id") ? json.get("target_id").asLong() : 0;
                    yield sj("戳一戳", selfId, "发送者:" + userId, "目标:" + targetId);
                }
                case "title" -> {
                    String title = json.has("title") ? json.get("title").asText() : "";
                    yield sj("群头衔变更", selfId, "用户:" + userId, "新头衔:" + title);
                }
                case "profile_like" -> {
                    String nick = json.has("operator_nick") ? json.get("operator_nick").asText() : "";
                    int times = json.has("times") ? json.get("times").asInt() : 0;
                    yield sj("资料点赞", selfId, "操作者:" + nick, "次数:" + times);
                }
                case "input_status" -> {
                    String statusText = json.has("status_text") ? json.get("status_text").asText() : "";
                    yield sj("输入状态", selfId, "用户:" + userId, "状态:" + statusText);
                }
                default -> sj("通知:" + sub, selfId, "用户:" + userId);
            };
        }

        long groupId = json.has("group_id") ? json.get("group_id").asLong() : 0;
        String sub = json.has("sub_type") ? json.get("sub_type").asText() : "";

        return switch (noticeType) {
            case "group_upload" -> {
                String fileName = json.path("file").path("name").asText("");
                yield sj("群文件上传", selfId, "群:" + groupId, "用户:" + userId, "文件:" + fileName);
            }
            case "group_admin" -> switch (sub) {
                case "set" -> sj("设置管理员", selfId, "群:" + groupId, "用户:" + userId);
                case "unset" -> sj("取消管理员", selfId, "群:" + groupId, "用户:" + userId);
                default -> sj("群管理变动", selfId, "群:" + groupId, "用户:" + userId);
            };
            case "group_decrease" -> {
                long operatorId = json.has("operator_id") ? json.get("operator_id").asLong() : 0;
                String detail = switch (sub) {
                    case "leave" -> "退群";
                    case "kick" -> "被踢";
                    case "kick_me" -> "我被踢出";
                    default -> "减少";
                };
                yield sj("群成员" + detail, selfId, "群:" + groupId, "用户:" + userId, "操作者:" + operatorId);
            }
            case "group_increase" -> {
                long operatorId = json.has("operator_id") ? json.get("operator_id").asLong() : 0;
                String detail = "approve".equals(sub) ? "同意入群" : "invite".equals(sub) ? "邀请入群" : "增加";
                yield sj("群成员" + detail, selfId, "群:" + groupId, "用户:" + userId, "操作者:" + operatorId);
            }
            case "group_ban" -> {
                long duration = json.has("duration") ? json.get("duration").asLong() : 0;
                long operatorId = json.has("operator_id") ? json.get("operator_id").asLong() : 0;
                String detail = "ban".equals(sub) ? "禁言" : "lift_ban".equals(sub) ? "解除禁言" : "禁言变更";
                yield sj("群" + detail, selfId, "群:" + groupId, "用户:" + userId, "时长:" + duration + "s", "操作者:" + operatorId);
            }
            case "group_recall" -> {
                long messageId = json.has("message_id") ? json.get("message_id").asLong() : 0;
                yield sj("群消息撤回", selfId, "群:" + groupId, "用户:" + userId, "消息ID:" + messageId);
            }
            case "group_card" -> {
                String cardNew = json.has("card_new") ? json.get("card_new").asText() : "";
                yield sj("群名片变更", selfId, "群:" + groupId, "用户:" + userId, "新名片:" + cardNew);
            }
            case "group_msg_emoji_like" -> {
                long messageId = json.has("message_id") ? json.get("message_id").asLong() : 0;
                yield sj("群表情回应", selfId, "群:" + groupId, "消息ID:" + messageId);
            }
            case "essence" -> {
                long messageId = json.has("message_id") ? json.get("message_id").asLong() : 0;
                String detail = "add".equals(sub) ? "添加精华" : "delete".equals(sub) ? "删除精华" : "精华变更";
                yield sj("群" + detail, selfId, "群:" + groupId, "消息ID:" + messageId);
            }
            case "friend_add" ->
                    sj("好友添加", selfId, "新好友:" + userId);
            case "friend_recall" -> {
                long messageId = json.has("message_id") ? json.get("message_id").asLong() : 0;
                yield sj("好友消息撤回", selfId, "好友:" + userId, "消息ID:" + messageId);
            }
            default -> sj("通知:" + noticeType, selfId, "群:" + groupId, "用户:" + userId);
        };
    }

    // ============================================================
    //  请求事件
    // ============================================================

    private String buildRequestDesc(JsonNode json, long selfId) {
        String reqType = json.has("request_type") ? json.get("request_type").asText() : "";
        long userId = json.has("user_id") ? json.get("user_id").asLong() : 0;
        String comment = json.has("comment") ? json.get("comment").asText() : "";

        if ("friend".equals(reqType)) {
            return sj("好友请求", selfId, "请求者:" + userId, "验证信息:" + comment);
        }
        if ("group".equals(reqType)) {
            long groupId = json.has("group_id") ? json.get("group_id").asLong() : 0;
            String sub = json.has("sub_type") ? json.get("sub_type").asText() : "";
            String detail = "add".equals(sub) ? "加群申请" : "invite".equals(sub) ? "邀请入群" : "群请求";
            return sj(detail, selfId, "群:" + groupId, "请求者:" + userId, "验证信息:" + comment);
        }
        return null;
    }

    /**
     * 拼接日志描述字符串。
     * 格式：{事件名} Bot:{selfId} | key1 | key2 | ...
     */
    private String sj(String eventName, long selfId, String... details) {
        StringBuilder sb = new StringBuilder(eventName);
        sb.append(" Bot:").append(selfId);
        for (String d : details) {
            if (d != null && !d.isEmpty()) {
                sb.append(" | ").append(d);
            }
        }
        return sb.toString();
    }
}
