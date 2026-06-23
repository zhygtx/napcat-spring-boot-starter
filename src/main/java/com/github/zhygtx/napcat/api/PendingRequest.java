package com.github.zhygtx.napcat.api;

import com.fasterxml.jackson.databind.JavaType;
import com.github.zhygtx.napcat.api.response.ApiResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;

/**
 * 待处理的 API 请求封装。
 * <p>
 * 保存一个请求的 CompletableFuture（回执到达时 complete）、
 * 超时兜底任务（超时后 completeExceptionally）以及响应 data 的期望 Java 类型。
 *
 * @param future      响应值 CompletableFuture
 * @param timeoutTask 超时兜底任务（可取消）
 * @param action      对应 API 的 action 名称（用于日志）
 * @param dataType    响应 data 字段的期望 Java 类型
 */
public record PendingRequest(CompletableFuture<ApiResponse<?>> future, ScheduledFuture<?> timeoutTask, String action,
                             JavaType dataType) {
}