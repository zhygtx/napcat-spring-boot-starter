package com.github.zhygtx.napcat.event.notice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zhygtx.napcat.event.sender.GroupUploadFileInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 群文件上传通知。
 * <p>
 * 对应 {@code notice_type=group_upload}。
 * 当群内有文件上传时触发。
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupUploadNoticeEvent extends GroupNoticeEvent {

    /** 上传的文件信息 */
    @JsonProperty("file")
    private GroupUploadFileInfo file;
}
