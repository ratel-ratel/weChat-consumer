package cn.vpclub.shm.shcmcc.consumer.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * 查询微信用户信息 响应 bean
 * Created by nice on 2017/9/21.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueryUserInfoResponse extends WeChatBaseResponse {
    private String subscribe;//用户是否订阅该公众号标识 值为0时，代表此用户没有关注该公众号，拉取不到其余信息
    private String openid;//用户唯一标识
    @JsonProperty(value = "nickname")
    private String nickName;//用户昵称
    private String sex;//性别 值为1时是男性，值为2时是女性，值为0时是未知
    private String language;//用户的语言，简体中文为zh_CN
    private String city;//用户所在城市
    private String province;//用户所在省份
    private String country;//用户所在国家
    @JsonProperty(value = "headimgurl")
    private String headImgUrl;//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
    @JsonProperty(value = "subscribe_time")
    private String subscribeTime;//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间 1414810422
    private String remark;
    @JsonProperty(value = "groupid")
    private String groupId;//用户所在的分组ID（暂时兼容用户分组旧接口）
    @JsonProperty(value = "tagid_list")
    private List<String> tagIdList;//用户拥有标签 id list
    @JsonProperty(value = "unionid")
    private String unionId;//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
}

