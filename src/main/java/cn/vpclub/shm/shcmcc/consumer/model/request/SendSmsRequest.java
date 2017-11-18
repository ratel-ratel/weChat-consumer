package cn.vpclub.shm.shcmcc.consumer.model.request;

import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import cn.vpclub.moses.utils.validator.annotations.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

/**
 * 发送短信的请求参数
 * Created by nice on 2017/9/25.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsRequest extends BaseAbstractParameter {
    @Value("${SendSms.appId}")
    @NotEmpty(when = {ValidatorConditionType.CREATE},message = "appid 不能为空")
    @JsonProperty(value = "appid")
    private String appId;//平台 id
    @Value("${SendSms.ability}")
    @NotEmpty(when = {ValidatorConditionType.CREATE},message = "ability 不能为空")
    private String ability;//能力编码
    /**
     * //能力编码20位APPID+YYYYMMDDHHMISS+8 位数字序列
     * （此序列由集团客户自主生成，比如从00000001 开始递增等等），
     * transationid样例：201610311205321235962014101615303080000
     */
    @NotEmpty(when = {ValidatorConditionType.CREATE},message = "transationid 不能为空")
    @JsonProperty(value = "transationid")
    private String transactionId;
    @NotEmpty(when = {ValidatorConditionType.CREATE},message = "timestamp 不能为空")
    private String timestamp;//当前时间戳
    @NotEmpty(when = {ValidatorConditionType.CREATE},message = "randomstr 不能为空")
    @JsonProperty(value = "randomstr")
    private String randomString;//32位随机字符串
    @NotEmpty(when = {ValidatorConditionType.CREATE},message = "sign 不能为空")
    private String sign;
    @NotEmpty(when = {ValidatorConditionType.CREATE},message = "body 不能为空")
    private String body;
}
