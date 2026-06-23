package com.github.zhygtx.napcat.api.response.group;

import lombok.Data;

/**
 * &#064;全体剩余次数响应数据。
 * <p>
 * 对应 <code>/get_group_at_all_remain</code> 的 data 字段。
 */
@Data
public class GroupAtAllRemainData {

    /** 是否可以 @全体 */
    private boolean canAtAll;

    /** 群内 @全体剩余次数 */
    private int remainAtAllCountForGroup;

    /** 当前 Uin @全体剩余次数 */
    private int remainAtAllCountForUin;
}
