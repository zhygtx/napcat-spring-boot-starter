package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * 群相册列表响应数据。
 * <p>
 * 对应 <code>/get_qun_album_list</code> 的 data 字段。
 * <pre>
 * {
 *   "album_list": [ ... ],
 *   "attach_info": "...",
 *   "has_more": false
 * }
 * </pre>
 */
@Data
public class QunAlbumListData {

    /** 相册列表 */
    private Object albumList;

    /** 翻页附加信息 */
    private String attachInfo;

    /** 是否还有更多 */
    private boolean hasMore;
}
