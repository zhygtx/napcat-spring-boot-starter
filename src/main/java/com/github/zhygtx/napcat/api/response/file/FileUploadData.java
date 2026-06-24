package com.github.zhygtx.napcat.api.response.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 文件上传响应数据。
 * <p>
 * 对应 <code>/upload_group_file</code>、<code>/upload_private_file</code> 的 data 字段。
 * <pre>
 * {
 *   "file_id": "file_uuid_123"
 * }
 * </pre>
 */
@Data
public class FileUploadData {

    /** 文件 ID */
    @JsonProperty("file_id")
    private String fileId;
}
