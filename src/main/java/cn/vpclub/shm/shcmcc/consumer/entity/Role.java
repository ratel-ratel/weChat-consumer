package cn.vpclub.shm.shcmcc.consumer.entity;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.annotations.NotEmpty;
import cn.vpclub.moses.utils.validator.annotations.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author yinxicheng
 * @since 2017-09-18
 */

@Getter
@Setter
@ToString
@ApiModel("-ModelAndView控制")
public class Role extends BaseAbstractParameter {
    private static final long serialVersionUID = 1L;

    @NotEmpty(when = {ValidatorConditionType.READ,ValidatorConditionType.UPDATE,ValidatorConditionType.DELETE})
	private Long id;
    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
	private String name;
    /**
     * 更新操作员
     */
    @ApiModelProperty("更新操作员")
	private String updateName;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
	private String remark;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
	private Long createdTime;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @NotEmpty(when = {ValidatorConditionType.CREATE})
	private Long createdBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
	private Long updatedTime;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    @NotEmpty(when = {ValidatorConditionType.UPDATE})
	private Long updatedBy;
    /**
     * 删除标识 1 在线 2 删除
     */
    @ApiModelProperty("删除标识 1 在线 2 删除")
	private Integer deleted;


}
