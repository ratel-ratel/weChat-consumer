package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import lombok.*;

import java.io.Serializable;

/**
 * 二级菜单
 * Created by nice on 2017/11/14.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SecondMenuData implements Serializable {
    /**
     * {
     "type":"view",
     "name":"搜索",
     "url":"http://www.soso.com/"
     },
     {
     "type":"miniprogram",
     "name":"wxa",
     "url":"http://mp.weixin.qq.com",
     "appid":"wx286b93c14bbf93aa",
     "pagepath":"pages/lunar/index"
     },
     {
     "type":"click",
     "name":"赞一下我们",
     "key":"V1001_GOOD"
     }
     */
    private String type;
    private String name;
    private String url;
    private String appid;
    private String pagepath;
    private String key;
}
