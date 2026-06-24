package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * 自定义表情条目响应数据。
 * <p>
 * 对应 <code>/fetch_custom_face</code> 的 data 数组条目。
 */
@Data
public class CustomFaceData {

    /** 表情 ID */
    private String emojiId;

    /** 表情包 ID */
    private int packageId;

    /** 表情名称 */
    private String emojiName;

    /** 表情文件路径 */
    private String filePath;

    /** 是否为动态表情 */
    private boolean isAnimation;

    /** 表情宽度 */
    private int width;

    /** 表情高度 */
    private int height;
}
