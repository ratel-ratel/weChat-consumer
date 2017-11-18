package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 获取标签下粉丝列表 请求bean
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryOpenidListByTagRequest extends BaseAbstractParameter {
    @JsonProperty(value = "tagid")
    private String tagId;
    @JsonProperty(value = "next_openid")
    private String nextOpenid;//从哪个 openid 开始
}
