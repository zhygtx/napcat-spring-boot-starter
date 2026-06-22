package com.github.zhygtx.napcat;

/**
 * NapCat SDK 常量定义。
 * <p>
 * 统一定义握手 attributes key、请求头名称等跨组件使用的常量，
 * 避免字符串字面量重复。
 */
public final class NapCatConstants {

    private NapCatConstants() {
        // 工具类禁止实例化
    }

    /** WebSocket 握手 attributes 中存储 BotQQ 的 key */
    public static final String ATTR_BOT_QQ = "bot_qq";

    /** WebSocket 握手 attributes 中存储路径后缀的 key */
    public static final String ATTR_PATH_SUFFIX = "bot_path_suffix";

    /** WebSocket 握手 attributes 中存储 token 的 key */
    public static final String ATTR_TOKEN = "bot_token";

    /** NapCat 客户端携带的自定义请求头：Bot QQ 号 */
    public static final String HEADER_X_SELF_ID = "x-self-id";

    /** HTTP Authorization 请求头名称 */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    /** Bearer token 前缀 */
    public static final String BEARER_PREFIX = "Bearer ";
}
