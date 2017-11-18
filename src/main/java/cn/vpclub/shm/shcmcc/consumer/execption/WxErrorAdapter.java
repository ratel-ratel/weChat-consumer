package cn.vpclub.shm.shcmcc.consumer.execption;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by nice on 2017/11/15.
 */
public class WxErrorAdapter implements JsonDeserializer<WxError> {
    public WxErrorAdapter() {
    }

    public WxError deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        WxError wxError = new WxError();
        JsonObject wxErrorJsonObject = json.getAsJsonObject();
        if(wxErrorJsonObject.get("errcode") != null && !wxErrorJsonObject.get("errcode").isJsonNull()) {
            wxError.setErrorCode(GsonHelper.getAsPrimitiveInt(wxErrorJsonObject.get("errcode")));
        }

        if(wxErrorJsonObject.get("errmsg") != null && !wxErrorJsonObject.get("errmsg").isJsonNull()) {
            wxError.setErrorMsg(GsonHelper.getAsString(wxErrorJsonObject.get("errmsg")));
        }

        wxError.setJson(json.toString());
        return wxError;
    }
}
