package cn.vpclub.shm.shcmcc.consumer.execption;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Iterator;

/**
 * Created by nice on 2017/11/15.
 */
public class WxMenuGsonAdapter implements JsonSerializer<WxMenu>, JsonDeserializer<WxMenu> {
    public WxMenuGsonAdapter() {
    }

    public JsonElement serialize(WxMenu menu, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        JsonArray buttonArray = new JsonArray();
        Iterator var6 = menu.getButtons().iterator();

        while(var6.hasNext()) {
            WxMenuButton button = (WxMenuButton)var6.next();
            JsonObject buttonJson = this.convertToJson(button);
            buttonArray.add(buttonJson);
        }

        json.add("button", buttonArray);
        if(menu.getMatchRule() != null) {
            json.add("matchrule", this.convertToJson(menu.getMatchRule()));
        }

        return json;
    }

    protected JsonObject convertToJson(WxMenuButton button) {
        JsonObject buttonJson = new JsonObject();
        buttonJson.addProperty("type", button.getType());
        buttonJson.addProperty("name", button.getName());
        buttonJson.addProperty("key", button.getKey());
        buttonJson.addProperty("url", button.getUrl());
        buttonJson.addProperty("media_id", button.getMediaId());
        if(button.getSubButtons() != null && button.getSubButtons().size() > 0) {
            JsonArray buttonArray = new JsonArray();
            Iterator var4 = button.getSubButtons().iterator();

            while(var4.hasNext()) {
                WxMenuButton sub_button = (WxMenuButton)var4.next();
                buttonArray.add(this.convertToJson(sub_button));
            }

            buttonJson.add("sub_button", buttonArray);
        }

        return buttonJson;
    }

    protected JsonObject convertToJson(WxMenuRule menuRule) {
        JsonObject matchRule = new JsonObject();
        matchRule.addProperty("tag_id", menuRule.getTagId());
        matchRule.addProperty("sex", menuRule.getSex());
        matchRule.addProperty("country", menuRule.getCountry());
        matchRule.addProperty("province", menuRule.getProvince());
        matchRule.addProperty("city", menuRule.getCity());
        matchRule.addProperty("client_platform_type", menuRule.getClientPlatformType());
        matchRule.addProperty("language", menuRule.getLanguage());
        return matchRule;
    }

    private WxMenuRule convertToRule(JsonObject json) {
        WxMenuRule menuRule = new WxMenuRule();
        menuRule.setTagId(GsonHelper.getString(json, "group_id"));
        menuRule.setSex(GsonHelper.getString(json, "sex"));
        menuRule.setCountry(GsonHelper.getString(json, "country"));
        menuRule.setProvince(GsonHelper.getString(json, "province"));
        menuRule.setCity(GsonHelper.getString(json, "city"));
        menuRule.setClientPlatformType(GsonHelper.getString(json, "client_platform_type"));
        menuRule.setLanguage(GsonHelper.getString(json, "language"));
        return menuRule;
    }

    public WxMenu deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        WxMenu menu = new WxMenu();
        JsonArray buttonsJson = json.getAsJsonObject().get("menu").getAsJsonObject().get("button").getAsJsonArray();

        for(int i = 0; i < buttonsJson.size(); ++i) {
            JsonObject buttonJson = buttonsJson.get(i).getAsJsonObject();
            WxMenuButton button = this.convertFromJson(buttonJson);
            menu.getButtons().add(button);
            if(buttonJson.get("sub_button") != null && !buttonJson.get("sub_button").isJsonNull()) {
                JsonArray sub_buttonsJson = buttonJson.get("sub_button").getAsJsonArray();

                for(int j = 0; j < sub_buttonsJson.size(); ++j) {
                    JsonObject sub_buttonJson = sub_buttonsJson.get(j).getAsJsonObject();
                    button.getSubButtons().add(this.convertFromJson(sub_buttonJson));
                }
            }
        }

        return menu;
    }

    protected WxMenuButton convertFromJson(JsonObject json) {
        WxMenuButton button = new WxMenuButton();
        button.setName(GsonHelper.getString(json, "name"));
        button.setKey(GsonHelper.getString(json, "key"));
        button.setUrl(GsonHelper.getString(json, "url"));
        button.setType(GsonHelper.getString(json, "type"));
        button.setMediaId(GsonHelper.getString(json, "media_id"));
        return button;
    }
}
