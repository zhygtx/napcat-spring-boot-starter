package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 获取收藏列表
 * <p>
 */
@Data
public class CollectionData {

    private Long errCode;

    private String errMsg;

    private CollectionCollectionSearchListData collectionSearchList;

}