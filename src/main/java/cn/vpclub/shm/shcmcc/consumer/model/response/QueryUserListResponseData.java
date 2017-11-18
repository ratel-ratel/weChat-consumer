package cn.vpclub.shm.shcmcc.consumer.model.response;

import lombok.*;

import java.util.List;

/**
 * 查询公众号下用户列表响应 data
 * Created by nice on 2017/9/21.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserListResponseData extends WeChatBaseResponse {
    private List<String> openid;
}
