package com.github.zhygtx.napcat.api.response.extra;

import lombok.Data;

/**
 * 在线文件消息响应数据。
 * <p>
 * 对应 <code>/get_online_file_msg</code> 的 data 字段。
 */
@Data
public class OnlineFileMsgData {

    /** 文件名称 */
    private String fileName;

    /** 文件大小（字节） */
    private long fileSize;

    /** 文件 ID */
    private String fileId;

    /** 发送者 QQ */
    private long senderUid;

    /** 是否在线文件 */
    private boolean isOnlineFile;
}
