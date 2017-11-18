package cn.vpclub.shm.shcmcc.consumer.model.menu;

import cn.vpclub.shm.shcmcc.consumer.execption.ToStringUtils;
import cn.vpclub.shm.shcmcc.consumer.execption.WxGsonBuilder;
import cn.vpclub.shm.shcmcc.consumer.execption.WxMenuRule;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nice on 2017/11/16.
 */
public class WxMenu implements Serializable {
    private static final long serialVersionUID = -7083914585539687746L;
    private List<WxMenuButton> buttons = new ArrayList();
    private WxMenuRule matchRule;

    public WxMenu() {
    }

    public static WxMenu fromJson(String json) {
        return (WxMenu) WxGsonBuilder.create().fromJson(json, WxMenu.class);
    }

    public static WxMenu fromJson(InputStream is) {
        return (WxMenu)WxGsonBuilder.create().fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), WxMenu.class);
    }

    public List<WxMenuButton> getButtons() {
        return this.buttons;
    }

    public void setButtons(List<WxMenuButton> buttons) {
        this.buttons = buttons;
    }

    public WxMenuRule getMatchRule() {
        return this.matchRule;
    }

    public void setMatchRule(WxMenuRule matchRule) {
        this.matchRule = matchRule;
    }

    public String toJson() {
        return WxGsonBuilder.create().toJson(this);
    }

    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}
