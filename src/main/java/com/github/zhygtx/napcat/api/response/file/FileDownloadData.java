package com.github.zhygtx.napcat.api.response.file;

import lombok.Data;

/**
 * 文件下载响应数据。
 * <p>
 * 对应 <code>/download_file</code> 的 data 字段。
 */
@Data
public class FileDownloadData {

    /** 下载后的本地文件路径 */
    private String file;
}
