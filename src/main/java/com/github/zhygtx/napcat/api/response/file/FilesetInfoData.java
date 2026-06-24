package com.github.zhygtx.napcat.api.response.file;

import lombok.Data;

/**
 * 文件集信息响应数据。
 * <p>
 * 对应 <code>/get_fileset_info</code> 的 data 字段。
 */
@Data
public class FilesetInfoData {

    /** 文件集 ID */
    private String filesetId;

    /** 文件集名称 */
    private String name;

    /** 文件集内文件总数 */
    private int totalFileCount;

    /** 文件集总大小（字节） */
    private long totalFileSize;

    /** 创建时间 */
    private long createTime;

    /** 创建者 QQ */
    private long creatorId;

    /** 创建者昵称 */
    private String creatorName;
}
