package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * 自定义表情详情响应数据。
 * <p>
 * 对应 <code>/fetch_custom_face_detail</code> 的 data 字段。
 */
@Data
public class CustomFaceDetailData {

    /** 表情 ID */
    private String emojiId;

    /** 表情名称 */
    private String emojiName;

    /** 表情描述 */
    private String emojiDesc;

    /** 表情包 ID */
    private int packageId;

    /** 文件大小 */
    private long fileSize;

    /** MD5 */
    private String md5;

    /** 是否为动态表情 */
    private boolean isAnimation;

    /** 宽度 */
    private int width;

    /** 高度 */
    private int height;
}
