package cn.vpclub.shm.shcmcc.consumer.model.response;

import lombok.*;
import org.springframework.beans.factory.annotation.*;

import java.io.Serializable;

/**
 * Created by nice on 2017/11/13.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JsApiResponse implements Serializable {
    private String secret; //用户唯一凭证密钥
    private String appId;//用户唯一凭证
}
