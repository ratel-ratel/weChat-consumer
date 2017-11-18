package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by nice on 2017/11/17.
 */
@Getter
@Setter
@ToString
public class Articles implements Serializable {
    /**
     * "title":"Happy Day",
     * "description":"Is Really A Happy Day",
     * "url":"http://stagegw.vpclub.cn/shm/web/app/#/login?openId=olh8O1g0wz2rmRaOT6s-O_G_VXRo",
     * "picurl":"http://gscmimg.oss-cn-shenzhen.aliyuncs.com/upload/0/201711/13/201711132025074248.png"
     */
    private String title;//标题
    private String description;//描述
    private String url;//点击图片跳转后的Url
    private String picurl;//图片地址
}
