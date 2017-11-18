package cn.vpclub.shm.shcmcc.consumer.model.request;

import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import cn.vpclub.moses.utils.validator.annotations.NotEmpty;
import lombok.*;

import java.io.Serializable;

/**
 * 发送短信的请求参数
 * Created by nice on 2017/11/8.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsparam extends BaseAbstractParameter {
    @NotEmpty(when = {ValidatorConditionType.CREATE},message = "mobile 手机号码不能为空")
    private String mobile;
    private String content;
}
