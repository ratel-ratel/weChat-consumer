package cn.vpclub.shm.shcmcc.consumer.execption;

import com.google.gson.*;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;

import java.lang.reflect.Type;

/**
 * Created by nice on 2017/11/15.
 */
public class WxMediaUploadResultAdapter implements JsonDeserializer<WxMediaUploadResult> {
    public WxMediaUploadResultAdapter() {
    }

    public WxMediaUploadResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        WxMediaUploadResult uploadResult = new WxMediaUploadResult();
        JsonObject uploadResultJsonObject = json.getAsJsonObject();
        if(uploadResultJsonObject.get("type") != null && !uploadResultJsonObject.get("type").isJsonNull()) {
            uploadResult.setType(GsonHelper.getAsString(uploadResultJsonObject.get("type")));
        }

        if(uploadResultJsonObject.get("media_id") != null && !uploadResultJsonObject.get("media_id").isJsonNull()) {
            uploadResult.setMediaId(GsonHelper.getAsString(uploadResultJsonObject.get("media_id")));
        }

        if(uploadResultJsonObject.get("thumb_media_id") != null && !uploadResultJsonObject.get("thumb_media_id").isJsonNull()) {
            uploadResult.setThumbMediaId(GsonHelper.getAsString(uploadResultJsonObject.get("thumb_media_id")));
        }

        if(uploadResultJsonObject.get("created_at") != null && !uploadResultJsonObject.get("created_at").isJsonNull()) {
            uploadResult.setCreatedAt(GsonHelper.getAsPrimitiveLong(uploadResultJsonObject.get("created_at")));
        }

        return uploadResult;
    }
}
