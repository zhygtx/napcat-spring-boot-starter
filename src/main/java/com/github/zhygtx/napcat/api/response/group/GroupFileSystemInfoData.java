package com.github.zhygtx.napcat.api.response.group;

import lombok.Data;

/**
 * 群文件系统信息响应数据。
 * <p>
 * 对应 <code>/get_group_file_system_info</code> 的 data 字段。
 */
@Data
public class GroupFileSystemInfoData {

    /** 文件总数 */
    private int fileCount;

    /** 文件数量上限 */
    private int limitCount;

    /** 已用空间（字节） */
    private long usedSpace;

    /** 总空间（字节） */
    private long totalSpace;
}
