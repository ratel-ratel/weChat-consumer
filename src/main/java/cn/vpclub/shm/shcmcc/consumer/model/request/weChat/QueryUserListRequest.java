package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 查询公众号下用户列表请求 bean
 * Created by nice on 2017/9/21.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserListRequest extends BaseAbstractParameter {
    @JsonProperty(value = "next_openid")
    private String nextOpenid; //第一个拉取的OPENID，不填默认从头开始拉取
}
