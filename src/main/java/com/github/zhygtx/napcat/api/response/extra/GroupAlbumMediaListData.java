package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * 群相册媒体列表响应数据。
 * <p>
 * 对应 <code>/get_group_album_media_list</code> 的 data 字段。
 */
@Data
public class GroupAlbumMediaListData {

    /** 媒体列表 */
    private Object mediaList;

    /** 翻页附加信息 */
    private String attachInfo;

    /** 是否还有更多 */
    private boolean hasMore;
}
