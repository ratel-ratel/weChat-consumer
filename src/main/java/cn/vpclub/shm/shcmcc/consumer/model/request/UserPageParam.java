package cn.vpclub.shm.shcmcc.consumer.model.request;

import cn.vpclub.moses.core.model.request.PageBaseSearchParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 分页查询请求包装类
 * </p>
 *
 * @author yinxicheng
 * @since 2017-09-18
 */
@Getter
@Setter
@ToString
public class UserPageParam extends PageBaseSearchParam {
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 删除标识  1:在线 2:删除
     */
    private Integer deleted;
    /**
     * 是否关注 1:已关注；2:未关注
     */
    private Integer follow;
    /**
     * 是否黑名单 1:在；2:不在
     */
    private Integer blackList;
    /**
     * * 手机号
     */
    private String mobile;
    /**
     * 微信openid
     */
    private String openId;
}
