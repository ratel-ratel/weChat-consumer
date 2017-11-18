package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import cn.vpclub.moses.utils.validator.annotations.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 发送文本消息请求类
 * Created by nice on 2017/11/17.
 */

@Getter
@Setter
@ToString
public class TextRequest implements Serializable {
    private String content;// 文本消息内容

}
