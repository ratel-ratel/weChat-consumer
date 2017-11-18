package cn.vpclub.shm.shcmcc.consumer.web;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.moses.utils.common.MapParserUtil;
import cn.vpclub.moses.utils.common.StringUtil;
import cn.vpclub.moses.utils.web.HttpResponseUtil;
import cn.vpclub.shm.shcmcc.consumer.entity.WeChat;
import cn.vpclub.shm.shcmcc.consumer.execption.WxErrorException;
import cn.vpclub.shm.shcmcc.consumer.model.request.weChat.*;
import cn.vpclub.shm.shcmcc.consumer.model.response.WeChatBaseResponse;
import cn.vpclub.shm.shcmcc.consumer.util.UserUtil;
import cn.vpclub.shm.shcmcc.consumer.util.WeChatUtil;
import cn.vpclub.spring.boot.hazelcast.utils.HCacheMapUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by nice on 2017/10/27.
 */
@RestController
@Slf4j
@Scope("prototype")
@RequestMapping("/weChat")
@Api(value = "", description = "RESTful-API-SERVICE")
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
public class WeController {
    @Autowired
    private WeChatUtil weChatUtil;

//    @RequestMapping(value = "/sendMessageCustom")
//    @ResponseBody
//    public WeChatBaseResponse sendTextMessageCustom(@RequestBody SendTextMessageRequest request) {
//        WeChatBaseResponse weChatBaseResponse = weChatUtil.sendTextMessageCustom(request);
//        return weChatBaseResponse;
//    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws DocumentException
     * @throws me.chanjar.weixin.common.exception.WxErrorException
     */
    @RequestMapping(value = "/checkToken")
    public void doGet(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, IOException, SAXException, ParserConfigurationException, DocumentException, me.chanjar.weixin.common.exception.WxErrorException {
        //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp，nonce参数
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");
        if (StringUtil.isNotEmpty(echostr)) {
            log.info("echostr   is   " + echostr);
            response.getOutputStream().println(echostr);
        } else {
            String xml = weChatUtil.getXmlString(request);

            if (StringUtil.isNotEmpty(xml)) {
                XmlRequest xmlRequest = weChatUtil.getXmlRequest(xml);
                log.info("XmlRequest  is  " + xmlRequest);
                if (StringUtil.isNotEmpty(xmlRequest.getFromUserName())) {

                    WeChatBaseResponse weChatBaseResponse = weChatUtil.sendMessageCustom(xmlRequest);
                    log.info("sendMessageCustom  back  " + JsonUtil.objectToJson(weChatBaseResponse));
                }
            }

        }
    }

}
