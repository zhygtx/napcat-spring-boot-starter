package com.github.zhygtx.napcat.api.response.extra;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import lombok.Data;

/**
 * 获取群荣誉信息
 * <p>
 */
@Data
public class GroupHonorInfoData {

    /** 群号 */
    @JsonProperty("group_id")
    private Long groupId;

    /** 当前龙王 */
    @JsonProperty("current_talkative")
    private JsonNode currentTalkative;

    /** 龙王列表 */
    @JsonProperty("talkative_list")
    private List<String> talkativeList;

    /** 群聊之火列表 */
    @JsonProperty("performer_list")
    private List<String> performerList;

    /** 群聊炽热列表 */
    @JsonProperty("legend_list")
    private List<String> legendList;

    /** 快乐源泉列表 */
    @JsonProperty("emotion_list")
    private List<String> emotionList;

    /** 冒尖小春笋列表 */
    @JsonProperty("strong_newbie_list")
    private List<String> strongNewbieList;

}