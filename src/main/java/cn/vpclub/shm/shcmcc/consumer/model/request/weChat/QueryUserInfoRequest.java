package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import lombok.*;

/**
 * 查询用户信息
 * Created by nice on 2017/9/21.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserInfoRequest extends BaseAbstractParameter {
    private String openid;
}
