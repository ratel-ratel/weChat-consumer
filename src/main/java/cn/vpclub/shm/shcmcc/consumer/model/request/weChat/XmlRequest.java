package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import lombok.*;

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
public class XmlRequest implements Serializable {
    @XmlElement(name = "ToUserName")
    private String ToUserName;//
    @XmlElement(name = "FromUserName")
    private String FromUserName;//openId
    @XmlElement(name = "CreateTime")
    private String CreateTime;
    @XmlElement(name = "MsgType")
    private String MsgType;//消息类型
    @XmlElement(name = "Event")
    private String Event;
    @XmlElement(name = "EventKey")
    private String EventKey;//事件的key
    @XmlElement(name = "Encrypt")
    private String Encrypt;
}
