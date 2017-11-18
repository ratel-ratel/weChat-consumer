package cn.vpclub.shm.shcmcc.consumer.web;


import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.shm.shcmcc.consumer.model.request.SendSmsRequest;
import cn.vpclub.shm.shcmcc.consumer.model.request.SendSmsRequestData;
import cn.vpclub.shm.shcmcc.consumer.model.request.SendSmsparam;
import cn.vpclub.shm.shcmcc.consumer.model.response.SendSmsResponse;
import cn.vpclub.shm.shcmcc.consumer.service.SendMessageService;
import cn.vpclub.shm.shcmcc.consumer.util.SendSMSUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nice on 2017/10/27.
 */
@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
@RequestMapping("/sendMessage")
@Api(value = "", description = "RESTful-API-SERVICE")
public class SendMessageController {

    private SendMessageService sendMessageService;

    /**
     * 发送短信
     */
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-sendSms method")
    public BaseResponse sendSms(@RequestBody SendSmsparam request) {
        return sendMessageService.sendMessage(request);
    }
}
