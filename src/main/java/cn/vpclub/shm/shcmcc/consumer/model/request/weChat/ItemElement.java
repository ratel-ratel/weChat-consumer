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
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemElement implements Serializable {
    @XmlElement(name = "Title")
    private String Title;//标题
    @XmlElement(name = "Description")
    private String Description;//导读
    @XmlElement(name = "PicUrl")
    private String picUrl; //图片地址url
    @XmlElement(name = "Url")
    private String Url; // http://stagegw.vpclub.cn/shm/web/app/#/login/ddd?openid=
}
