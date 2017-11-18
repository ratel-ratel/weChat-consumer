package cn.vpclub.shm.shcmcc.consumer.model.response;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 微信接口 通用响应 ben
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WeChatBaseResponse extends BaseAbstractParameter {
    @JsonProperty(value = "errcode")
    public String returnCode;
    @JsonProperty(value = "errmsg")
    public String message;
}
