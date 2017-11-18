package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import cn.vpclub.shm.shcmcc.consumer.entity.Article;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nice on 2017/11/17.
 */
@Getter
@Setter
@ToString
public class NewsInfo implements Serializable {
    private List<Articles> articles;
}
