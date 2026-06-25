package com.github.zhygtx.napcat.api.response.extra;

import java.util.List;
import lombok.Data;

/**
 * 获取群根目录文件列表
 * <p>
 */
@Data
public class GroupRootFilesData {

    /** 文件列表 */
    private List<String> files;

    /** 文件夹列表 */
    private List<String> folders;

}