package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * 机型展示响应数据。
 * <p>
 * 对应 <code>/_get_model_show</code> 的 data 数组条目。
 */
@Data
public class ModelShowData {

    /** 机型名称 */
    private String model;

    /** 机型展示 */
    private String modelShow;
}
