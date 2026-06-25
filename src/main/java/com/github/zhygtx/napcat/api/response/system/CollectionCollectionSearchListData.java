package com.github.zhygtx.napcat.api.response.system;

import java.util.List;
import lombok.Data;

/**
 * <p>
 */
@Data
public class CollectionCollectionSearchListData {

    private List<CollectionCollectionSearchListItemData> collectionItemList;

    private Boolean hasMore;

    private String bottomTimeStamp;

}