package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 收藏列表响应数据。
 * <p>
 * 对应 <code>/get_collection_list</code> 的 data 字段。
 */
@Data
public class CollectionListData {

    /** 收藏列表 */
    private JsonNode collectionList;

    /** 是否还有更多 */
    private boolean hasMore;

    /** 下一页起始位置 */
    private String nextStartPos;
}
