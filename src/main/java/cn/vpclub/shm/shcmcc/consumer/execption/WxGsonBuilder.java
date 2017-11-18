package cn.vpclub.shm.shcmcc.consumer.execption;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;

/**
 * Created by nice on 2017/11/15.
 */
public class WxGsonBuilder {
    public static final GsonBuilder INSTANCE = new GsonBuilder();

    public WxGsonBuilder() {
    }

    public static Gson create() {
        return INSTANCE.create();
    }

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(WxAccessToken.class, new WxAccessTokenAdapter());
        INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
        INSTANCE.registerTypeAdapter(WxMenu.class, new WxMenuGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMediaUploadResult.class, new WxMediaUploadResultAdapter());
    }
}
