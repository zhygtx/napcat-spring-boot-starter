package com.github.zhygtx.napcat.api.response.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 */
@Data
public class FlashFileData {

    @JsonProperty("file_name")
    private String fileName;

    private Integer size;

}