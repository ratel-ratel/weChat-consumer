package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by nice on 2017/11/14.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlResponse implements Serializable {
    @XmlElement(name = "ToUserName")
    private String ToUserName;
    @XmlElement(name = "FromUserName")
    private String FromUserName;//openId
    @XmlElement(name = "CreateTime")
    private String CreateTime;//获取当前时间
    @XmlElement(name = "MsgType")
    private String MsgType; // news event
    @XmlElement(name = "Event")
    private String Event;//事件类型，VIEW
    @XmlElement(name = "EventKey")
    private String EventKey; // 事件KEY值，设置的跳转URL
//    @XmlElement(name = "MenuID")
//    private String MenuID;//自定义菜单
//    @XmlElement(name = "ArticleCount")
//    private String ArticleCount; //默认1
//    @XmlElement(name = "Item")
//    private ItemElement Item;

}
