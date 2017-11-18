package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import lombok.*;

/**
 * 批量获取用户信息
 * Created by nice on 2017/9/21.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BatchUserQueryRequestData extends BaseAbstractParameter {
    private String openid;//用户唯一标识
}
