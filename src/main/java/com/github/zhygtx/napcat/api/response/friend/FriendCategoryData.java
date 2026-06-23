package com.github.zhygtx.napcat.api.response.friend;

import lombok.Data;
import java.util.List;

/**
 * 好友分类响应数据。
 * <p>
 * 对应 <code>/get_friends_with_category</code> 的 data 字段。
 */
@Data
public class FriendCategoryData {

    /** 分组 ID */
    private int categoryId;

    /** 分组名称 */
    private String categoryName;

    /** 分组排序 */
    private int categorySortId;

    /** 该分组下的好友列表 */
    private List<FriendListData> buddyList;
}
