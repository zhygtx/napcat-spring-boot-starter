package com.github.zhygtx.napcat.event.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zhygtx.napcat.event.BaseEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * OneBot v11 请求事件基类。
 * <p>
 * 对应 {@code post_type=request}。
 * 请求事件需要用户回应，如好友请求、加群请求等。
 */
@Getter
@Setter
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class RequestEvent extends BaseEvent {

    @JsonProperty("request_type")
    private String requestType;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("flag")
    private String flag;
}
