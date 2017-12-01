package cn.vpclub.shm.shcmcc.consumer.service;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.moses.utils.constant.ValidatorConditionType;
import cn.vpclub.moses.utils.hazelcast.HCacheMapUtil;
import cn.vpclub.moses.utils.validator.AttributeValidatorException;
import cn.vpclub.shm.shcmcc.consumer.model.enums.NameSpaceConstant;
import cn.vpclub.shm.shcmcc.consumer.model.enums.SendSMSCodeEnum;
import cn.vpclub.shm.shcmcc.consumer.model.request.SendSmsRequest;
import cn.vpclub.shm.shcmcc.consumer.model.request.SendSmsRequestData;
import cn.vpclub.shm.shcmcc.consumer.model.request.SendSmsparam;
import cn.vpclub.shm.shcmcc.consumer.model.response.SendSmsResponse;
import cn.vpclub.shm.shcmcc.consumer.util.SendSMSUtil;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by nice on 2017/11/8.
 */
@Slf4j
@Service
public class SendMessageService {
    private SendSMSUtil sendSMSUtil;
    private HazelcastInstance hazelcastInstance;

    public SendMessageService(SendSMSUtil sendSMSUtil, HazelcastInstance hazelcastInstance) {
        this.sendSMSUtil = sendSMSUtil;
        this.hazelcastInstance = hazelcastInstance;
    }
    @Value("${SendSms.content}")
    private String content;
    /**
     * @param request
     * @return
     */
    public BaseResponse sendMessage(SendSmsparam request) {
        log.info("sendMessage  request  " + JsonUtil.objectToJson(request));
        BaseResponse baseResponse;
        try {
            request.validate(ValidatorConditionType.CREATE);
            SendSmsRequestData sendSmsRequestData = new SendSmsRequestData();
            sendSmsRequestData.setMsisdn(request.getMobile());
            String randomCode = sendSMSUtil.getRandomCode(6);
            String message=content.replace("{code}",randomCode);
            log.info("message   is  "+message);
            sendSmsRequestData.setContent(message);
            //构建发送短信请求类
            SendSmsRequest sendSmsRequest = new SendSmsRequest();
            sendSmsRequest.setBody(JsonUtil.objectToJson(sendSmsRequestData));
            SendSmsResponse sendSmsResponse = sendSMSUtil.sendSms(sendSmsRequest);
            //发送验证码成功,将短信验证码存到缓存中
            if (null != sendSmsResponse && SendSMSCodeEnum.CODE_00000.getCode().equals(sendSmsResponse.getReturnCode())) {
                baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
                baseResponse.setMessage(SendSMSCodeEnum.CODE_00000.getValue());
                HCacheMapUtil.put(hazelcastInstance, NameSpaceConstant.SEND_MESSAGE, request.getMobile(), randomCode, 120);
            } else {
                baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
            }
        } catch (AttributeValidatorException e) {
            log.error("error: {}", e);
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
            baseResponse.setMessage(e.getMessage());
        }
        log.info("sendMessage  back  " + JsonUtil.objectToJson(baseResponse));
        return baseResponse;
    }
}
