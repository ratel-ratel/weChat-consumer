package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import lombok.*;

/**
 * 编辑标签
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTagRequest extends BaseAbstractParameter {
    private UpdateTagRequestData tag;
}
