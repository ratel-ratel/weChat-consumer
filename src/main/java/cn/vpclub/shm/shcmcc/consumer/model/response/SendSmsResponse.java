package cn.vpclub.shm.shcmcc.consumer.model.response;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 发送短信响应 bean
 * Created by nice on 2017/9/25.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsResponse extends BaseAbstractParameter {
    @JsonProperty(value = "code")
    private String returnCode;//returnCode
    @JsonProperty(value = "msg")
    private String message;//message
    @JsonProperty(value = "transationid")
    private String transactionId;//请求流水
    private String timestamp;//响应时间戳 yyyyMMddHHmmss 20161101162530
    @JsonProperty(value = "randomstr")
    private String randomString;
    private String sign;//数据签名
    private String body;

}
