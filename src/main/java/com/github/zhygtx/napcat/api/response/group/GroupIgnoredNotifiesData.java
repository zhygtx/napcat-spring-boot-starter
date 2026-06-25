package com.github.zhygtx.napcat.api.response.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * 获取群忽略通知
 * <p>
 */
@Data
public class GroupIgnoredNotifiesData {

    /** 邀请请求列表 */
    @JsonProperty("invited_requests")
    private List<String> invitedRequests;

    /** 邀请请求列表 */
    private List<String> InvitedRequest;

    /** 加入请求列表 */
    @JsonProperty("join_requests")
    private List<String> joinRequests;

}