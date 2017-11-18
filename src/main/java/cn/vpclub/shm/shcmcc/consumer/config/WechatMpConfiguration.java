//package cn.vpclub.shm.shcmcc.consumer.config;
//
//
//import me.chanjar.weixin.mp.api.WxMpConfigStorage;
//import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
//import me.chanjar.weixin.mp.api.WxMpMessageRouter;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * wechat mp configuration
// *
// * @author zhu.yanqing
// */
//@Configuration
//@ConditionalOnClass(WxMpService.class)
//@EnableConfigurationProperties(WechatMpProperties.class)
//public class WechatMpConfiguration {
//    @Autowired
//    private WechatMpProperties properties;
//
//    @Bean
//    @ConditionalOnMissingBean
//    public WxMpConfigStorage configStorage() {
//        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
//        configStorage.setAppId(this.properties.getAppId());
//        configStorage.setSecret(this.properties.getSecret());
//        configStorage.setToken(this.properties.getToken());
//        configStorage.setAesKey(this.properties.getAesKey());
//        configStorage.setPartnerId(this.properties.getPartnerId());
//        configStorage.setPartnerKey(this.properties.getPartnerKey());
//        return configStorage;
//    }
////    WxMpMessageRouter
//    @Bean
//    @ConditionalOnMissingBean
//    public WxMpService wxMpService(WxMpConfigStorage configStorage) {
//        WxMpService wxMpService = new WxMpServiceImpl();
//        wxMpService.setWxMpConfigStorage(configStorage);
//        return wxMpService;
//    }
//
//
//    @Bean
//    @ConditionalOnMissingBean
//    public WxMpMessageRouter router(WxMpService wxMpService) {
//        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
//
////        // 记录所有事件的日志 （异步执行）
////        newRouter.rule().handler(this.logHandler).next();
////
////        // 自定义菜单事件
////        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
////                .event(WxConsts.BUTTON_CLICK).handler(this.getMenuHandler()).end();
////
////        // 点击菜单连接事件
////        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
////                .event(WxConsts.BUTTON_VIEW).handler(this.nullHandler).end();
////
////        // 关注事件
////        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
////                .event(WxConsts.EVT_SUBSCRIBE).handler(this.getSubscribeHandler())
////                .end();
////
////        // 取消关注事件
////        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
////                .event(WxConsts.EVT_UNSUBSCRIBE)
////                .handler(this.getUnsubscribeHandler()).end();
////
////        // 扫码事件
////        newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
////                .event(WxConsts.EVT_SCAN).handler(this.getScanHandler()).end();
////
////        // 默认
////        newRouter.rule().async(false).handler(this.getMsgHandler()).end();
//
//        return newRouter;
//    }
//}
