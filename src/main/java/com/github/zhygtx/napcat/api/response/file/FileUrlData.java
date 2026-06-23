package com.github.zhygtx.napcat.api.response.file;

import lombok.Data;

/**
 * 文件 URL 响应数据。
 * <p>
 * 对应 <code>/get_group_file_url</code>、<code>/get_private_file_url</code> 的 data 字段。
 */
@Data
public class FileUrlData {

    /** 文件下载 URL */
    private String url;
}
