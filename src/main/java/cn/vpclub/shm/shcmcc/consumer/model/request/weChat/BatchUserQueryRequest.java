package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * 批量获取用户信息 请求 bean
 * Created by nice on 2017/9/21.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BatchUserQueryRequest extends BaseAbstractParameter {
    @JsonProperty(value = "user_list")
    private List<BatchUserQueryRequestData> userList;
}
