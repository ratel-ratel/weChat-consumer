//package cn.vpclub.shm.shcmcc.consumer.web;
//
//
//import cn.vpclub.moses.core.enums.ReturnCodeEnum;
//import cn.vpclub.moses.core.model.response.BaseResponse;
//import cn.vpclub.moses.utils.common.JsonUtil;
//import cn.vpclub.moses.utils.web.HttpResponseUtil;
//import lombok.extern.slf4j.Slf4j;
//import me.chanjar.weixin.common.bean.menu.WxMenu;
//import me.chanjar.weixin.common.bean.menu.WxMenuButton;
//import me.chanjar.weixin.common.bean.menu.WxMenuRule;
//import me.chanjar.weixin.common.exception.WxErrorException;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.core.Context;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by 祝燕青 on 2017/2/17.
// */
//@RestController
//@RequestMapping("/menu")
//@Slf4j
//@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
//public class WxMenuController {
//
//
//    @Value("${redirect.baseUrl}")
//    private String baseUrl;
//    //任务大厅菜单连接地址
//    @Value("${redirect.taskMenuUrl}")
//    private String taskMenuUrl;
//    @Autowired
//    private WxMpService wxService;
//
//    private String keyTag = "unfinished";
//    private String typeTag = "click";
//
//    public WxMenuController(WxMpService wxService) {
//        this.wxService = wxService;
//    }
//
//    /**
//     * 获得自定义菜单
//     */
//    @RequestMapping(value = "/query_menu")
//    public void queryMenu(@Context HttpServletResponse resp) {
//        log.info("进入WxMenuController.queryMenu");
//        BaseResponse<WxMpMenu> baseResponse = new BaseResponse<>();
//        try {
//            WxMpMenu wxMpMenu = wxService.getMenuService().menuGet();
//            String logJson = JsonUtil.objectToJson(wxMpMenu);
//            log.info("获得自定义菜单返回信息：{}", logJson);
//            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
//            baseResponse.setDataInfo(wxMpMenu);
//        } catch (WxErrorException e) {
//            log.info("WxMenuController.queryMenu出现异常:{}", e);
//            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1005.getCode());
//            baseResponse.setMessage(ReturnCodeEnum.CODE_1005.getValue());
//        }
//        //输出结果
//        HttpResponseUtil.setResponseJsonBody(resp, JsonUtil.objectToJson(baseResponse));
//    }
//
//    /**
//     * 创建微信菜单(默认菜单)
//     */
//    @RequestMapping(value = "/create_menu")
//    public void createMenu(HttpServletRequest request, HttpServletResponse resp) {
//        log.info("进入WxMenuController.createWechatMenu");
//        BaseResponse<String> baseResponse = new BaseResponse<>();
//        try {
//            WxMenu menu = new WxMenu();
//            List<WxMenuButton> buttons = new ArrayList<>();
//
//            /*金融业务菜单 start*/
//            WxMenuButton financeButton = new WxMenuButton();
//            List<WxMenuButton> financeButtonSubButton = new ArrayList();
//            financeButton.setName("金融业务");
//            financeButton.setType(typeTag);
//
//            WxMenuButton subButton = new WxMenuButton();
//            subButton.setName("银卡开户");
//            subButton.setType(typeTag);
//            subButton.setKey(keyTag);
//            financeButtonSubButton.add(subButton);
//
//            subButton = new WxMenuButton();
//            subButton.setName("申请贷款");
//            subButton.setType(typeTag);
//            subButton.setKey(keyTag);
//            financeButtonSubButton.add(subButton);
//
//            financeButton.setSubButtons(financeButtonSubButton);
//
//            buttons.add(financeButton);
//            /*金融业务菜单 end*/
//
//            /*商户服务菜单  start*/
//            WxMenuButton merchantServerButton = new WxMenuButton();
//            merchantServerButton.setName("商户服务");
//            merchantServerButton.setType(typeTag);
//            merchantServerButton.setKey(keyTag);
//
//            buttons.add(merchantServerButton);
//            /*商户服务菜单  end*/
//
//            /*服务中心菜单 start*/
//            WxMenuButton serverCenterButton = new WxMenuButton();
//            List<WxMenuButton> serverCenterButtonSubButton = new ArrayList();
//
//            serverCenterButton.setName("服务中心");
//            serverCenterButton.setType(typeTag);
//
//            subButton = new WxMenuButton();
//            subButton.setName("任务平台");
//            subButton.setType(typeTag);
//            subButton.setKey("newcomerMenu");
//            serverCenterButtonSubButton.add(subButton);
//
//            subButton = new WxMenuButton();
//            subButton.setName("在线商城");
//            subButton.setType(typeTag);
//            subButton.setKey(keyTag);
//            serverCenterButtonSubButton.add(subButton);
//
//            subButton = new WxMenuButton();
//            subButton.setName("个人资料");
//            subButton.setType(typeTag);
//            subButton.setKey(keyTag);
//            serverCenterButtonSubButton.add(subButton);
//
//            subButton = new WxMenuButton();
//            subButton.setName("便民服务");
//            subButton.setType(typeTag);
//            subButton.setKey(keyTag);
//            serverCenterButtonSubButton.add(subButton);
//
//            serverCenterButton.setSubButtons(serverCenterButtonSubButton);
//            buttons.add(serverCenterButton);
//            /*服务中心菜单 end*/
//
//            menu.setButtons(buttons);
//
//            String result = wxService.getMenuService().menuCreate(menu);
//            log.info("创建默认菜单返回的结果：{}", result);
//            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
//            baseResponse.setDataInfo(result);
//        } catch (Exception e) {
//            log.info("WxMenuController.createWechatMenu出现异常:{}", e);
//            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1005.getCode());
//            baseResponse.setMessage(ReturnCodeEnum.CODE_1005.getValue());
//        }
//        //输出结果
//        HttpResponseUtil.setResponseJsonBody(resp, JsonUtil.objectToJson(baseResponse));
//    }
//
//    /**
//     * 创建微信菜单(个性化菜单，根据标签ID作为匹配规则)
//     */
//    @RequestMapping(value = "/create_conditional_menu")
//    public void createConditionalMenu(@Context HttpServletResponse resp) {
//        log.info("进入WxMenuController.createConditionalMenu");
//        BaseResponse<String> baseResponse = new BaseResponse<>();
//        try {
//            WxMenu menu = new WxMenu();
//            List<WxMenuButton> buttons = new ArrayList<>();
//
//            /*金融业务菜单 start*/
//            WxMenuButton financeButton = new WxMenuButton();
//            List<WxMenuButton> financeButtonSubButton = new ArrayList();
//            financeButton.setName("金融业务");
//            financeButton.setType(typeTag);
//
//            WxMenuButton subButton = new WxMenuButton();
//            subButton.setName("银卡开户");
//            subButton.setType(typeTag);
//            subButton.setKey(keyTag);
//            financeButtonSubButton.add(subButton);
//
//            subButton = new WxMenuButton();
//            subButton.setName("申请贷款");
//            subButton.setType(typeTag);
//            subButton.setKey(keyTag);
//            financeButtonSubButton.add(subButton);
//
//            financeButton.setSubButtons(financeButtonSubButton);
//
//            buttons.add(financeButton);
//            /*金融业务菜单 end*/
//
//            /*商户服务菜单  start*/
//            WxMenuButton merchantServerButton = new WxMenuButton();
//            merchantServerButton.setName("商户服务");
//            merchantServerButton.setType(typeTag);
//            merchantServerButton.setKey(keyTag);
//
//            buttons.add(merchantServerButton);
//            /*商户服务菜单  end*/
//
//            /*服务中心菜单 start*/
//            WxMenuButton serverCenterButton = new WxMenuButton();
//            List<WxMenuButton> serverCenterButtonSubButton = new ArrayList();
//
//            serverCenterButton.setName("服务中心");
//            serverCenterButton.setType(typeTag);
//
//            subButton = new WxMenuButton();
//            subButton.setName("任务平台");
//            subButton.setType("view");
//            subButton.setUrl(baseUrl + taskMenuUrl);
//            serverCenterButtonSubButton.add(subButton);
//
//            subButton = new WxMenuButton();
//            subButton.setName("在线商城");
//            subButton.setType(typeTag);
//            subButton.setKey(keyTag);
//            serverCenterButtonSubButton.add(subButton);
//
//            subButton = new WxMenuButton();
//            subButton.setName("个人资料");
//            subButton.setType(typeTag);
//            subButton.setKey(keyTag);
//            serverCenterButtonSubButton.add(subButton);
//
//            subButton = new WxMenuButton();
//            subButton.setName("便民服务");
//            subButton.setType(typeTag);
//            subButton.setKey(keyTag);
//            serverCenterButtonSubButton.add(subButton);
//
//            serverCenterButton.setSubButtons(serverCenterButtonSubButton);
//            buttons.add(serverCenterButton);
//            /*服务中心菜单 end*/
//
//            menu.setButtons(buttons);
//            WxMenuRule rule = new WxMenuRule();
//            rule.setTagId("100");
//            menu.setMatchRule(rule);
//            String result = wxService.getMenuService().menuCreate(menu.toJson());
//            log.info("创建个性化菜单返回的结果：{}", result);
//            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
//            baseResponse.setDataInfo(ReturnCodeEnum.CODE_1000.getValue());
//            //输出结果
//            HttpResponseUtil.setResponseJsonBody(resp, JsonUtil.objectToJson(result));
//        } catch (Exception e) {
//            log.info("WxMenuController.createConditionalMenu出现异常:{}", e);
//            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1005.getCode());
//            baseResponse.setMessage(ReturnCodeEnum.CODE_1005.getValue());
//        }
//        //输出结果
//        HttpResponseUtil.setResponseJsonBody(resp, JsonUtil.objectToJson(baseResponse));
//    }
//
//    /**
//     * 删除菜单
//     */
//    @RequestMapping(value = "/delete_menu")
//    public void deleteMenu(@Context HttpServletResponse resp) {
//        log.info("进入WxMenuController.deleteMenu");
//        BaseResponse response = new BaseResponse();
//        try {
//            wxService.getMenuService().menuDelete();
//            response.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
//            response.setMessage(ReturnCodeEnum.CODE_1000.getValue());
//        } catch (WxErrorException e) {
//            log.info("WxMenuController.deleteMenu出现异常:{}", e);
//            response.setReturnCode(ReturnCodeEnum.CODE_1005.getCode());
//            response.setMessage(ReturnCodeEnum.CODE_1005.getValue());
//        }
//        //输出结果
//        HttpResponseUtil.setResponseJsonBody(resp, JsonUtil.objectToJson(response));
//    }
//}
