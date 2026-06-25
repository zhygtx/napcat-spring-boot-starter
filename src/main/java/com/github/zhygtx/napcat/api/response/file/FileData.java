package com.github.zhygtx.napcat.api.response.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取文件
 * <p>
 */
@Data
public class FileData {

    /** 本地路径 */
    private String file;

    /** 下载URL */
    private String url;

    /** 文件大小 */
    @JsonProperty("file_size")
    private String fileSize;

    /** 文件名 */
    @JsonProperty("file_name")
    private String fileName;

    /** Base64编码 */
    private String base64;

}