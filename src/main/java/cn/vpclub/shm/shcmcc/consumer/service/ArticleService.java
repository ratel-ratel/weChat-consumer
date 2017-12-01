package cn.vpclub.shm.shcmcc.consumer.service;

import cn.vpclub.moses.utils.common.StringUtil;
import cn.vpclub.shm.shcmcc.consumer.entity.Employee;
import cn.vpclub.shm.shcmcc.consumer.entity.User;
import cn.vpclub.shm.shcmcc.consumer.model.enums.DataTypeEnum;
import cn.vpclub.shm.shcmcc.consumer.model.request.EmployeePageParam;
import cn.vpclub.shm.shcmcc.consumer.rpc.ArticleRpcService;
import cn.vpclub.shm.shcmcc.consumer.model.request.ArticlePageParam;;
import cn.vpclub.shm.shcmcc.consumer.entity.Article;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageDataResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.AttributeValidatorException;
import cn.vpclub.shm.shcmcc.consumer.rpc.EmployeeRpcService;
import cn.vpclub.shm.shcmcc.consumer.rpc.UserRpcService;
import cn.vpclub.shm.shcmcc.consumer.util.UserUtil;
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
public class ArticleService {

    private ArticleRpcService articleRpcService;

    private UserRpcService userRpcService;

    private EmployeeRpcService employeeRpcService;

    private UserUtil userUtil;

    public BaseResponse add(Article request) {
        BaseResponse response;
        //业务操作
        log.info("add Article " + request);
        try {
            request.validate(ValidatorConditionType.CREATE);
            response = articleRpcService.add(request);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public BaseResponse update(Article request) {
        BaseResponse response;
        //业务操作
        log.info("update Article " + request);
        try {
            request.validate(ValidatorConditionType.UPDATE);
            response = articleRpcService.update(request);
            log.debug("update Article back " + response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 文章查询
     *
     * @param request
     * @return
     */
    public BaseResponse query(Article request) {
        BaseResponse response;
        //业务操作
        log.info("文章查询请求参数: {}", request);
        try {
            request.validate(ValidatorConditionType.READ);
            response = articleRpcService.query(request);

        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
        }
        log.info("文章查询返回结果: {}" + response);
        return response;
    }

    public BaseResponse delete(Article request) {
        BaseResponse response;
        //业务操作
        log.info("delete Article " + request);
        try {
            request.validate(ValidatorConditionType.DELETE);
            response = articleRpcService.delete(request);
            log.debug("delete Article back " + response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public PageDataResponse list(ArticlePageParam request) {
        PageDataResponse response;
        //业务操作
        log.info("Article list method request " + request);
        if (null == request) {
            response = BackResponseUtil.getPageDataResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            log.info("Article list method request " + request);
            response = articleRpcService.list(request);
        }
        return response;
    }

    /**
     * 文章分页查询
     *
     * @param request
     * @return
     */
    public PageResponse page(ArticlePageParam request) {
        PageResponse response;
        //业务操作
        log.info("文章分页查询请求参数: {} ", request);
        if (null == request || StringUtil.isEmpty(request.getOpenId())) {
            response = BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            User user = new User();
            user.setOpenId(request.getOpenId());
            BaseResponse<User> baseResponse = userUtil.queryUserFromDatabaseOrWaChat(user);
            if(null != baseResponse && ReturnCodeEnum.CODE_1000.getCode().equals(baseResponse.getReturnCode())){
                //判定是否是内部员工
                EmployeePageParam employeePageParam = new EmployeePageParam();
                user = baseResponse.getDataInfo();
                if(StringUtil.isEmpty(user.getMobile())){
                    request.setType(DataTypeEnum.TYPE_PUBLIC.getCode());
                }else{
                    employeePageParam.setNameOrMobile(user.getMobile());
                    log.info("员工查询请求参数: {}", employeePageParam);
                    PageResponse<Employee> pageResponse = employeeRpcService.page(employeePageParam);
                    log.info("员工查询返回结果: {}", pageResponse);
                    if (ReturnCodeEnum.CODE_1000.getCode().equals(pageResponse.getReturnCode())) {
                        //表示是内部员工
                        request.setType(DataTypeEnum.TYPE_PRIVATE.getCode());
                    } else {
                        request.setType(DataTypeEnum.TYPE_PUBLIC.getCode());
                    }
                }
            }else{
                request.setType(DataTypeEnum.TYPE_PUBLIC.getCode());
            }
        }
        request.setDeleted(DataTypeEnum.DELETE_ONLINE.getCode());
        request.setStatus(DataTypeEnum.STATUS_RELEASE.getCode());//查看已发布的
        response = articleRpcService.page(request);
        log.info("文章分页查询返回结果: {} " + response.getReturnCode());
        return response;
    }
}
