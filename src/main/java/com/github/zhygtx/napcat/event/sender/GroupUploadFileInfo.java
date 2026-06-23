package com.github.zhygtx.napcat.event.sender;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import lombok.Data;
/**
 * 群文件上传事件中的文件信息。
 * <p>
 * 字段说明：
 * <ul>
 *   <li>id — 文件 ID</li>
 *   <li>name — 文件名</li>
 *   <li>size — 文件大小（字节）</li>
 *   <li>busid — 文件总线 ID</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupUploadFileInfo {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("size")
    private long size;

    @JsonProperty("busid")
    private long busid;
}
