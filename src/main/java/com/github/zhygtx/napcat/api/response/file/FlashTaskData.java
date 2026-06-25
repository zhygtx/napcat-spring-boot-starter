package com.github.zhygtx.napcat.api.response.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 创建闪传任务
 * <p>
 */
@Data
public class FlashTaskData {

    @JsonProperty("task_id")
    private String taskId;

}