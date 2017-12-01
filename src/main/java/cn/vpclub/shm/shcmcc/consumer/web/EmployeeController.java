package cn.vpclub.shm.shcmcc.consumer.web;


import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.shm.shcmcc.consumer.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前端控制器
 * Created by dell on 2017/9/22.
 */
@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
@RequestMapping("/employee")
@Api(value = "", description = "RESTful-API-SERVICE")
public class EmployeeController {
    private EmployeeService employeeService;

    @RequestMapping(value = "/employeeChecked", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("-employeeChecked method")
    public BaseResponse employeeChecked(@RequestParam("openId") String openId){
        return  employeeService.employeeChecked(openId);
    }

}
