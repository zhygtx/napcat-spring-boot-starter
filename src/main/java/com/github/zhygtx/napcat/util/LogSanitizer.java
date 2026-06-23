package com.github.zhygtx.napcat.util;

import com.github.zhygtx.napcat.config.NapCatProperties;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * 日志内容清洗工具。
 * <p>
 * 在日志输出前对消息内容进行脱敏和截断，防止敏感信息泄露。
 * <p>
 * 功能：
 * <ul>
 *   <li>替换敏感字段（token、password、secret 等）的值</li>
 *   <li>截断过长的消息内容</li>
 * </ul>
 */
@Component
public class LogSanitizer {

    /** 需要脱敏的 JSON 字段名列表 */
    private static final Pattern SENSITIVE_FIELD_PATTERN = Pattern.compile(
            "\"(token|password|secret|access_token|bot_token)\"\\s*:\\s*\"[^\"]+\"",
            Pattern.DOTALL
    );

    /** 脱敏替换模板 */
    private static final String SENSITIVE_REPLACEMENT = "\"$1\":\"***\"";

    private final int maxLength;

    public LogSanitizer(NapCatProperties properties) {
        this.maxLength = properties.getLog().getLogMaxLength();
    }

    /**
     * 清洗消息内容用于日志输出。
     * <p>
     * 处理流程：
     * <ol>
     *   <li>对敏感字段（token、password 等）的值进行脱敏替换</li>
     *   <li>截断超过 {@code logMaxLength} 的内容</li>
     * </ol>
     *
     * @param payload 原始消息内容
     * @return 清洗后的安全日志内容
     */
    public String sanitize(String payload) {
        if (payload == null || payload.isEmpty()) {
            return payload;
        }

        // 1. 脱敏敏感字段
        String result = SENSITIVE_FIELD_PATTERN.matcher(payload).replaceAll(SENSITIVE_REPLACEMENT);

        // 2. 截断
        if (result.length() > maxLength) {
            result = result.substring(0, maxLength) + " ... (已截断 " + (payload.length() - maxLength) + " 个字符)";
        }

        return result;
    }
}
