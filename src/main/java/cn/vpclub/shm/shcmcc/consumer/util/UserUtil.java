package cn.vpclub.shm.shcmcc.consumer.util;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.utils.common.JsonUtil;

import cn.vpclub.shm.shcmcc.consumer.entity.User;
import cn.vpclub.shm.shcmcc.consumer.model.enums.RoleEnum;
import cn.vpclub.shm.shcmcc.consumer.model.enums.WeChatEnum;
import cn.vpclub.shm.shcmcc.consumer.model.enums.DataTypeEnum;
import cn.vpclub.shm.shcmcc.consumer.model.request.weChat.QueryUserInfoRequest;
import cn.vpclub.shm.shcmcc.consumer.model.response.QueryUserInfoResponse;
import cn.vpclub.shm.shcmcc.consumer.model.response.QueryUserListResponse;
import cn.vpclub.shm.shcmcc.consumer.rpc.UserRpcService;
import com.hazelcast.util.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nice on 2017/11/10.
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserUtil {
    private UserRpcService userRpcService;
    private WeChatUtil weChatUtil;

    /**
     * 判断用户是否关注并相应保存或更新
     *
     * @param user
     * @return
     */
    public BaseResponse queryUserFromDatabaseOrWaChat(User user) {
        BaseResponse<User> response;
        response = userRpcService.query(user);
        log.info("调用用户查询接口返回结果:  " + JsonUtil.objectToJson(response));
        //存在则更新，否则查询微信接口
        if (null != response && ReturnCodeEnum.CODE_1000.getCode().equals(response.getReturnCode())) {
            User userInfo = response.getDataInfo();
            if (user.getMobile().equals(userInfo.getMobile())) {
                response.setMessage(RoleEnum.CODE_2006.getValue());
            } else {
                //更新该条记录
                userInfo.setMobile(user.getMobile());
                response = userRpcService.update(userInfo);
                log.info("更新用户信息返回结果: {}" + JsonUtil.objectToJson(response));
            }
        } else {
            //判断是否为此公众号下用户
            QueryUserInfoRequest queryUserInfo = new QueryUserInfoRequest();
            queryUserInfo.setOpenid(user.getOpenId());
            QueryUserInfoResponse queryUserInfoResponse = weChatUtil.queryUserInfo(queryUserInfo);
            log.info("查询该用户信息请返回结果: {}", queryUserInfoResponse);
            if (null != queryUserInfoResponse && WeChatEnum.CODE_0.getCode().equals(queryUserInfoResponse.getReturnCode())) {
                //将用户信息添加至数据库
                copyProperties(user, queryUserInfoResponse);//对象赋值
                log.info("用户添加请求参数: {}", user);
                response = userRpcService.add(user);
                log.info("用户添加返回结果: {}", response.getReturnCode());
            } else {
                log.info("查询微信用户信息失败");
                response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
            }
        }
        return response;
    }

    //通过openId查询数据库或微信查询出用户信息(如果数据库中没有用户信息，微信订阅号下有就保存用户信息)
    public BaseResponse queryUserDatabaseOrWaChat(User user) {
        log.info("通过openId查询数据库或微信查询出用户信息请求参数  " + JsonUtil.objectToJson(user));
        BaseResponse response;
        response = userRpcService.query(user);
        log.info("调用用户查询接口返回结果:  " + JsonUtil.objectToJson(response));

        if (null != response && ReturnCodeEnum.CODE_1000.getCode().equals(response.getReturnCode()) && null != response.getDataInfo()) {
            return response;
        } else {
            //判断是否为此公众号下用户，并保存改用户信息
            QueryUserInfoRequest queryUserInfo = new QueryUserInfoRequest();
            queryUserInfo.setOpenid(user.getOpenId());
            QueryUserInfoResponse queryUserInfoResponse = weChatUtil.queryUserInfo(queryUserInfo);
            log.info("查询该用户信息请返回结果: {}", queryUserInfoResponse);
            //为关注状态
            if (null != queryUserInfoResponse && WeChatEnum.CODE_0.getCode().equals(queryUserInfoResponse.getReturnCode())
                    && queryUserInfoResponse.getSubscribe().equals(WeChatEnum.SUBSCRIBE.getCode())) {
                //将用户信息添加至数据库
                copyProperties(user, queryUserInfoResponse);//对象赋值
                log.info("用户添加请求参数  : " + JsonUtil.objectToJson(user));
                response = userRpcService.add(user);
                log.info("用户添加返回结果  : " + JsonUtil.objectToJson(response));
            } else {
                log.info("查询微信用户信息失败");
                response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
            }
        }
        log.info("通过openId查询数据库或微信查询出用户信息返回结果  " + JsonUtil.objectToJson(response));
        return response;
    }

    /**
     * 将QueryUserInfoResponse 中属性复制到 User ，
     *
     * @param user
     * @param response
     * @return
     */

    private User copyProperties(User user, QueryUserInfoResponse response) {
        log.info("copyProperties  request  User : " + JsonUtil.objectToJson(user) + " QueryUserInfoResponse : " + JsonUtil.objectToJson(response));
        if (null == user) {
            user = new User();
        }
        //给用户set属性
        user.setSubscribe(response.getSubscribe());
        user.setOpenId(response.getOpenid());
        user.setNickName(response.getNickName());
        Integer sex = null == response.getSex() ? 0 : Integer.parseInt(response.getSex());
        user.setSex(sex);
        user.setLanguage(response.getLanguage());
        user.setCity(response.getCity());
        user.setProvince(response.getProvince());
        user.setCountry(response.getCountry());
        user.setHeadImgUrl(response.getHeadImgUrl());
        user.setSubscribeTime(Long.parseLong(response.getSubscribeTime()));
        user.setUnionId(response.getUnionId());
        user.setRemark(response.getRemark());
        user.setGroupId(response.getGroupId());
        user.setBlackList(DataTypeEnum.NO_BLACKLIST.getCode());
        user.setFollow(DataTypeEnum.FOLLOW.getCode());
        user.setTagidList(JsonUtil.objectToJson(response.getTagIdList()));
        user.setBlackList(DataTypeEnum.NO_BLACKLIST.getCode());
        user.setCreatedBy(Long.parseLong(WeChatEnum.INSTALL_ORGID.getValue()));//初始化orgId
        user.setUpdatedBy(user.getCreatedBy());
        long currentTimeMillis = System.currentTimeMillis();
        user.setCreatedTime(currentTimeMillis);
        user.setUpdatedTime(currentTimeMillis);
        user.setDeleted(DataTypeEnum.DELETE_ONLINE.getCode());
        log.info("copyProperties  back  " + JsonUtil.objectToJson(user));
        return user;
    }
}
