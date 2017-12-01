package cn.vpclub.shm.shcmcc.consumer.service;


import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.utils.common.StringUtil;
import cn.vpclub.shm.shcmcc.consumer.entity.User;
import cn.vpclub.shm.shcmcc.consumer.model.enums.DataTypeEnum;
import cn.vpclub.shm.shcmcc.consumer.model.request.EmployeePageParam;
import cn.vpclub.shm.shcmcc.consumer.model.request.UserPageParam;
import cn.vpclub.shm.shcmcc.consumer.rpc.EmployeeRpcService;
import cn.vpclub.shm.shcmcc.consumer.rpc.UserRpcService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by dell on 2017/11/30.
 */
@Slf4j
@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRpcService employeeRpcService;

    private UserRpcService userRpcService;

    /**
     * 判断是否是内部员工
     *
     * @param openId
     * @return
     */
    public BaseResponse employeeChecked(String openId) {
        BaseResponse baseResponse;
        log.info("内部员工判断请求参数: {}", openId);
        baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
        baseResponse.setDataInfo(1);//默认不是内部员工
        if (StringUtil.isNotEmpty(openId)) {
            //查询用户信息表
            UserPageParam userPageParam = new UserPageParam();
            userPageParam.setOpenId(openId);
            userPageParam.setDeleted(DataTypeEnum.DELETE_ONLINE.getCode());
            PageResponse pageResponse = userRpcService.page(userPageParam);
            log.info("查询用户信息表返回结果: {}", pageResponse);
            if (ReturnCodeEnum.CODE_1000.getCode().equals(pageResponse.getReturnCode())) {
                User user = (User) pageResponse.getRecords().get(0);
                if (StringUtil.isNotEmpty(user.getMobile())) {
                    //查询员工信息表
                    EmployeePageParam employeePageParam = new EmployeePageParam();
                    employeePageParam.setNameOrMobile(user.getMobile());
                    pageResponse = employeeRpcService.page(employeePageParam);
                    log.info("查询员工信息表返回结果: {}", pageResponse);
                    if (ReturnCodeEnum.CODE_1000.getCode().equals(pageResponse.getReturnCode())) {
                        baseResponse.setDataInfo(2);//表示内部员工
                    }
                }
            }
        }
        log.info("返回前端状态:", baseResponse.getDataInfo());
        return baseResponse;
    }
}
