package com.github.zhygtx.napcat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * NapCat SDK 配置属性。
 * <p>
 * 对应 {@code application.yml} 中 {@code napcat.*} 前缀下的所有配置项。
 * <p>
 * 配置示例：
 * <pre>{@code
 * napcat:
 *   ws:
 *     server:
 *       enable: true
 *       url: "/ws/bot"
 *     timeout: 30
 *     heartbeat-timeout: 60
 *     heartbeat-min-interval: 5
 *     max-text-buffer-size: 1048576
 *     max-binary-buffer-size: 1048576
 *     enable-token: false
 *     token-value: ""
 *
 *   task-pool:
 *     core-pool-size: 50
 *     max-pool-size: 100
 *     queue-capacity: 5000
 *     keep-alive-time: 60
 *     thread-name-prefix: "NapCatTaskPool-"
 * }</pre>
 */
@ConfigurationProperties(prefix = "napcat")
@Data
public class NapCatProperties {

    /** WebSocket 服务配置 */
    private Ws ws = new Ws();

    /** 任务线程池配置 */
    private TaskPool taskPool = new TaskPool();

    /** API 调用配置 */
    private Api api = new Api();

    /** 日志配置 */
    private Log log = new Log();

    // ============================================================
    // WebSocket 配置
    // ============================================================

    @Data
    public static class Ws {

        /** 服务器端点配置 */
        private Server server = new Server();

        /** 连接超时时间（秒），默认 30 */
        private int timeout = 30;

        /** 心跳超时时间（秒），超过此时间未收到心跳视为连接断开，默认 60 */
        private int heartbeatTimeout = 60;

        /** 心跳最小间隔（秒），小于此间隔的高频心跳会被丢弃，默认 5 */
        private int heartbeatMinInterval = 5;

        /** 最大文本消息缓冲区大小（字节），默认 1MB */
        private int maxTextBufferSize = 1_048_576;

        /** 最大二进制消息缓冲区大小（字节），默认 1MB */
        private int maxBinaryBufferSize = 1_048_576;

        /** 是否启用密钥鉴权 */
        private boolean enableToken = false;

        /** 静态 token 值（单 bot 场景），留空则使用动态注册 */
        private String tokenValue;
    }

    // ============================================================
    // WebSocket 服务器端点配置
    // ============================================================

    @Data
    public static class Server {

        /** 是否启用 WebSocket 服务 */
        private boolean enable = true;

        /** WebSocket 端点路径 */
        private String url = "/ws/bot";
    }

    // ============================================================
    // 任务线程池配置
    // ============================================================

    @Data
    public static class TaskPool {

        /** 核心线程数 */
        private int corePoolSize = 50;

        /** 任务队列容量（超过此容量将触发 CallerRunsPolicy 反压） */
        private int queueCapacity = 5000;

        /** 空闲线程存活时间（秒） */
        private int keepAliveTime = 60;

        /** 最大线程数 */
        private int maxPoolSize = 100;

        /** 线程名称前缀 */
        private String threadNamePrefix = "NapCatTaskPool-";
    }

    // ============================================================
    // API 配置
    // ============================================================

    @Data
    public static class Api {

        /** API 调用超时时间（秒），默认 30 */
        private int timeout = 30;

        /** 最大待处理请求数，超过时新请求会被拒绝，默认 500 */
        private int maxPending = 500;
    }

    // ============================================================
    // 日志配置
    // ============================================================

    @Data
    public static class Log{
        /** 是否忽略心跳日志（默认 true，心跳消息不输出到日志） */
        private boolean ignoreHeartbeatLog = true;

        /** 日志中消息内容最大长度，超过此长度截断，默认 500 */
        private int logMaxLength = 500;
    }
}
