package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * 获取群相册列表
 * <p>
 */
@Data
public class QunAlbumData {

    /** 群相册列表 */
    @JsonProperty("album_list")
    private List<String> albumList;

    /** 分页附加信息，传入下一次请求以获取更多数据 */
    @JsonProperty("attach_info")
    private String attachInfo;

    /** 是否有更多数据 */
    @JsonProperty("has_more")
    private Boolean hasMore;

}