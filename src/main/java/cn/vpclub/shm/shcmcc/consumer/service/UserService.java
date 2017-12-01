package cn.vpclub.shm.shcmcc.consumer.service;

import cn.vpclub.moses.utils.common.DateUtil;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.moses.utils.common.StringUtil;
import cn.vpclub.moses.utils.hazelcast.HCacheMapUtil;
import cn.vpclub.moses.utils.image.ValidateCode;
import cn.vpclub.shm.shcmcc.consumer.model.enums.NameSpaceConstant;
import cn.vpclub.shm.shcmcc.consumer.model.enums.RoleEnum;
import cn.vpclub.shm.shcmcc.consumer.model.request.UserRequest;
import cn.vpclub.shm.shcmcc.consumer.rpc.UserRpcService;
import cn.vpclub.shm.shcmcc.consumer.model.request.UserPageParam;;
import cn.vpclub.shm.shcmcc.consumer.entity.User;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageDataResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.validator.AttributeValidatorException;
import cn.vpclub.shm.shcmcc.consumer.util.UserUtil;
import com.hazelcast.core.HazelcastInstance;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.Date;

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
public class UserService {

    private UserRpcService userRpcService;
    private HazelcastInstance hazelcastInstance;
    private UserUtil userUtil;

    public BaseResponse add(User request) {
        BaseResponse response;
        //业务操作
        log.info("add User " + request);
        try {
            request.validate(ValidatorConditionType.CREATE);
            response = userRpcService.add(request);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public BaseResponse update(User request) {
        BaseResponse response;
        //业务操作
        log.info("update User " + request);
        try {
            request.validate(ValidatorConditionType.UPDATE);
            response = userRpcService.update(request);
            log.debug("update User back " + response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public BaseResponse query(User request) {
        BaseResponse response;
        //业务操作
        log.info("query User " + request);
        try {
            request.validate(ValidatorConditionType.READ);
            response = userRpcService.query(request);
            log.info("query User back" + response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public BaseResponse delete(User request) {
        BaseResponse response;
        //业务操作
        log.info("delete User " + request);
        try {
            request.validate(ValidatorConditionType.DELETE);
            response = userRpcService.delete(request);
            log.debug("delete User back " + response);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public PageDataResponse list(UserPageParam request) {
        PageDataResponse response;
        //业务操作
        log.info("User list method request " + request);
        if (null == request) {
            response = BackResponseUtil.getPageDataResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            log.info("User list method request " + request);
            response = userRpcService.list(request);
        }
        return response;
    }

    public PageResponse page(UserPageParam request) {
        PageResponse response;
        //业务操作
        log.info("User page method request " + request);
        if (null == request) {
            response = BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1006.getCode());
        } else {
            log.info("User page method request " + request);
            response = userRpcService.page(request);
        }
        return response;
    }

    /**
     * 微信用户绑定手机号
     *
     * @param request
     * @return
     */
    public BaseResponse bindMobile(UserRequest request) {
        BaseResponse response;
        //业务操作
        log.info("绑定手机号请求参数: {}", request);
        try {
            request.validate(ValidatorConditionType.UPDATE);
        } catch (AttributeValidatorException e) {
            log.info("请求参数不全");
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
            return response;
        }
        //校验图片验证码
        String validateCode = request.getValidateCode();
        response = validateCode(validateCode);
        log.info("图片验证码校验返回结果: {}", response.getReturnCode());
        if (null == response || !ReturnCodeEnum.CODE_1000.getCode().equals(response.getReturnCode())) {
            response.setReturnCode(ReturnCodeEnum.CODE_1005.getCode());
            response.setMessage(RoleEnum.CODE_2004.getValue());
            log.info("图片验证码校验失败");
            return response;
        }
        //验证通过则清除验证码缓存
        HCacheMapUtil.remove(hazelcastInstance, NameSpaceConstant.GRAPHIC_CODE, validateCode);
        //校验短信验证码
        response = validateSmsCode(request);
        log.info("短息验证码校验返回结果: {}", response.getReturnCode());
        if (null == response || !ReturnCodeEnum.CODE_1000.getCode().equals(response.getReturnCode())) {
            log.info("短息验证码校验失败");
            response.setReturnCode(ReturnCodeEnum.CODE_1005.getCode());
            response.setMessage(RoleEnum.CODE_2005.getValue());
            return response;
        }
        //验证通过,清除短信验证码缓存
        HCacheMapUtil.remove(hazelcastInstance, NameSpaceConstant.SEND_MESSAGE, request.getMobile());

        //通过 openId 查询出微信用户信息
        User user = new User();
        user.setOpenId(request.getOpenId());
        response = userUtil.queryUserDatabaseOrWaChat(user);
        if (null == response || !response.getReturnCode().equals(ReturnCodeEnum.CODE_1000.getCode()) || null == response.getDataInfo()) {
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
            response.setMessage(RoleEnum.CODE_2007.getValue());
        } else {
            User dataInfo = (User) response.getDataInfo();
            if (dataInfo.getMobile().equals(request.getMobile())) {
                //用户信息中手机号和要绑定的手机号一致
                response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
                response.setMessage(RoleEnum.CODE_2006.getValue());
            } else {
                //用户信息中手机号和要绑定的手机号不一致
                dataInfo.setMobile(request.getMobile());
                response = userRpcService.update(dataInfo);
            }
        }
        return response;
    }

    /**
     * 校验图片验证码
     *
     * @param code
     * @return
     */
    private BaseResponse validateCode(String code) {
        BaseResponse baseResponse = new BaseResponse();
        //缓存中获取的验证码
        log.debug("code is: {}", code);
        String hazeCode = HCacheMapUtil.get(hazelcastInstance, NameSpaceConstant.GRAPHIC_CODE, code, String.class);
        log.debug("hazeCode is: {}", hazeCode);
        Long hazeCodeTime = HCacheMapUtil.get(hazelcastInstance, NameSpaceConstant.GRAPHIC_CODE_TIME, code.toLowerCase(), Long.class);
        Long currentTime = System.currentTimeMillis();
        if (hazeCodeTime == null) {
            hazeCodeTime = currentTime;
        }
        if (StringUtil.isEmpty(code)) {
            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1006.getCode());
            baseResponse.setMessage(ReturnCodeEnum.CODE_1006.getValue());
            return baseResponse;
        }
//        if (currentTime - hazeCodeTime > 120000) {
//            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1006.getCode());
//            baseResponse.setMessage(RoleEnum.CODE_2003.getValue());
//            return baseResponse;
//        }
        if (code.equalsIgnoreCase(hazeCode)) {
            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
            baseResponse.setMessage(ReturnCodeEnum.CODE_1000.getValue());
        } else {
            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1005.getCode());
            baseResponse.setMessage(RoleEnum.CODE_2004.getValue());
        }
        return baseResponse;
    }

    /**
     * 校验短信验证码
     *
     * @param request
     * @return
     */
    private BaseResponse validateSmsCode(UserRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        //缓存中获取的短信验证码
        log.info("validateSmsCode request ", JsonUtil.objectToJson(request));
        String hazeCode = HCacheMapUtil.get(hazelcastInstance, NameSpaceConstant.SEND_MESSAGE, request.getMobile(), String.class);
        log.info("hazeCode is: {}", hazeCode);
        if (null == request || StringUtil.isEmpty(request.getSmsCode())) {
            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1006.getCode());
            baseResponse.setMessage(ReturnCodeEnum.CODE_1006.getValue());
            return baseResponse;
        }
        if (request.getSmsCode().equalsIgnoreCase(hazeCode)) {
            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
            baseResponse.setMessage(ReturnCodeEnum.CODE_1000.getValue());
        } else {
            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1005.getCode());
            baseResponse.setMessage(RoleEnum.CODE_2005.getValue());
        }
        return baseResponse;
    }

    /**
     * 生成图片验证码
     *
     * @param request
     * @param response
     */
    public void generateQRCode(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            String code = ValidateCode.random(4);
            log.info("生成的验证码code是:{}", code);
            HCacheMapUtil.put(hazelcastInstance, NameSpaceConstant.GRAPHIC_CODE, code, code, 1200);
            //放入到测试缓存中
            HCacheMapUtil.put(hazelcastInstance, NameSpaceConstant.GRAPHIC_CODE_DEBUG, code, DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"), 1200);
            HCacheMapUtil.put(hazelcastInstance, NameSpaceConstant.GRAPHIC_CODE_TIME, code, System.currentTimeMillis(), 1000);

            byte[] codeImg = ValidateCode.render(code, 100, 50);
            // 禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");

            // 将图像输出到Servlet输出流中。
            ServletOutputStream sos = response.getOutputStream();
            sos.write(codeImg);
            //ImageIO.write(codeImg, "jpeg", sos);
            sos.flush();
            sos.close();
        } catch (IOException e) {
            log.info("generateQRCode error : {}", e);
        }
    }
}
