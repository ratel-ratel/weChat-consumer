package cn.vpclub.shm.shcmcc.consumer.util;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.utils.common.JsonUtil;

import cn.vpclub.shm.shcmcc.consumer.entity.User;
import cn.vpclub.shm.shcmcc.consumer.enums.WeChatEnum;
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

    public BaseResponse queryUserFromDatabaseOrWaChat(User user) {
        log.info("queryUserFromDatabaseOrWaChat  request  " + JsonUtil.objectToJson(user));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse = userRpcService.query(user);
        log.info("userRpcService  query  " + JsonUtil.objectToJson(baseResponse));
        //如果在数据库中查询到了,直接返回
        if (null != baseResponse && ReturnCodeEnum.CODE_1000.getCode().equals(baseResponse.getReturnCode())) {
            return baseResponse;
        }
        //数据库中没有查询公众号下面的用户
        QueryUserListResponse users = weChatUtil.getUsers(null);
        if (null == users || null == users.getData() || CollectionUtil.isEmpty(users.getData().getOpenid())) {
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
            log.info("获取公众号下所有用户openId 失败");
            return baseResponse;
        }
        List<String> openid = users.getData().getOpenid();
        //判断 是否为此公众号下用户
        if (openid.contains(user.getOpenId())) {
            QueryUserInfoRequest queryUserInfo = new QueryUserInfoRequest();
            queryUserInfo.setOpenid(user.getOpenId());
            QueryUserInfoResponse queryUserInfoResponse = weChatUtil.queryUserInfo(queryUserInfo);
            if (null != queryUserInfoResponse && WeChatEnum.CODE_0.getCode().equals(queryUserInfoResponse.getReturnCode())) {
                User add = new User();
                add = copyProperties(add, queryUserInfoResponse);
                log.info("add  users   request " + JsonUtil.objectToJson(add));
                baseResponse = userRpcService.add(add);
                log.info("add  users   back " + JsonUtil.objectToJson(baseResponse));
                if (null == baseResponse || !ReturnCodeEnum.CODE_1000.getCode().equals(baseResponse.getReturnCode())) {
                    log.info("添加用户信息失败");
                    baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
                }
            } else {
                log.info("查询微信用户信息失败");
                baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
            }
        } else {
            log.info("此用户不是公众号下用户");
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
        }
        log.info("queryUserFromDatabaseOrWaChat  back  " + JsonUtil.objectToJson(baseResponse));
        return baseResponse;
    }

    private User copyProperties(User user, QueryUserInfoResponse response) {
        log.info("copyProperties  request  User : " + JsonUtil.objectToJson(user) + " QueryUserInfoResponse : " + JsonUtil.objectToJson(response));
        if (null == user) {
            user = new User();
        }
        //给用户set属性
        user.setSubscribe(response.getSubscribe());
        user.setOpenId(response.getOpenid());
        user.setNickName(response.getNickName());
        user.setSex(Integer.parseInt(response.getSex()));
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
