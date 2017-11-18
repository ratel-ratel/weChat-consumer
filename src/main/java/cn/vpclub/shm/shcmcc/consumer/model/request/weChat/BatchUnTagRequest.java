package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * 批量为用户取消标签 请求 bean
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BatchUnTagRequest extends BaseAbstractParameter {
    @JsonProperty(value = "openid_list")
    private List<String> openidList;//openid list
    @JsonProperty(value = "tagid")
    private String tagId;//标签 id
}
