package cn.vpclub.shm.shcmcc.consumer.model.request;

import cn.vpclub.moses.core.model.request.PageBaseSearchParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页查询请求包装类
 * Created by dell on 2017/9/22.
 */
@Getter
@Setter
@ToString
public class EmployeePageParam extends PageBaseSearchParam {

    /**
     * 名称或手机号码（模糊查询）
     */
    private  String nameOrMobile;

    /**
     * 是否关注
     */
    private Integer follow;
}
