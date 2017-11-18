package cn.vpclub.shm.shcmcc.consumer.web;

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

import cn.vpclub.shm.shcmcc.consumer.entity.AdminUser;
import cn.vpclub.shm.shcmcc.consumer.model.request.AdminUserPageParam;
import cn.vpclub.shm.shcmcc.consumer.service.AdminUserService;

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
@AllArgsConstructor
@RequestMapping("/adminUser")
@Api(value = "", description = "RESTful-API-SERVICE")
public class AdminUserController {

    private AdminUserService adminUserService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-add method")
    public BaseResponse add(@RequestBody AdminUser vo) {
        return adminUserService.add(vo);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-update method")
    public BaseResponse update(@RequestBody AdminUser vo) {
        return adminUserService.update(vo);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-update method")
    public BaseResponse query(@RequestBody AdminUser vo) {
        return adminUserService.query(vo);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-update method")
    public BaseResponse delete(@RequestBody AdminUser vo) {
        return adminUserService.delete(vo);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-list method")
    public PageDataResponse list(@RequestBody AdminUserPageParam vo) {
        return adminUserService.list(vo);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-page method")
    public PageResponse page(@RequestBody AdminUserPageParam vo) {
        return adminUserService.page(vo);
    }
}
