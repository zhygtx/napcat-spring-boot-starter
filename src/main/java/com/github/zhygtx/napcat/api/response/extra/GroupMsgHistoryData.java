package com.github.zhygtx.napcat.api.response.extra;

import java.util.List;
import lombok.Data;

/**
 * 获取群历史消息
 * <p>
 */
@Data
public class GroupMsgHistoryData {

    /** 消息列表 */
    private List<String> messages;

}