package cn.vpclub.shm.shcmcc.consumer.model.request.weChat;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 二级菜单
 * Created by nice on 2017/11/14.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SecondMenu implements Serializable {
    private String name;//菜单
    private List<SecondMenuData> SecondMenu;//
}
