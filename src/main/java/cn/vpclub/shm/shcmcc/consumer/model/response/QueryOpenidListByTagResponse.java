package cn.vpclub.shm.shcmcc.consumer.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 获取标签下粉丝列表 响应bean
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryOpenidListByTagResponse extends WeChatBaseResponse {
    private String count;
    @JsonProperty(value = "next_openid")
    private String nextOpenid;
    private QueryOpenidListByTagResponseData data;
}
