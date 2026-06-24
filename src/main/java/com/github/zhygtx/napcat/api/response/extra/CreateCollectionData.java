package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * 创建收藏响应数据。
 * <p>
 * 对应 <code>/create_collection</code> 的 data 字段。
 */
@Data
public class CreateCollectionData {

    /** 收藏 ID */
    private String collectionId;

    /** 创建时间 */
    private long createTime;
}
