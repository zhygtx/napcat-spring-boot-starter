package com.github.zhygtx.napcat.api.response.file;

import lombok.Data;

/**
 * 文件集 ID 响应数据。
 * <p>
 * 对应 <code>/get_fileset_id</code> 的 data 字段。
 * <pre>
 * {
 *   "fileset_id": "..."
 * }
 * </pre>
 */
@Data
public class FilesetIdData {

    /** 文件集 ID */
    private String filesetId;
}
