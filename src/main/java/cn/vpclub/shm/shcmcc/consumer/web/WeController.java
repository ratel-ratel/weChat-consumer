package cn.vpclub.shm.shcmcc.consumer.web;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.moses.utils.common.MapParserUtil;
import cn.vpclub.moses.utils.common.StringUtil;
import cn.vpclub.shm.shcmcc.consumer.model.enums.WeChatEnum;
import cn.vpclub.shm.shcmcc.consumer.model.request.weChat.XmlRequest;
import cn.vpclub.shm.shcmcc.consumer.model.response.WeChatBaseResponse;
import cn.vpclub.shm.shcmcc.consumer.util.WeChatUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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

    @ResponseBody
    @GetMapping(value = "/checkToken", produces = "text/plain;charset=utf-8")
    public String authGet(@RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {

        log.info("接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        if (weChatUtil.checkSignature(timestamp, nonce, signature)) {
            log.info("echostr:{}", echostr);
            return echostr;
        }
        log.info("非法的请求");
        return "非法请求";
    }

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
    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
    public void doGet(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, IOException, SAXException, ParserConfigurationException, DocumentException, me.chanjar.weixin.common.exception.WxErrorException {
        //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp，nonce参数
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");
        log.info("接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        if (!weChatUtil.checkSignature(timestamp, nonce, signature)) {
            log.info("非法请求，可能属于伪造的请求！");
            response.getOutputStream().println(WeChatEnum.ERROR_REQUEST.getValue());
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

    /**
     * 前段分享获取jsapi授权登相关参数
     */
    @RequestMapping(value = "/getJsApiAuth", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getJsapiAuth(@RequestBody Map<String, Object> reqMap, HttpServletResponse resp) throws me.chanjar.weixin.common.exception.WxErrorException {
        log.info("进入WechatAuthController.getJsapiAuth");
        BaseResponse response = new BaseResponse();
        boolean flag = true;
        //发起请求网页的URL
        String url = MapParserUtil.getStringFromMap(reqMap, "pageUrl");
        log.info("发起请求网页的URL：{}", url);
        if (StringUtils.isEmpty(url)) {
            log.info("页面请求地址pagUrl为空");
            flag = false;
            response.setReturnCode(ReturnCodeEnum.CODE_1006.getCode());
            response.setMessage("pageUrl" + ReturnCodeEnum.CODE_1006.getValue());
            return response;
        }
        if (flag) {
            log.info("开始调用服务，获取微信签名。");
            //调用服务，生成签名
            WxJsapiSignature signature = weChatUtil.createJsapiSignature(url);
            log.info("签名业务处理完毕，返回最终结果：{} ", signature);
            if (null != signature) {
                response.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
                response.setMessage(ReturnCodeEnum.CODE_1000.getValue());
                response.setDataInfo(signature);
            } else {
                log.info("签名生成失败");
                response.setReturnCode(ReturnCodeEnum.CODE_1006.getCode());
                response.setMessage(ReturnCodeEnum.CODE_1006.getValue());
            }

        }
        log.info("前段分享获取jsapi授权,返回 " + JsonUtil.objectToJson(response));
        return response;
    }
}
