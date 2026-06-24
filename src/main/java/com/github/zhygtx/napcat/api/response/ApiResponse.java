package com.github.zhygtx.napcat.api.response;

import lombok.Data;

/**
 * OneBot API 通用响应包装。
 * <p>
 * 对应 NapCat 响应的顶层 JSON 结构：
 * <pre>
 * {
 *   "status": "ok",
 *   "retcode": 0,
 *   "data": { ... },
 *   "message": "",
 *   "wording": "",
 *   "stream": "normal-action"
 * }
 * </pre>
 *
 * @param <T> data 字段的具体类型
 */
@Data
public class ApiResponse<T> {

    /** 状态："ok" 或 "failed" */
    private String status;

    /** 返回码：0 表示成功，非零表示错误 */
    private int retcode;

    /** 业务数据，类型由泛型决定 */
    private T data;

    /** 错误消息 */
    private String message;

    /** 提示 */
    private String wording;

    /** 流式响应类型（如 "normal-action"） */
    private String stream;

    /**
     * 判断 API 调用是否成功。
     *
     * @return true 表示成功（status 为 "ok"）
     */
    @SuppressWarnings("unused")
    public boolean isOk() {
        return "ok".equals(status);
    }
}
