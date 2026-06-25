package com.github.zhygtx.napcat.api.response.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取文件集 ID
 * <p>
 */
@Data
public class FilesetIdData {

    /** 文件集 ID */
    @JsonProperty("fileset_id")
    private String filesetId;

}