package cn.vpclub.shm.shcmcc.consumer.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 查询公众号下用户列表响应 bean
 * Created by nice on 2017/9/21.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserListResponse extends WeChatBaseResponse {
    private String total;
    private String count;
    private QueryUserListResponseData data;
    @JsonProperty(value = "next_openid")
    private String nextOpenid;//此次查询最后一个 openid 的值 可作为下次请求的 next_openid
}
