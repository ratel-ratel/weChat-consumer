package cn.vpclub.shm.shcmcc.consumer.model.request;

import cn.vpclub.moses.core.model.request.PageBaseSearchParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 *  分页查询请求包装类
 * </p>
 *
 * @author yinxicheng
 * @since 2017-09-18
 */
@Getter
@Setter
@ToString
public class ArticlePageParam extends PageBaseSearchParam{

    /**
     * 微信openid
     */
    private String openId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 用户类型
     */
    private Integer type;
    /**
     * 文章状态
     */
    private Integer status;
    /**
     *删除标识  1:在线 2:删除
     */
    private Integer deleted;
}
