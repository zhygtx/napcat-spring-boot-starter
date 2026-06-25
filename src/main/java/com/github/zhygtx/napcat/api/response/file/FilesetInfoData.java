package com.github.zhygtx.napcat.api.response.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * 获取文件集信息
 * <p>
 */
@Data
public class FilesetInfoData {

    @JsonProperty("fileset_id")
    private String filesetId;

    @JsonProperty("file_list")
    private List<String> fileList;

}