package cn.vpclub.shm.shcmcc.consumer.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * query黑名单列表的响应 bean
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryBlackListResponse extends WeChatBaseResponse {
    private String total;//总数
    private String count;
    private QueryBlackListData data;
    @JsonProperty(value = "next_openid")
    private String nextOpenid;//下一个查询黑名单的 begin_openid
}
