package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * <p>
 */
@Data
public class CollectionCollectionSearchListItemSummaryData {

    private String textSummary;

    private String linkSummary;

    private String gallerySummary;

    private String audioSummary;

    private String videoSummary;

    private String fileSummary;

    private String locationSummary;

    private CollectionCollectionSearchListItemSummaryRichMediaSummaryData richMediaSummary;

}