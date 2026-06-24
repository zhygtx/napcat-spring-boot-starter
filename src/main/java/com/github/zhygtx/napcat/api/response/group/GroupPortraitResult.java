package com.github.zhygtx.napcat.api.response.group;

import lombok.Data;

/**
 * 群头像设置结果响应数据。
 * <p>
 * 对应 <code>/set_group_portrait</code> 的 data 字段。
 * <pre>
 * {
 *   "result": 0,
 *   "errMsg": ""
 * }
 * </pre>
 */
@Data
public class GroupPortraitResult {

    /** 结果码（0 表示成功） */
    private int result;

    /** 错误信息 */
    private String errMsg;
}
