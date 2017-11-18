package cn.vpclub.shm.shcmcc.consumer.util;

import cn.vpclub.moses.core.enums.ReturnCodeEnum;
import cn.vpclub.moses.utils.common.DateUtil;
import cn.vpclub.moses.utils.common.JsonUtil;

import cn.vpclub.shm.shcmcc.consumer.model.request.SendSmsRequest;
import cn.vpclub.shm.shcmcc.consumer.model.response.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by nice on 2017/9/25.
 */
@Slf4j
@Service
public class SendSMSUtil {
    @Value("${SendSms.smsUrl}")
    private String smsUrl;
    @Value("${SendSms.appSecrect}")
    private String appSecrect;
    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param str 加密后的报文
     * @return
     */
    public  String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
//            messageDigest.update(str.getBytes("ISO-8859-1"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private  String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public SendSmsResponse sendSms(SendSmsRequest request) {
        SendSmsResponse response = new SendSmsResponse();
        try {
            String formatDate = DateUtil.formatDate(new Date(), DateUtil.YEARMONTHDAYHHMMSS);
            request.setTimestamp(System.currentTimeMillis()+"");
            request.setAbility("messageSendService");
            request.setAppId("2017072119360137977");
            String transactionId=request.getAppId()+formatDate+"00000001";
            request.setTransactionId(transactionId);
            SignBaseData data = new SignBaseData();
            data.setValue("appid", request.getAppId());
            data.setValue("ability", request.getAbility());
            data.setValue("transationid", request.getTransactionId());
            data.setValue("timestamp",request.getTimestamp() );
            String randomString = getRandomString(32);
            data.setValue("randomstr",randomString );
            data.setValue("body", request.getBody());
            log.info("ASCII  is "+JsonUtil.objectToJson(data));
            String asciiString = this.getASCIIString(data);
            String sha256StrJava = getSHA256StrJava(asciiString);
            data.setValue("sign",sha256StrJava);
            SortedMap<String, Object> values = data.getValues();
            request.setSign(sha256StrJava);
            request.setRandomString(randomString);
            log.info("发送短信的请求地址 : "+smsUrl+" 请求参数是 : "+JsonUtil.objectToJson(values));
            String sendJsonPost = HttpClientUtil.doPostJson(smsUrl, JsonUtil.objectToJson(values));
            log.info("发送短信响应地址是  :  "+sendJsonPost);
            response=JsonUtil.jsonToObject(sendJsonPost,SendSmsResponse.class);
        } catch (Exception e) {
            log.error("error: {}", e);
            response.setReturnCode(ReturnCodeEnum.CODE_1006.getCode().toString());
            response.setMessage(e.getMessage());
        }
        log.info("sendSms result: " + JsonUtil.objectToJson(response));
        return response;
    }

    /**
     * 获取随机字符串
     * @param length
     * @return
     */
    public  String getRandomString(int length) {
        //随机字符串的随机字符库
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }

    /**
     * 获取随机数字验证码
     * @param length
     * @return
     */

    public  String getRandomCode(int length) {
        //随机字符串的随机字符库
        String KeyString = "0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }

    public String getASCIIString(SignBaseData data ){
        log.info("getASCIIString request "+JsonUtil.objectToJson(data));
        SortedMap<String, Object> values = data.getValues();
        Set<String> strings = values.keySet();
        StringBuffer response=new StringBuffer();
        for (String set:strings) {
            response.append(set).append("=").append(values.get(set).toString()).append("&");
        }
        response.append("key=").append(appSecrect);
        log.info("getASCIIString back" +response.toString());
        return response.toString();
    }
}
