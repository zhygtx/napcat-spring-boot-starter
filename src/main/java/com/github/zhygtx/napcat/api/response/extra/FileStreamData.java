package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 上传文件流
 * <p>
 */
@Data
public class FileStreamData {

    private String type;

    @JsonProperty("stream_id")
    private String streamId;

    private String status;

    @JsonProperty("received_chunks")
    private Long receivedChunks;

    @JsonProperty("total_chunks")
    private Long totalChunks;

}