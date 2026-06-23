package com.github.zhygtx.napcat.api;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.zhygtx.napcat.api.response.ApiResponse;
import com.github.zhygtx.napcat.api.response.VoidData;
import com.github.zhygtx.napcat.config.NapCatProperties;
import com.github.zhygtx.napcat.session.Bot;
import com.github.zhygtx.napcat.session.BotSessionRegistry;
import com.github.zhygtx.napcat.util.NapCatObjectMapper;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * NapCat API 请求引擎。
 * <p>
 * 负责底层 WebSocket 请求发送、响应匹配、超时管理、发送锁等核心能力。
 * 所有业务 API 方法已迁移到 {@link NapCat}（通过接口分离）。本类仅保留引擎层方法。
 * <p>
 * 关键职责：
 * <ul>
 *   <li>{@link #sendRequest(long, String, ObjectNode, JavaType)} — 构建并发送请求，注册 echo 映射</li>
 *   <li>{@link #handleResponse(JsonNode)} — 接收 NapCat 响应，匹配 echo 完成 future</li>
 *   <li>{@link #cancelPendingRequestsForBot(Long)} — Bot 离线时清理其所有 pending 请求</li>
 * </ul>
 */
@Service
public class NapCatApiClient {

    private static final Logger log = LoggerFactory.getLogger(NapCatApiClient.class);

    private final ConcurrentHashMap<String, PendingRequest> pendingRequests = new ConcurrentHashMap<>();
    private final AtomicLong echoCounter = new AtomicLong(0);
    private final ScheduledExecutorService timeoutScheduler = Executors.newSingleThreadScheduledExecutor(
            r -> {
                Thread t = new Thread(r, "NapCatApiTimeout-");
                t.setDaemon(true);
                return t;
            });

    private final NapCatObjectMapper mapper;
    private final BotSessionRegistry botRegistry;
    private final NapCatProperties properties;

    public NapCatApiClient(NapCatObjectMapper mapper,
                           BotSessionRegistry botRegistry,
                           NapCatProperties properties) {
        this.mapper = mapper;
        this.botRegistry = botRegistry;
        this.properties = properties;
    }

    // ==================== 公共入口 ====================

    /**
     * 处理 NapCat 返回的 API 响应。
     * <p>
     * 由 {@code NapCatWebSocketHandler} 在收到包含 {@code echo} 字段的消息时调用。
     * 根据 echo 匹配待处理请求，反序列化 data 后 complete 对应的 future。
     */
    public void handleResponse(JsonNode json) {
        String echo = json.has("echo") ? json.get("echo").asText() : null;
        if (echo == null) return;

        PendingRequest pr = pendingRequests.remove(echo);
        if (pr == null) {
            log.warn("收到未知 echo 的 API 响应: {}", echo);
            return;
        }

        pr.timeoutTask().cancel(false);

        try {
            ApiResponse<Object> response = new ApiResponse<>();
            response.setStatus(json.has("status") ? json.get("status").asText() : null);
            response.setRetcode(json.has("retcode") ? json.get("retcode").asInt() : -1);
            response.setMessage(json.has("message") ? json.get("message").asText() : null);
            response.setWording(json.has("wording") ? json.get("wording").asText() : null);

            JsonNode dataNode = json.get("data");
            if (dataNode != null && !dataNode.isNull()) {
                JavaType dataType = pr.dataType();
                if (dataType != null && !dataType.getRawClass().equals(VoidData.class)) {
                    response.setData(mapper.readValue(dataNode.traverse(), dataType));
                } else if (dataNode.isObject()) {
                    response.setData(mapper.treeToValue(dataNode, Object.class));
                }
            }

            CompletableFuture<ApiResponse<?>> future = pr.future();
            future.complete(response);

            log.debug("[API响应] Echo: {} | Action: {} | Status: {} | Retcode: {}",
                    echo, pr.action(), response.getStatus(), response.getRetcode());

        } catch (Exception e) {
            pr.future().completeExceptionally(
                    new RuntimeException("API 响应反序列化失败: " + pr.action(), e));
        }
    }

    /**
     * 取消指定 Bot 的所有待处理请求。
     * <p>
     * 当 Bot 的 WebSocket 连接断开时调用，让所有等待的调用方立即收到异常。
     */
    public void cancelPendingRequestsForBot(Long botQQ) {
        if (botQQ == null) return;

        int count = 0;
        String prefix = botQQ + "|";
        for (String echo : pendingRequests.keySet()) {
            if (echo.startsWith(prefix)) {
                PendingRequest pr = pendingRequests.remove(echo);
                if (pr != null) {
                    pr.timeoutTask().cancel(false);
                    pr.future().completeExceptionally(new BotOfflineException(botQQ));
                    count++;
                }
            }
        }
        if (count > 0) {
            log.info("Bot [{}] 离线，已取消 {} 个待处理 API 请求", botQQ, count);
        }
    }

    // ==================== 引擎方法（package-private，供 NapCat 调用） ====================

    /**
     * 发送 API 请求的核心方法。
     * <p>
     * 构建 JSON 请求 → 生成 echo → 注册 PendingRequest → 加锁发送 → 返回 future。
     */
    <T> CompletableFuture<ApiResponse<T>> sendRequest(
            long botQQ, String action, ObjectNode params, JavaType dataType) {

        Bot bot = botRegistry.getBot(botQQ);
        if (bot == null) {
            return CompletableFuture.failedFuture(new BotOfflineException(botQQ));
        }

        if (pendingRequests.size() >= properties.getApi().getMaxPending()) {
            return CompletableFuture.failedFuture(
                    new IllegalStateException("待处理请求数已达上限 (" + properties.getApi().getMaxPending() + ")，请求被拒绝"));
        }

        String echo = botQQ + "|" + echoCounter.incrementAndGet();
        ObjectNode requestJson = mapper.createObjectNode();
        requestJson.put("action", action);
        requestJson.set("params", params);
        requestJson.put("echo", echo);
        String jsonStr = requestJson.toString();

        CompletableFuture<ApiResponse<T>> future = new CompletableFuture<>();
        ScheduledFuture<?> timeoutTask = timeoutScheduler.schedule(() -> {
            PendingRequest pr = pendingRequests.remove(echo);
            if (pr != null) {
                future.completeExceptionally(new TimeoutException("API 调用超时: " + action + " (echo=" + echo + ")"));
            }
        }, properties.getApi().getTimeout(), TimeUnit.SECONDS);

        @SuppressWarnings("unchecked")
        CompletableFuture<ApiResponse<?>> wildcardFuture = (CompletableFuture<ApiResponse<?>>) (CompletableFuture<?>) future;
        pendingRequests.put(echo, new PendingRequest(wildcardFuture, timeoutTask, action, dataType));

        synchronized (bot.getSendLock()) {
            try {
                bot.getSession().sendMessage(new TextMessage(jsonStr));
                log.debug("[API调用] Bot: {} | Action: {} | Echo: {}", botQQ, action, echo);
            } catch (IOException e) {
                pendingRequests.remove(echo);
                timeoutTask.cancel(false);
                return CompletableFuture.failedFuture(
                        new RuntimeException("发送 API 请求失败: " + action, e));
            }
        }
        return future;
    }

    /** 获取简单类型的 JavaType */
    JavaType type(Class<?> clazz) {
        return mapper.getTypeFactory().constructType(clazz);
    }

    /** 获取 List 类型的 JavaType */
    JavaType listType(Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(List.class, elementClass);
    }

    // ==================== 资源清理 ====================

    @PreDestroy
    public void destroy() {
        log.info("正在关闭 NapCatApiClient 超时调度器...");
        timeoutScheduler.shutdown();
        try {
            if (!timeoutScheduler.awaitTermination(3, TimeUnit.SECONDS)) {
                timeoutScheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            timeoutScheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }

        int remaining = pendingRequests.size();
        if (remaining > 0) {
            log.warn("NapCatApiClient 关闭，取消 {} 个待处理请求", remaining);
            pendingRequests.forEach((echo, pr) -> {
                pr.timeoutTask().cancel(false);
                pr.future().completeExceptionally(
                        new IllegalStateException("NapCatApiClient 已关闭"));
            });
            pendingRequests.clear();
        }
    }
}
