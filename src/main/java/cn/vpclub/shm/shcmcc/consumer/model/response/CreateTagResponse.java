package cn.vpclub.shm.shcmcc.consumer.model.response;

import lombok.*;

/**
 * 创建标签响应 bean
 * Created by nice on 2017/9/20.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateTagResponse extends WeChatBaseResponse {
    private CreateTagResponseData tag;
}
