package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * 将用户加入黑名单请求 bean
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlackListRequest extends BaseAbstractParameter {
    @JsonProperty(value = "openid_list")
    private List<String> openidList;
}
