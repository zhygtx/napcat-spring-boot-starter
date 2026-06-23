package com.github.zhygtx.napcat.api.response.system;

import lombok.Data;

/**
 * 分享链接响应数据。
 * <p>
 * 对应 <code>/get_share_link</code> 的 data 字段。
 */
@Data
public class ShareLinkData {

    /** 短链接 */
    private String url;

    /** 标题 */
    private String title;

    /** 内容 */
    private String content;
}
