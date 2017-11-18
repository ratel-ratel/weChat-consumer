package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import javax.xml.bind.annotation.XmlElement;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by nice on 2017/11/14.
 */
@Getter
@Setter
@ToString
public class ArticlesElement implements Serializable {
    @XmlElement(name = "Item")
    private ItemElement Item;
}
