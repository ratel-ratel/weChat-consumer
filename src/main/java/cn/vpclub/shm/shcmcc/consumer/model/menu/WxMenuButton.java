package cn.vpclub.shm.shcmcc.consumer.model.menu;

import cn.vpclub.shm.shcmcc.consumer.execption.ToStringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nice on 2017/11/16.
 */
public class WxMenuButton implements Serializable{

    private static final long serialVersionUID = -1070939403109776555L;
    private String type;
    private String name;
    private String key;
    private String url;
    private String mediaId;
    private List<WxMenuButton> subButtons = new ArrayList();

    public WxMenuButton() {
    }

    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<WxMenuButton> getSubButtons() {
        return this.subButtons;
    }

    public void setSubButtons(List<WxMenuButton> subButtons) {
        this.subButtons = subButtons;
    }

    public String getMediaId() {
        return this.mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
