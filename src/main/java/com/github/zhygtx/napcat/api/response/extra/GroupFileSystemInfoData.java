package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取群文件系统信息
 * <p>
 */
@Data
public class GroupFileSystemInfoData {

    /** 文件总数 */
    @JsonProperty("file_count")
    private Integer fileCount;

    /** 文件上限 */
    @JsonProperty("limit_count")
    private Integer limitCount;

    /** 已使用空间 */
    @JsonProperty("used_space")
    private Long usedSpace;

    /** 总空间 */
    @JsonProperty("total_space")
    private Long totalSpace;

}