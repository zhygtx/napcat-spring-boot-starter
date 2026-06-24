package com.github.zhygtx.napcat.api.response.file;

import lombok.Data;

/**
 * 文件信息响应数据。
 * <p>
 * 对应 <code>/get_image</code>、<code>/get_record</code>、<code>/get_file</code> 的 data 字段。
 */
@Data
public class FileData {

    /** 文件路径 */
    private String file;

    /** 文件名（可选） */
    private String fileName;

    /** 文件大小（可选） */
    private long fileSize;

    /** URL（可选） */
    private String url;

    /** Base64 编码内容（可选） */
    private String base64;
}
