package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * <p>
 */
@Data
public class CollectionCollectionSearchListItemData {

    private String cid;

    private Long type;

    private Long status;

    private CollectionCollectionSearchListItemAuthorData author;

    private Long bid;

    private Long category;

    private String createTime;

    private String collectTime;

    private String modifyTime;

    private String sequence;

    private String shareUrl;

    private Long customGroupId;

    private Boolean securityBeat;

    private CollectionCollectionSearchListItemSummaryData summary;

}