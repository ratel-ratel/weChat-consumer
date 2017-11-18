package cn.vpclub.shm.shcmcc.consumer.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

/**
 * Created by dell on 2017/11/11.
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JsApiTicketResponse extends WeChatBaseResponse {
    private String ticket;//微信token
    @JsonProperty(value = "expires_in")
    private Long expiresIn;
}
