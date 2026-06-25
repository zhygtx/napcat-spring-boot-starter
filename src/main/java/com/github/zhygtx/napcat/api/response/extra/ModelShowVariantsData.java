package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 */
@Data
public class ModelShowVariantsData {

    /** 显示名称 */
    @JsonProperty("model_show")
    private String modelShow;

    /** 是否需要付费 */
    @JsonProperty("need_pay")
    private Boolean needPay;

}