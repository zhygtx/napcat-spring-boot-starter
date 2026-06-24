package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 用户在线状态响应数据。
 * <p>
 * 对应 <code>/nc_get_user_status</code> 的 data 字段。
 * <pre>
 * {
 *   "status": 10,
 *   "ext_status": 0
 * }
 * </pre>
 */
@Data
public class UserStatusData {

    /** 在线状态 */
    private int status;

    /** 扩展状态 */
    private int extStatus;
}
