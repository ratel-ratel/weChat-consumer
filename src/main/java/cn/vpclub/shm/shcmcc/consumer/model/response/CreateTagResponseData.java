package cn.vpclub.shm.shcmcc.consumer.model.response;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import lombok.*;

/**
 * 创建标签响应 data
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateTagResponseData extends BaseAbstractParameter {
    private String id;//标签 id
    private String name;//标签名
}
