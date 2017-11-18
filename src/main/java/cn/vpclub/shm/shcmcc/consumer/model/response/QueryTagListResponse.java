package cn.vpclub.shm.shcmcc.consumer.model.response;

import lombok.*;

import java.util.List;

/**
 * 获取公众号已创建的标签 响应 bean
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryTagListResponse extends WeChatBaseResponse {
    private List<QueryTagListResponseData> tags;

}
