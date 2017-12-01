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
public class Article extends BaseAbstractParameter {
    private static final long serialVersionUID = 1L;

    @NotNull(when={ValidatorConditionType.READ},message = "文章ID不能为空")
    private Long id;
    /**
     * 文章标题
     */
    @ApiModelProperty("文章标题")
    private String title;
    /**
     * 文章摘要
     */
    @ApiModelProperty("文章摘要")
    private String summary;
    /**
     * 作者
     */
    @ApiModelProperty("作者")
    private String author;
    /**
     * 原文地址
     */
    @ApiModelProperty("原文地址")
    private String oldAddress;
    /**
     * 文章内容
     */
    @ApiModelProperty("文章内容")
    private String content;
    /**
     * 文章类型(1:所有:2:内部员工)
     */
    @ApiModelProperty("文章类型(1:公开:2:内部员工)")
    private Integer type;
    /**
     * 文章状态(1:草稿;2:发布)
     */
    @ApiModelProperty("文章状态(1:草稿;2:发布)")
    private Integer status;
    /**
     * 企业封面
     */
    @ApiModelProperty("企业封面")
    private String vpImg;
    /**
     * 微信封面
     */
    @ApiModelProperty("微信封面")
    private String weChatImg;
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
