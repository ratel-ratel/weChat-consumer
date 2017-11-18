package cn.vpclub.shm.shcmcc.consumer.util;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.moses.utils.common.StringUtil;
import cn.vpclub.moses.utils.common.XmlUtil;
import cn.vpclub.moses.utils.hazelcast.HCacheMapUtil;
import cn.vpclub.moses.utils.web.HttpRequestUtil;

import cn.vpclub.shm.shcmcc.consumer.entity.WeChat;
import cn.vpclub.shm.shcmcc.consumer.enums.ArticlesEnum;
import cn.vpclub.shm.shcmcc.consumer.enums.WeChatEnum;
import cn.vpclub.shm.shcmcc.consumer.model.request.weChat.*;
import cn.vpclub.shm.shcmcc.consumer.model.response.*;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by nice on 2017/9/19.
 */
@Slf4j
@Service
public class WeChatUtil {
    private HazelcastInstance hazelcast;
    private WeChat weChat;
    @Value("${weChat.tokenUrl}")
    private String tokenUrl;
    @Value("${weChat.userUrl}")
    private String userUrl;
    @Value("${weChat.baseUrl}")
    private String baseUrl;
    //发送图文消息的链接和图片地址
    //去绑定手机号的页面
    @Value("${articles.newsUrlOne}")
    private String newsUrlOne;
    @Value("${articles.newsPicUrlOne}")
    private String newsPicUrlOne;
    //去资讯地址
    @Value("${articles.newsUrlTwo}")
    private String newsUrlTwo;
    @Value("${articles.newsPicUrlTwo}")
    private String newsPicUrlTwo;
    //物联网链接地址
    @Value("${articles.newsUrlIOT}")
    private String newsUrlIOT;
    @Value("${articles.newsPicUrlIOT}")
    private String newsPicUrlIOT;
    //政企业务的图文地址
    @Value("${articles.newsUrlStatecos}")
    private String newsUrlStatecos;
    @Value("${articles.newsPicUrlStatecos}")
    private String newsPicUrlStatecos;
    @Value("${articles.textContent}")
    private String textContent;

    @Autowired
    public WeChatUtil(HazelcastInstance hazelcast, WeChat weChat) {
        this.hazelcast = hazelcast;
        this.weChat = weChat;
    }

    private String byteToHexStr(byte myByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tampArr = new char[2];
        tampArr[0] = Digit[(myByte >>> 4) & 0X0F];
        tampArr[1] = Digit[myByte & 0X0F];
        String str = new String(tampArr);
        return str;
    }

    /**
     * 客服发送图文消息给,用户
     *
     * @return
     */
    public  WeChatBaseResponse sendMessageCustom(XmlRequest request) {
        log.info("调用微信 [客服发送图文消息给用户] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        WeChatBaseResponse response = new WeChatBaseResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            if (null == request || StringUtil.isEmpty(request.getFromUserName())) {
                log.info("客服发送图文消息给,用户  openId  是空的");
                response.setReturnCode(ReturnCodeEnum.CODE_1005.getCode().toString());
                response.setMessage(ArticlesEnum.SEND_MESSAGE_ERROR.getValue());
                return response;
            }
            //构建消息返回对象
            News news = new News();
            String menKey = request.getEventKey();
            news.setTouser(request.getFromUserName());//openid
            news.setMsgtype("news");
            NewsInfo newsInfo = new NewsInfo();
            List<Articles> list = new ArrayList<>();
            //匹配是从那个菜单点击进来的返回对应的图文消息
            //先判断用户是否是新关注用户
            if (StringUtil.isEmpty(menKey)&&ArticlesEnum.USER_SUBSCRIBE.getValue().equals(request.getEvent())) {
                //构建文本消息请求   关注公众号后欢迎信息及带绑定手机号的链接消息
                SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
                sendTextMessageRequest.setTouser(news.getTouser());
                sendTextMessageRequest.setMsgtype(ArticlesEnum.TEXT_MESSAGE.getValue());
                TextRequest textRequest=new TextRequest();
                //填充 openid
                textContent= textContent.replace("{0}",news.getTouser());
                textRequest.setContent(textContent);
                sendTextMessageRequest.setText(textRequest);
                WeChatBaseResponse weChatBaseResponse = sendTextMessageCustom(sendTextMessageRequest);
                if (null != weChatBaseResponse && WeChatEnum.CODE_0.getCode().equals(weChatBaseResponse.getReturnCode())) {
                    response.setReturnCode(WeChatEnum.CODE_0.getCode());
                    response.setMessage(WeChatEnum.CODE_0.getValue());
                    return response;
                }
            } else if (ArticlesEnum.MENU_INFORMATION.getValue().equals(menKey)) {
                //查询资讯的图文内容
                Articles articlesTwo = new Articles();
                articlesTwo.setDescription(ArticlesEnum.DESCRIPTION_TWO.getValue());
                articlesTwo.setTitle(ArticlesEnum.TITLE_TWO.getValue());
                String articlesUrl = newsUrlTwo + news.getTouser();
                articlesTwo.setUrl(articlesUrl);
                articlesTwo.setPicurl(newsPicUrlTwo);
                list.add(articlesTwo);
            } else if (ArticlesEnum.MENU_IOT.getValue().equals(menKey)) {
                //物联网图文内容
                Articles articles = new Articles();
                articles.setDescription(ArticlesEnum.DESCRIPTION_IOT.getValue());
                articles.setTitle(ArticlesEnum.TITLE_IOT.getValue());
                String newsUrl = newsUrlIOT + news.getTouser();
                articles.setUrl(newsUrl);
                articles.setPicurl(newsPicUrlIOT);
                list.add(articles);
            } else if (ArticlesEnum.MENU_STATECOS.getValue().equals(menKey)) {
                //政企业务图文内容
                Articles articles = new Articles();
                articles.setDescription(ArticlesEnum.DESCRIPTION_STATECOS.getValue());
                articles.setTitle(ArticlesEnum.TITLE_STATECOS.getValue());
                String newsUrl = newsUrlStatecos + news.getTouser();
                articles.setUrl(newsUrl);
                articles.setPicurl(newsPicUrlStatecos);
                list.add(articles);
            }
            newsInfo.setArticles(list);
            news.setNews(newsInfo);
            String url = baseUrl + "/message/custom/send?access_token=" + token;
            log.info("调用微信 [客服发送图文消息给用户] 接口地址: {} ", url + " 参数 :" + JsonUtil.objectToJson(news));
            try {
                long startTime = System.currentTimeMillis();
                String result = HttpClientUtil.doPostJson(url, JsonUtil.objectToJson(news));
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [客服发送图文消息给用户] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [客服发送图文消息给用户] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, WeChatBaseResponse.class);
                //设置返回值和返回信息
                if (null != response && null == response.getReturnCode()) {
                    response.setReturnCode(WeChatEnum.CODE_0.getCode());
                    response.setMessage(WeChatEnum.CODE_0.getValue());
                }
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        //如果token 失效清空token
        emptyToken(response);
        log.info("调用微信 [客服发送图文消息给用户] back: " + JsonUtil.objectToJson(response));
        return response;
    }

    /**
     * 发送文本消息
     *
     * @return
     */
    public WeChatBaseResponse sendTextMessageCustom(SendTextMessageRequest request) {
        log.info("调用微信 [客服发送文本消息给用户] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        WeChatBaseResponse response = new WeChatBaseResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/message/custom/send?access_token=" + token;
            log.info("调用微信 [客服发送文本消息给用户] 接口地址: {} ", url + " 参数 :" + JsonUtil.objectToJson(request));
            try {
                long startTime = System.currentTimeMillis();
                String result = HttpClientUtil.doPostJson(url, JsonUtil.objectToJson(request));
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [客服发送文本消息给用户] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [客服发送文本消息给用户] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, WeChatBaseResponse.class);
                //设置返回值和返回信息
                if (null != response && null == response.getReturnCode()) {
                    response.setReturnCode(WeChatEnum.CODE_0.getCode());
                    response.setMessage(WeChatEnum.CODE_0.getValue());
                }
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        //如果token 失效清空token
        emptyToken(response);
        log.info("调用微信 [客服发送文本消息给用户] back: " + JsonUtil.objectToJson(response));
        return response;
    }

    /**
     * 从 HttpServletRequest 中得到xml 对象
     *
     * @param xmlString
     * @return
     */
    public XmlRequest getXmlRequest(String xmlString) {
        log.info("getXml  request  " + xmlString);
        //将xml字符串解析成java 对象
        XmlRequest xmlRequest = XmlUtil.deserialize(xmlString, XmlRequest.class);
        log.info("XmlRequest  is " + JsonUtil.objectToJson(xmlRequest));
        String openId = xmlRequest.getFromUserName(); //获取微信公众号id
        String url = "http://stagegw.vpclub.cn/shm/web/app/#/login?openId=" + openId;
        if (StringUtil.isNotEmpty(openId)) {
            log.info("get openId  " + openId);
            return xmlRequest;
        }
        log.info("openId 为空 ");
        return xmlRequest;
    }

    /**
     * 将xml 解析为字符串
     *
     * @param request
     * @return
     */
    public String getXmlString(HttpServletRequest request) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(request.getInputStream());
        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");//解决中文问题，试过用GBK不行
            t.setOutputProperty(OutputKeys.METHOD, "html");
            t.setOutputProperty(OutputKeys.VERSION, "4.0");
            t.setOutputProperty(OutputKeys.INDENT, "no");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            t.transform(new DOMSource(doc), new StreamResult(bos));
            log.info("将xml 解析为字符串  返回  " + bos.toString());
            return bos.toString();
        } catch (TransformerConfigurationException e) {
            log.info("TransformerConfigurationException  " + JsonUtil.objectToJson(e));
        } catch (TransformerException e) {
            log.info("TransformerException  " + JsonUtil.objectToJson(e));
        }
        return "";
    }


    /**
     * 微信 jsapi_ticket
     *
     * @return
     */
    public JsApiTicketResponse getJsApiTicket() {
        JsApiTicketResponse jsApiTicketResponse = new JsApiTicketResponse();
        String token = getToken();
        // https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKE
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
        log.info("调用微信 [微信 jsapi_ticket] 请求参数: {} ", url);
        long startTime = System.currentTimeMillis();
        String result = HttpClientUtil.doGet(url);
        HttpRequestUtil.sendGet(url, token);
        long endTime = System.currentTimeMillis();
        log.info("调用微信 [微信 jsapi_ticket] 接口用时: {} 毫秒", (endTime - startTime));
        log.info("调用微信 [微信 jsapi_ticket] 接口返回信息: {} ", result);
        jsApiTicketResponse = JsonUtil.jsonToObject(result, JsApiTicketResponse.class);
        log.info("调用微信 [微信 jsapi_ticket] back: " + JsonUtil.objectToJson(jsApiTicketResponse));
        return jsApiTicketResponse;
    }

    //请求微信 token
    public String requestToken() {
        String paramsStr = "grant_type=" + weChat.getGrantType()
                + "&appid=" + weChat.getAppId()
                + "&secret=" + weChat.getSecret();
        log.info("requestToken param is :" + paramsStr);
        String accessToken = null;
        try {
            String str = HttpRequestUtil.sendGet(tokenUrl, paramsStr);
            log.info(str);
            Map<String, Object> map = JsonUtil.jsonToMap(str);
            Integer expiresIn = (Integer) map.get("expires_in");
            accessToken = (String) map.get("access_token");
            putToken(accessToken, Long.valueOf(expiresIn - 60 * 10));//提前10分钟清空token
        } catch (Exception e) {
            log.error("error", e);
        }
        return accessToken;
    }

    //将微信token,放入缓存中
    public void putToken(String token, Long expires) {
        log.info("putToken param :token=" + token + ",expires=" + expires);
        int i = HCacheMapUtil.put(hazelcast, "ACCESS_TOKEN", "token", token, expires);
        log.info("putToken result:" + i);
    }


    //获取微信 token,先在缓存中取
    public String getToken() {
//        log.info("getToken start");
        String token = null;
        //  token = requestToken();
        try {
            token = HCacheMapUtil.get(hazelcast, "ACCESS_TOKEN", "token", String.class);
            if (StringUtil.isEmpty(token)) {
                token = requestToken();
            }
        } catch (Exception e) {
            log.error("error", e);
            token = requestToken();
        }

        log.info("getToken result:" + token + " appId" + weChat.getAppId());
        return token;
    }

    //查询所有用户,或从某个位置查询 用户List
    public QueryUserListResponse getUsers(QueryUserListRequest request) {
        QueryUserListResponse response = new QueryUserListResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String paramsStr = "access_token=" + token;
            if (null != request && StringUtil.isNotEmpty(request.getNextOpenid())) {
                paramsStr = "access_token=" + token + "&next_openid=" + request.getNextOpenid();
            }
            String url = baseUrl + "/user/get?";
            log.info("调用微信 [查询所有用户] 接口地址: {} ", url);
            try {
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendGet(url, paramsStr);
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [查询所有用户] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [查询所有用户] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, QueryUserListResponse.class);
                //设置返回值和返回信息
                if (null != response && null == response.getReturnCode()) {
                    response.setReturnCode(WeChatEnum.CODE_0.getCode());
                    response.setMessage(WeChatEnum.CODE_0.getValue());
                }
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
//        log.info("getUsers result: " + JsonUtil.objectToJson(response.getReturnCode()));
        //如果token 失效清空token
        emptyToken(response);
        return response;
    }

    /**
     * 获取单个用户信息
     */
    public QueryUserInfoResponse queryUserInfo(QueryUserInfoRequest request) {
        log.info("调用微信 [获取单个用户信息] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        QueryUserInfoResponse response = new QueryUserInfoResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String paramsStr = "access_token=" + token
                    + "&openid=" + request.getOpenid();
            log.info("requestToken param is :" + paramsStr);
            String url = baseUrl + "/user/info?";
            log.info("调用微信 [获取单个用户信息] 接口地址: {} ", url + " 参数 :" + paramsStr);
            try {
                log.info("微信接口请求:" + paramsStr);
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendGet(url, paramsStr);
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [获取单个用户信息] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [获取单个用户信息] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, QueryUserInfoResponse.class);
                //设置返回值和返回信息
                if (null != response && null == response.getReturnCode()) {
                    response.setReturnCode(WeChatEnum.CODE_0.getCode());
                    response.setMessage(WeChatEnum.CODE_0.getValue());
                }
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        //如果token 失效清空token
        emptyToken(response);
        log.info("调用微信 [获取单个用户信息] back: " + JsonUtil.objectToJson(response));
        return response;
    }

    /**
     * 批量获取用户信息 BatchUserQuery
     */
    public BatchUserQueryResponse batchUserQuery(BatchUserQueryRequest request) {
        log.info("调用微信 [批量获取用户信息] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        BatchUserQueryResponse response = new BatchUserQueryResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/user/info/batchget?access_token=" + getToken();
            log.info("调用微信 [批量获取用户信息] 接口地址: {} ", url);
            try {
                String paramStr = JsonUtil.objectToJson(request);
                log.info("微信接口请求:" + paramStr);
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendPost(url, paramStr, "application/json");
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [批量获取用户信息] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [批量获取用户信息] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, BatchUserQueryResponse.class);
                //设置返回值和返回信息
                if (null != response && null == response.getReturnCode()) {
                    response.setReturnCode(WeChatEnum.CODE_0.getCode());
                    response.setMessage(WeChatEnum.CODE_0.getValue());
                }
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        log.info("batchUserQuery result: " + JsonUtil.objectToJson(response));
        //如果token 失效清空token
        emptyToken(response);
        return response;
    }

    /**
     * 将用户加入黑名单
     *
     * @param request
     * @return
     */
    public WeChatBaseResponse addBlackList(BlackListRequest request) {
        log.info("调用微信 [将用户加入黑名单] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        WeChatBaseResponse response = new WeChatBaseResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/tags/members/batchblacklist?access_token=" + getToken();
            log.info("调用微信 [将用户加入黑名单] 接口地址: {} ", url);
            try {
                String paramStr = JsonUtil.objectToJson(request);
                log.info("微信接口请求:" + paramStr);
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendPost(url, paramStr, "application/json");
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [将用户加入黑名单] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [将用户加入黑名单] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, WeChatBaseResponse.class);
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        log.info("addBlackList result: " + JsonUtil.objectToJson(response));
        //如果token 失效清空token
        emptyToken(response);
        return response;
    }

    /**
     * 获取黑名单列表
     */
    public QueryBlackListResponse queryBlackList(QueryBlackListRequest request) {
        log.info("调用微信 [获取黑名单列表] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        QueryBlackListResponse response = new QueryBlackListResponse();
        String token = getToken();
        if (null != request && StringUtil.isEmpty(request.getBeginOpenid())) {
            request.setBeginOpenid("");
        }
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/tags/members/getblacklist?access_token=" + getToken();
            log.info("调用微信 [获取黑名单列表] 接口地址: {} ", url);
            try {
                String paramStr = JsonUtil.objectToJson(request);
                log.info("微信接口请求:" + paramStr);
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendPost(url, paramStr, "application/json");
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [获取黑名单列表] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [获取黑名单列表] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, QueryBlackListResponse.class);
                //设置返回值和返回信息
                if (null != response && null == response.getReturnCode()) {
                    response.setReturnCode(WeChatEnum.CODE_0.getCode());
                    response.setMessage(WeChatEnum.CODE_0.getValue());
                }
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        log.info("addBlackList result: " + JsonUtil.objectToJson(response));
        //如果token 失效清空token
        emptyToken(response);
        return response;
    }

    /**
     * 将用户移除黑名单
     */
    public WeChatBaseResponse removeBlackList(BlackListRequest request) {
        log.info("调用微信 [将用户移除黑名单] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        WeChatBaseResponse response = new WeChatBaseResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/tags/members/batchunblacklist?access_token=" + getToken();
            log.info("调用微信 [将用户移除黑名单] 接口地址: {} ", url);
            try {
                String paramStr = JsonUtil.objectToJson(request);
                log.info("微信接口请求:" + paramStr);
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendPost(url, paramStr, "application/json");
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [将用户移除黑名单] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [将用户移除黑名单] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, WeChatBaseResponse.class);
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        log.info("removeBlackList result: " + JsonUtil.objectToJson(response));
        //如果token 失效清空token
        emptyToken(response);
        return response;
    }

    /**
     * 批量为用户打标签
     */
    public TagBatchResponse batchTag(TagBatchRequest request) {
        log.info("调用微信 [批量为用户打标签] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        TagBatchResponse response = new TagBatchResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/tags/members/batchtagging?access_token=" + getToken();
            log.info("调用微信 [批量为用户打标签] 接口地址: {} ", url);
            try {
                String paramStr = JsonUtil.objectToJson(request);
                log.info("微信接口请求:" + paramStr);
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendPost(url, paramStr, "application/json");
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [批量为用户打标签] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [批量为用户打标签] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, TagBatchResponse.class);
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        log.info("batchTag result: " + JsonUtil.objectToJson(response));
        //如果token 失效清空token
        emptyToken(response);
        return response;

    }

    /**
     * 创建标签
     *
     * @param request
     * @return
     */
    public CreateTagResponse createTag(CreateTagRequest request) {
        log.info("调用微信 [创建标签] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        CreateTagResponse response = new CreateTagResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/tags/create?access_token=" + getToken();
            log.info("调用微信 [创建标签] 接口地址: {} ", url);
            try {
                String paramStr = JsonUtil.objectToJson(request);
                log.info("微信接口请求:" + paramStr);
                long startTime = System.currentTimeMillis();
                String result = HttpClientUtil.doPostJson(url, paramStr);
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [创建标签] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [创建标签] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, CreateTagResponse.class);
                //设置返回值和返回信息
                if (null != response && null == response.getReturnCode()) {
                    response.setReturnCode(WeChatEnum.CODE_0.getCode());
                    response.setMessage(WeChatEnum.CODE_0.getValue());
                }
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        log.info("createTag result: " + JsonUtil.objectToJson(response));
        //如果token 失效清空token
        emptyToken(response);
        return response;
    }

    /**
     * 获取公众号已创建的标签
     */
    public QueryTagListResponse queryTagList() {
        QueryTagListResponse response = new QueryTagListResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/tags/get?access_token=" + getToken();
//           String url = baseUrl + "/tags/get?access_token=" + "LjwBY5vR_lRFAQpUFRxW-C17uka_qvQXVseXCtZ4qhuBHxFjQ50wOCNbXdfdOnV4ro2TizL2ehueOyj_a5r_wMFHsKbdrAb4fBg3QQ_ruxb-zY2q3F5ipnai1v5d19E-NYFbAIACNU";
            log.info("调用微信 [获取公众号已创建的标签] 接口地址: {} ", url);
            try {
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendGet(url, null);
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [获取公众号已创建的标签] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [获取公众号已创建的标签] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, QueryTagListResponse.class);
                //设置返回值和返回信息
                if (null != response && null == response.getReturnCode()) {
                    response.setReturnCode(WeChatEnum.CODE_0.getCode());
                    response.setMessage(WeChatEnum.CODE_0.getValue());
                }
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        log.info("queryTagList   back  " + JsonUtil.objectToJson(response));
        //如果token 失效清空token
        emptyToken(response);
        return response;
    }

    /**
     * 如果 token失效就清空token
     *
     * @param response
     */
    public void emptyToken(WeChatBaseResponse response) {
        log.info("emptyToken    request  " + JsonUtil.objectToJson(response));
        if (null != response.getMessage() && response.getMessage().indexOf("access_token is invalid") > 0) {
            int i = HCacheMapUtil.remove(hazelcast, "ACCESS_TOKEN", "token");
            log.info("emptyToken   back  " + i);
        }
    }

    /**
     * 编辑标签
     */
    public WeChatBaseResponse updateTag(UpdateTagRequest request) {
        log.info("调用微信 [编辑标签] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        WeChatBaseResponse response = new WeChatBaseResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/tags/update?access_token=" + getToken();
            log.info("调用微信 [编辑标签] 接口地址: {} ", url);
            try {
                String paramStr = JsonUtil.objectToJson(request);
                log.info("微信接口请求:" + paramStr);
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendPost(url, paramStr, "application/json");
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [编辑标签] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [编辑标签] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, WeChatBaseResponse.class);
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        log.info("updateTag result: " + JsonUtil.objectToJson(response));
        //如果token 失效清空token
        emptyToken(response);
        return response;
    }

    /**
     * 删除标签
     */
    public WeChatBaseResponse deleteTag(DeleteTagRequest request) {
        log.info("调用微信 [删除标签] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        WeChatBaseResponse response = new WeChatBaseResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/delete?access_token=" + getToken();
            log.info("调用微信 [删除标签] 接口地址: {} ", url);
            try {
                String paramStr = JsonUtil.objectToJson(request);
                log.info("微信接口请求:" + paramStr);
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendPost(url, paramStr, "application/json");
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [删除标签] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [删除标签] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, WeChatBaseResponse.class);
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        log.info("deleteTag result: " + JsonUtil.objectToJson(response));
        //如果token 失效清空token
        emptyToken(response);
        return response;
    }

    /**
     * 获取标签下粉丝列表
     */
    public QueryOpenidListByTagResponse queryOpenidListByTag(QueryOpenidListByTagRequest request) {
        log.info("调用微信 [获取标签下粉丝列表] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        QueryOpenidListByTagResponse response = new QueryOpenidListByTagResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/user/tag/get?access_token=" + getToken();
            log.info("调用微信 [获取标签下粉丝列表] 接口地址: {} ", url);
            try {
                String paramStr = JsonUtil.objectToJson(request);
                log.info("微信接口请求:" + paramStr);
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendPost(url, paramStr, "application/json");
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [获取标签下粉丝列表] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [获取标签下粉丝列表] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, QueryOpenidListByTagResponse.class);
                //设置返回值和返回信息
                if (null != response && null == response.getReturnCode()) {
                    response.setReturnCode(WeChatEnum.CODE_0.getCode());
                    response.setMessage(WeChatEnum.CODE_0.getValue());
                }
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        log.info("queryOpenidListByTag result: " + JsonUtil.objectToJson(response));
        //如果token 失效清空token
        emptyToken(response);
        return response;
    }

    /**
     * 批量为用户取消标签 batchUnTag
     */
    public WeChatBaseResponse batchUnTag(BatchUnTagRequest request) {
        log.info("调用微信 [批量为用户取消标签] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        WeChatBaseResponse response = new WeChatBaseResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/tags/members/batchuntagging?access_token=" + getToken();
            log.info("调用微信 [批量为用户取消标签] 接口地址: {} ", url);
            try {
                String paramStr = JsonUtil.objectToJson(request);
                log.info("微信接口请求:" + paramStr);
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendPost(url, paramStr, "application/json");
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [批量为用户取消标签] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [批量为用户取消标签] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, WeChatBaseResponse.class);
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        log.info("batchUnTag result: " + JsonUtil.objectToJson(response));
        //如果token 失效清空token
        emptyToken(response);
        return response;
    }

    /**
     * 获取用户身上的标签列表响应
     */
    public QueryUserTagListResponse queryUserTagList(QueryUserTagListRequest request) {
        log.info("调用微信 [获取用户身上的标签列表响应] 接口请求参数: {} " + JsonUtil.objectToJson(request));
        QueryUserTagListResponse response = new QueryUserTagListResponse();
        String token = getToken();
        if (StringUtil.isEmpty(token)) {
            response.setReturnCode("-1");
            response.setMessage("未获取到token");
        } else {
            String url = baseUrl + "/tags/getidlist?access_token=" + getToken();
            log.info("调用微信 [获取用户身上的标签列表响应] 接口地址: {} ", url);
            try {
                String paramStr = JsonUtil.objectToJson(request);
                log.info("微信接口请求:" + paramStr);
                long startTime = System.currentTimeMillis();
                String result = HttpRequestUtil.sendPost(url, paramStr, "application/json");
                long endTime = System.currentTimeMillis();
                log.info("调用微信 [获取用户身上的标签列表响应] 接口用时: {} 毫秒", (endTime - startTime));
                log.info("调用微信 [获取用户身上的标签列表响应] 接口返回信息: {} ", result);
                response = JsonUtil.jsonToObject(result, QueryUserTagListResponse.class);
                //设置返回值和返回信息
                if (null != response && null == response.getReturnCode()) {
                    response.setReturnCode(WeChatEnum.CODE_0.getCode());
                    response.setMessage(WeChatEnum.CODE_0.getValue());
                }
            } catch (Exception e) {
                log.error("error", e);
                response.setReturnCode(ReturnCodeEnum.CODE_1004.getCode().toString());
                response.setMessage(ReturnCodeEnum.CODE_1004.getValue());
            }
        }
        log.info("queryUserTagList result: " + JsonUtil.objectToJson(response));
        //如果token 失效清空token
        emptyToken(response);
        return response;
    }


}
