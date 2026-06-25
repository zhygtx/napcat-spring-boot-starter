package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 上传群文件
 * <p>
 */
@Data
public class GroupFileData {

    /** 文件 ID */
    @JsonProperty("file_id")
    private String fileId;

}