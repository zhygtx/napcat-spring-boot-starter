package com.github.zhygtx.napcat.api.response.friend;

import java.util.List;
import lombok.Data;

/**
 * <p>
 */
@Data
public class FriendsWithCategoryData {

    /** 分组ID */
    private Long categoryId;

    /** 分组名称 */
    private String categoryName;

    /** 分组内好友数量 */
    private Integer categoryMbCount;

    /** 好友列表 */
    private List<String> buddyList;

}