package cn.vpclub.shm.shcmcc.consumer.web;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.utils.common.DateUtil;
import cn.vpclub.moses.utils.common.StringUtil;
import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.hazelcast.HCacheMapUtil;
import cn.vpclub.moses.utils.image.ValidateCode;
import cn.vpclub.moses.utils.validator.AttributeValidatorException;
import cn.vpclub.shm.shcmcc.consumer.enums.NameSpaceConstant;
import cn.vpclub.shm.shcmcc.consumer.enums.RoleEnum;
import cn.vpclub.shm.shcmcc.consumer.model.request.UserRequest;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageDataResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import cn.vpclub.shm.shcmcc.consumer.entity.User;
import cn.vpclub.shm.shcmcc.consumer.model.request.UserPageParam;
import cn.vpclub.shm.shcmcc.consumer.service.UserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yinxicheng
 * @since 2017-09-18
 */
@RestController
@Slf4j
@Scope("prototype")
@RequestMapping("/user")
@Api(value = "", description = "RESTful-API-SERVICE")
public class UserController {

    private UserService userService;
    private HazelcastInstance hazelcastInstance;

    public UserController(UserService userService, HazelcastInstance hazelcastInstance) {
        this.userService = userService;
        this.hazelcastInstance = hazelcastInstance;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-add method")
    public BaseResponse add(@RequestBody User vo) {
        return userService.add(vo);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-update method")
    public BaseResponse update(@RequestBody User vo) {
        return userService.update(vo);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-update method")
    public BaseResponse query(@RequestBody User vo) {
        return userService.query(vo);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-update method")
    public BaseResponse delete(@RequestBody User vo) {
        return userService.delete(vo);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-list method")
    public PageDataResponse list(@RequestBody UserPageParam vo) {
        return userService.list(vo);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-page method")
    public PageResponse page(@RequestBody UserPageParam vo) {
        return userService.page(vo);
    }

    /**
     * 绑定手机号
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindMobile", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-bindMobile method")
    public BaseResponse bindMobile(@RequestBody UserRequest request) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            request.validate(ValidatorConditionType.UPDATE);
            baseResponse = userService.bindMobile(request);
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1006.getCode());
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
    /**
     * 生成图片验证码
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/generateQRCode", method = RequestMethod.GET)
    public void generateQRCode(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        userService.generateQRCode(request,response);
    }
}
