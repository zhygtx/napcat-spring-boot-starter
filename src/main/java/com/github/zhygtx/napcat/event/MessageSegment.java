package com.github.zhygtx.napcat.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

/**
 * OneBot v11 消息段。
 * <p>
 * 每条消息由若干消息段组成，每个消息段包含 {@code type} 和 {@code data} 两部分。
 * <p>
 * 常见类型：
 * <ul>
 *   <li>text — 纯文本，data 含 {@code text} 字段</li>
 *   <li>image — 图片，data 含 {@code file}、{@code url} 字段</li>
 *   <li>at — @某人，data 含 {@code qq} 字段</li>
 *   <li>face — QQ 表情，data 含 {@code id} 字段</li>
 * </ul>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageSegment {

    /** 段落类型（如 text、image、at、face、reply 等） */
    private String type;

    /** 各类型对应的数据内容 */
    private Map<String, Object> data;
}
