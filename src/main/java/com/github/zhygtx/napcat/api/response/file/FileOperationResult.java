package com.github.zhygtx.napcat.api.response.file;

import lombok.Data;

/**
 * 群文件操作结果响应数据。
 * <p>
 * 对应 <code>/rename_group_file</code>、<code>/move_group_file</code>、
 * <code>/trans_group_file</code> 等群文件操作的 data 字段。
 * <pre>
 * {
 *   "ok": true
 * }
 * </pre>
 */
@Data
public class FileOperationResult {

    /** 是否操作成功 */
    private boolean ok;
}
