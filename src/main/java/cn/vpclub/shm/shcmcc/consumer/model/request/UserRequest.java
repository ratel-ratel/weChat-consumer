package cn.vpclub.shm.shcmcc.consumer.model.request;

import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.BaseAbstractParameter;
import cn.vpclub.moses.utils.validator.annotations.NotEmpty;
import cn.vpclub.moses.utils.validator.annotations.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class UserRequest extends BaseAbstractParameter {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 绑定手机号
     */
    @ApiModelProperty("绑定手机号")
    @NotNull(when={ValidatorConditionType.UPDATE},message = "绑定手机号不能为空")
    private String mobile;
    /**
     * 微信openid
     */
    @ApiModelProperty("微信openid")
    @NotNull(when={ValidatorConditionType.UPDATE},message = "微信openid不能为空")
    private String openId;
    //图片验证码
    @NotEmpty(when={ValidatorConditionType.UPDATE},message = "图片验证码不能为空")
    private String validateCode;
    //短信验证码
    @NotEmpty(when={ValidatorConditionType.UPDATE},message = "短信验证码不能为空")
    private String smsCode;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickName;
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    @ApiModelProperty("用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
    private Integer sex;
    /**
     * 用户的语言，简体中文为zh_CN
     */
    @ApiModelProperty("用户的语言，简体中文为zh_CN")
    private String language;
    /**
     * 用户所在国家
     */
    @ApiModelProperty("用户所在国家")
    private String country;
    /**
     * 用户所在省份
     */
    @ApiModelProperty("用户所在省份")
    private String province;
    /**
     * 用户所在地区
     */
    @ApiModelProperty("用户所在地区")
    private String city;
    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
     */
    @ApiModelProperty("用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息")
    private String subscribe;
    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String headImgUrl;
    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    @ApiModelProperty("用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间")
    private Long subscribeTime;
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
     */
    @ApiModelProperty("只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段")
    private String unionId;
    /**
     * 公众号运营者对粉丝的备注
     */
    @ApiModelProperty("公众号运营者对粉丝的备注")
    private String remark;
    /**
     * 用户所在的分组ID（兼容旧的用户分组接口）
     */
    @ApiModelProperty("用户所在的分组ID（兼容旧的用户分组接口）")
    private String groupId;
    /**
     * 用户被打上的标签ID列表
     */
    @ApiModelProperty("用户被打上的标签ID列表")
    private String tagidList;
    /**
     * 用户角色id
     */
    @ApiModelProperty("用户角色id")
    private Long roleId;
    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;
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
    /**
     * 是否关注 1:已关注；2:未关注
     */
    @ApiModelProperty("是否关注 1:已关注；2:未关注")
    private Integer follow;
    /**
     * 是否黑名单 1:在；2:不在
     */
    @ApiModelProperty("是否黑名单 1:在；2:不在")
    private Integer blackList;

}
