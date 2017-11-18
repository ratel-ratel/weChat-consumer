package cn.vpclub.shm.shcmcc.consumer.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * 批量获取用户信息 响应bean
 * Created by nice on 2017/9/21.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BatchUserQueryResponse extends WeChatBaseResponse {
    @JsonProperty(value = "user_info_list")
    private List<BatchUserQueryResponseData> userInfoList;
}
