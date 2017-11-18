package cn.vpclub.shm.shcmcc.consumer.model.response;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import lombok.*;

import java.util.List;

/**
 * 获取标签下粉丝列表 响应data
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryOpenidListByTagResponseData extends BaseAbstractParameter {
    private List<String> openid;
}
