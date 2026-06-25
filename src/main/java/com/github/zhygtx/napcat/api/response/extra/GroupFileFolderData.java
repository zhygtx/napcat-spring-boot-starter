package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 创建群文件目录
 * <p>
 */
@Data
public class GroupFileFolderData {

    /** 操作结果 */
    private JsonNode result;

    /** 群项信息 */
    private JsonNode groupItem;

}