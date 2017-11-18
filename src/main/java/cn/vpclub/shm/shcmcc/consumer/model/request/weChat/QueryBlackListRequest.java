package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * query黑名单列表的请求 bean
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryBlackListRequest extends BaseAbstractParameter {
    @JsonProperty(value = "begin_openid")
    private String beginOpenid;
}
