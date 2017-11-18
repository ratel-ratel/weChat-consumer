package cn.vpclub.shm.shcmcc.consumer.model.response;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import lombok.*;

import java.util.List;

/**
 * query黑名单列表的响应data bean
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryBlackListData extends BaseAbstractParameter {
    private List<String> openid;//openid 列表
}
