package com.github.zhygtx.napcat.api.response.system;

import java.util.List;
import lombok.Data;

/**
 * <p>
 */
@Data
public class CollectionCollectionSearchListItemSummaryRichMediaSummaryData {

    private String title;

    private String subTitle;

    private String brief;

    private List<String> picList;

    private Long contentType;

    private String originalUri;

    private String publisher;

    private Long richMediaVersion;

}