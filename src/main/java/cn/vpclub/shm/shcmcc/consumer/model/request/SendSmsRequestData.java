package cn.vpclub.shm.shcmcc.consumer.model.request;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import lombok.*;

/**
 * Created by nice on 2017/9/25.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsRequestData extends BaseAbstractParameter {
    private String content;//所要发送的内容
    private String msisdn;//所要发送的电话号码
}
