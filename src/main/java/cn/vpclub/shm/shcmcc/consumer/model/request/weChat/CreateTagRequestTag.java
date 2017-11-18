package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import lombok.*;

/**
 * 创建标签请求 bean
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateTagRequestTag extends BaseAbstractParameter {
    private String name;
}
