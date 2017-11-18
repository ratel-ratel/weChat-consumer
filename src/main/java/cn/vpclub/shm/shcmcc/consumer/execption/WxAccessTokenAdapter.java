package cn.vpclub.shm.shcmcc.consumer.execption;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by nice on 2017/11/15.
 */
public class WxAccessTokenAdapter implements JsonDeserializer<WxAccessToken> {
    public WxAccessTokenAdapter() {
    }

    public WxAccessToken deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        WxAccessToken accessToken = new WxAccessToken();
        JsonObject accessTokenJsonObject = json.getAsJsonObject();
        if(accessTokenJsonObject.get("access_token") != null && !accessTokenJsonObject.get("access_token").isJsonNull()) {
            accessToken.setAccessToken(GsonHelper.getAsString(accessTokenJsonObject.get("access_token")));
        }

        if(accessTokenJsonObject.get("expires_in") != null && !accessTokenJsonObject.get("expires_in").isJsonNull()) {
            accessToken.setExpiresIn(GsonHelper.getAsPrimitiveInt(accessTokenJsonObject.get("expires_in")));
        }

        return accessToken;
    }
}
