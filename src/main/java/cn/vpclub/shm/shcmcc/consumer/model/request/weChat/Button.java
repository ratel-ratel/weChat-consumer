package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nice on 2017/11/14.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Button implements Serializable {
    private String type;
    private String name;
    private String key;
    private String url;
    private String media_id;
    private String appid;
    private String pagepath;
    @JsonProperty(value = "sub_button")
    private List<SubButton> subButton;
}
