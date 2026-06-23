package com.github.zhygtx.napcat.api.response.group;

import lombok.Data;
import java.util.List;

/**
 * 群根目录文件列表响应数据。
 * <p>
 * 对应 <code>/get_group_root_files</code> 和 <code>/get_group_files_by_folder</code> 的 data 字段。
 */
@Data
public class GroupRootFilesData {

    /** 文件夹列表 */
    private List<GroupFolderItem> folders;

    /** 文件列表 */
    private List<GroupFileItem> files;

    @Data
    public static class GroupFolderItem {

        /** 文件夹 ID */
        private String folderId;

        /** 文件夹名 */
        private String folderName;

        /** 创建时间 */
        private long createTime;

        /** 创建者 QQ */
        private long creatorUserId;

        /** 创建者昵称 */
        private String creatorNickname;

        /** 文件夹内文件数 */
        private int totalFileCount;
    }

    @Data
    public static class GroupFileItem {

        /** 文件 ID */
        private String fileId;

        /** 文件名 */
        private String fileName;

        /** busid */
        private long busid;

        /** 文件大小 */
        private long fileSize;

        /** 上传时间 */
        private long uploadTime;

        /** 过期时间 */
        private long deadTime;

        /** 修改时间 */
        private long modifyTime;

        /** 下载次数 */
        private int downloadTimes;

        /** 上传者 QQ */
        private long uploaderUserId;

        /** 上传者昵称 */
        private String uploaderNickname;
    }
}
