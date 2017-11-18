package cn.vpclub.shm.shcmcc.consumer.service;

import cn.vpclub.moses.utils.common.StringUtil;
import cn.vpclub.shm.shcmcc.consumer.enums.RoleEnum;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.shm.shcmcc.consumer.rpc.RoleRpcService;
import cn.vpclub.shm.shcmcc.consumer.model.request.RolePageParam;;
import cn.vpclub.shm.shcmcc.consumer.entity.Role;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageDataResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.AttributeValidatorException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * service业务层
 * </p>
 *
 * @author yinxicheng
 * @since 2017-09-18
 */
@Slf4j
@Service
@AllArgsConstructor
public class RoleService {

    private RoleRpcService roleRpcService;

    public BaseResponse add(Role request) {
        BaseResponse response;

        log.info("add Role request:" + JsonUtil.objectToJson(request));
        try {
            //非空校验
            request.validate(ValidatorConditionType.CREATE);

            //添加创建时间  更新时间
            long currTime = System.currentTimeMillis();
            request.setUpdatedTime(currTime);
            request.setCreatedTime(currTime);

            //将创建人赋值给更新人
            request.setUpdatedBy(request.getCreatedBy());

            //添加表示位selected设置为1 上线
            request.setDeleted(RoleEnum.UN_DELETED.getCode());

            response = roleRpcService.add(request);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        log.info("add Role response:  " + JsonUtil.objectToJson(response));
        return response;
    }

    public BaseResponse update(Role request) {
        BaseResponse response;
        /*
            更新操作，创建时间，创建人不会修改
            是否上线不会修改
            更改字段：角色名称，更新操作员，备注，更新人
         */
        //获得更新时间（系统当前时间）
        long timeMillis = System.currentTimeMillis();
        request.setUpdatedTime(timeMillis);

        log.info("update Role request:" + JsonUtil.objectToJson(request));
        try {
            /*
                非空校验
                1  remark非空：更新操作，remark（备注），数据库要求非空，而且
                客户更新信息里面很可能是 角色名称，更新操作员，备注三项
                2  updateBy 创建人 非空，是数据库要求，应该是入参传的
             */
            request.validate(ValidatorConditionType.UPDATE);

            //更新
            response = roleRpcService.update(request);
            log.debug("update Role back " + response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        log.info("update Role response:" + JsonUtil.objectToJson(response));
        return response;
    }

    public BaseResponse query(Role request) {
        BaseResponse response;
        //业务操作
        log.info("query Role request:" + JsonUtil.objectToJson(request));
        try {
            //非空校验
            request.validate(ValidatorConditionType.READ);

            //查询
            response = roleRpcService.query(request);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        log.info("query Role request:" + JsonUtil.objectToJson(response));
        return response;
    }

    public BaseResponse delete(Role request) {
        BaseResponse response;
        log.info("delete Role request:" + JsonUtil.objectToJson(request));
        try {
            //非空校验
            request.validate(ValidatorConditionType.DELETE);

            /*
                进行逻辑删除，将selected设置为2
                创建一个新对象，只赋值id和状态（selected），需要用枚举替换硬代码2
             */
            Role deletedRole = new Role();
            deletedRole.setId(request.getId());
            deletedRole.setDeleted(RoleEnum.IS_DELETED.getCode());

            //逻辑删除
            response = roleRpcService.delete(deletedRole);
            log.debug("delete Role back " + response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        log.info("delete Role response:" + JsonUtil.objectToJson(response));
        return response;
    }

    public PageDataResponse list(RolePageParam request) {
        PageDataResponse response;
        //业务操作
        log.info("Role list method request: " + JsonUtil.objectToJson(request));
        if (null == request) {
            response = BackResponseUtil.getPageDataResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            response = roleRpcService.list(request);
        }
        log.info("Role list method response :" + JsonUtil.objectToJson(response));
        return response;
    }

    public PageResponse page(RolePageParam request) {
        PageResponse response;
        log.info("page Role method request: " + JsonUtil.objectToJson(request));
        try {

            //校验 pageSize pageNumber 不为空
            request.validate(ValidatorConditionType.LIST);
            response = roleRpcService.page(request);
        } catch (AttributeValidatorException e) {
            e.printStackTrace();
            response = BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        log.info("page Role method response: " + JsonUtil.objectToJson(response));
        return response;
    }
}
