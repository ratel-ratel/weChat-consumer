package cn.vpclub.shm.shcmcc.consumer.entity;

import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import cn.vpclub.moses.utils.constant.ValidatorConditionType;
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
public class AdminUser extends BaseAbstractParameter {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    @NotNull(when = {ValidatorConditionType.READ,ValidatorConditionType.UPDATE,ValidatorConditionType.DELETE})
	private Long id;
    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
	private String name;
    /**
     * 登录名
     */
    @ApiModelProperty("登录名")
	private String loginName;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
	private String password;
    /**
     * 状态
     */
    @ApiModelProperty("状态")
	private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
	private Long createdTime;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
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
	private Long updatedBy;
    /**
     * 删除标识  1:在线 2:删除
     */
    @ApiModelProperty("删除标识  1:在线 2:删除")
	private Integer deleted;


}
