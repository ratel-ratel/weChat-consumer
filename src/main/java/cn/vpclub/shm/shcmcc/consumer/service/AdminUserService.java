package cn.vpclub.shm.shcmcc.consumer.service;

import cn.vpclub.shm.shcmcc.consumer.rpc.AdminUserRpcService;
import cn.vpclub.shm.shcmcc.consumer.model.request.AdminUserPageParam;;
import cn.vpclub.shm.shcmcc.consumer.entity.AdminUser;

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
 *  service业务层
 * </p>
 *
 * @author yinxicheng
 * @since 2017-09-18
 */
@Slf4j
@Service
@AllArgsConstructor
public class AdminUserService {

    private AdminUserRpcService adminUserRpcService;

    public BaseResponse add(AdminUser request) {
        BaseResponse response;
        //业务操作
        log.info("add AdminUser " + request);
        try {
            request.validate(ValidatorConditionType.CREATE);
            response = adminUserRpcService.add(request);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public BaseResponse update(AdminUser request) {
        BaseResponse response;
        //业务操作
        log.info("update AdminUser " + request);
        try {
            request.validate(ValidatorConditionType.UPDATE);
            response = adminUserRpcService.update(request);
            log.debug("update AdminUser back " + response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public BaseResponse query(AdminUser request) {
        BaseResponse response;
        //业务操作
        log.info("query AdminUser " + request);
        try {
            request.validate(ValidatorConditionType.READ);
            response = adminUserRpcService.query(request);
            log.info("query AdminUser back" + response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public BaseResponse delete(AdminUser request) {
        BaseResponse response;
        //业务操作
        log.info("delete AdminUser " + request);
        try {
            request.validate(ValidatorConditionType.DELETE);
            response = adminUserRpcService.delete(request);
            log.debug("delete AdminUser back " + response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public PageDataResponse list(AdminUserPageParam request) {
        PageDataResponse response;
        //业务操作
        log.info("AdminUser list method request " + request);
        if (null == request) {
            response = BackResponseUtil.getPageDataResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            log.info("AdminUser list method request " + request);
            response = adminUserRpcService.list(request);
        }
        return response;
    }
    public PageResponse page(AdminUserPageParam request) {
        PageResponse response;
        //业务操作
        log.info("AdminUser page method request " + request);
        if (null == request) {
            response = BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            log.info("AdminUser page method request " + request);
            response = adminUserRpcService.page(request);
        }
        return response;
    }
}
