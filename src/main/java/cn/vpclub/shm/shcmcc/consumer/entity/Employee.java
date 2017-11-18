package cn.vpclub.shm.shcmcc.consumer.entity;

import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import cn.vpclub.moses.utils.validator.annotations.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by dell on 2017/9/22.
 */
@Getter
@Setter
@ToString
public class Employee extends BaseAbstractParameter {
    private static final long serialVersionUID = 1L;

    @NotNull(when = {ValidatorConditionType.READ,ValidatorConditionType.UPDATE,ValidatorConditionType.DELETE})
    private Long id;
    /**
     * 员工名称
     */
    @NotNull(when={ValidatorConditionType.CREATE},message = "员工名称不能为空")
    private String name;
    /**
     * 手机号码
     */
    @NotNull(when={ValidatorConditionType.CREATE},message = "手机号不能为空")
    private String mobile;
    /**
     * 是否关注（1：关注；2：没有关注）
     */
    private Integer follow;
    /**
     * 创建人
     */
    @NotNull(when={ValidatorConditionType.CREATE},message = "创建人不能为空")
    private Long createdBy;
    /**
     * 创建时间
     */
    private Long createdTime;
    /**
     * 更新人
     */
    private Long updatedBy;
    /**
     * 更新时间
     */
    private Long updatedTime;
    /**
     *删除标识（1：在线；2：删除）
     */
    private Integer deleted;

}
