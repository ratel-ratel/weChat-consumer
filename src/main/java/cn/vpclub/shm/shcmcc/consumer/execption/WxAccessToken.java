package cn.vpclub.shm.shcmcc.consumer.execption;

import java.io.Serializable;

/**
 * Created by nice on 2017/11/15.
 */
public class WxAccessToken implements Serializable {
    private static final long serialVersionUID = 8709719312922168909L;
    private String accessToken;
    private int expiresIn = -1;

    public WxAccessToken() {
    }

    public static WxAccessToken fromJson(String json) {
        return (WxAccessToken)WxGsonBuilder.create().fromJson(json, WxAccessToken.class);
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
