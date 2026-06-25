package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * 获取群相册媒体列表
 * <p>
 */
@Data
public class GroupAlbumMediaData {

    @JsonProperty("media_list")
    private List<GroupAlbumMediaItemData> mediaList;

}