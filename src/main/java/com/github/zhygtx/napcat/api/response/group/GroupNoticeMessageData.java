package com.github.zhygtx.napcat.api.response.group;

import java.util.List;
import lombok.Data;

/**
 * <p>
 */
@Data
public class GroupNoticeMessageData {

    /** 文本内容 */
    private String text;

    /** 图片列表 */
    private List<String> image;

    /** 图片列表 */
    private List<String> images;

}