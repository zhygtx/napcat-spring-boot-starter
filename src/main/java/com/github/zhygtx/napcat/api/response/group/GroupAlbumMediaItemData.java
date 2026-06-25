package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 */
@Data
public class GroupAlbumMediaItemData {

    @JsonProperty("media_id")
    private String mediaId;

    private String url;

}