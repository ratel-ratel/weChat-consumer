package cn.vpclub.shm.shcmcc.consumer.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * 获取用户身上的标签列表响应 bean
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserTagListResponse extends WeChatBaseResponse {
    @JsonProperty(value = "tagid_list")
    private List<String> tagIdList;//标签ID List
}
