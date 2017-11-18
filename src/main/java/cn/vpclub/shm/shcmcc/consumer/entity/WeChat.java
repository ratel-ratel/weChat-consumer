package cn.vpclub.shm.shcmcc.consumer.entity;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by nice on 2017/9/19.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
public class WeChat extends BaseAbstractParameter {
    @JsonProperty(value = "grant_type")
    @Value("${weChat.grantType}")
    private String grantType;//固定值 client_credential
    @Value("${weChat.appId}")
    private String appId;//用户唯一凭证
    @Value("${weChat.secret}")
    private String secret;//用户唯一凭证密钥
}
