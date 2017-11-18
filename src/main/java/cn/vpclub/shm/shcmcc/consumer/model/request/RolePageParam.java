package cn.vpclub.shm.shcmcc.consumer.model.request;

import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import cn.vpclub.moses.utils.validator.annotations.NotEmpty;
import lombok.*;

/**
 * <p>
 *  分页查询请求包装类
 * </p>
 *
 * @author yinxicheng
 * @since 2017-09-18
 */
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RolePageParam extends BaseAbstractParameter {

    /*
        每页大小
     */
    @NotEmpty(when = {ValidatorConditionType.LIST})
    private Integer pageSize;

    /*
        页数
     */
    @NotEmpty(when = {ValidatorConditionType.LIST})
    private Integer pageNumber;

    /*
        起始行
     */
    private Integer startRow;

    /*
        更新操作员
     */
    private String updateName;

    /*
        角色名称
     */
    private String name;
}
