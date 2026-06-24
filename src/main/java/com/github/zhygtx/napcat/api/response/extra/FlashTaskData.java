package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * 闪传任务响应数据。
 * <p>
 * 对应 <code>/create_flash_task</code> 的 data 字段。
 * <pre>
 * {
 *   "task_id": "task_123"
 * }
 * </pre>
 */
@Data
public class FlashTaskData {

    /** 任务 ID */
    private String taskId;
}
