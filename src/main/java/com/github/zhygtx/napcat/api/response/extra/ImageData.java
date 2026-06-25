package com.github.zhygtx.napcat.api.response.extra;

import java.util.List;
import lombok.Data;

/**
 * 图片 OCR 识别
 * <p>
 */
@Data
public class ImageData {

    private List<ImageItemData> texts;

}