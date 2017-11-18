package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import cn.vpclub.moses.utils.validator.annotations.NotEmpty;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by nice on 2017/11/17.
 */

@Getter
@Setter
@ToString
public class News extends BaseAbstractParameter {
    @NotEmpty(when = {ValidatorConditionType.CREATE})
    private String touser;
    private String msgtype;
    private NewsInfo news;
}
