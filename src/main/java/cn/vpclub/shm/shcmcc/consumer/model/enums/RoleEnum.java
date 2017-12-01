package cn.vpclub.shm.shcmcc.consumer.model.enums;

import lombok.Getter;
import lombok.ToString;


/**
 * Created by xiebing 2017/9/21
 */

@Getter
@ToString
public enum RoleEnum {
    UN_DELETED(Integer.valueOf(1), "角色上线"),
    IS_DELETED(Integer.valueOf(2), "角色删除"),
    CODE_2002(Integer.valueOf(2002),"未查询到用户"),
    CODE_2003(Integer.valueOf(2003),"图形验证码已失效，请点击验证码刷新后重试"),
    CODE_2004(Integer.valueOf(2004),"图形验证码输入错误"),
    CODE_2005(Integer.valueOf(2005),"短信验证码输入错误"),
    CODE_2006(Integer.valueOf(2006),"微信号已绑定该手机号，不可重复绑定"),
    CODE_2007(Integer.valueOf(2007),"手机号绑定失败，稍后重试");
    private Integer code;
    private String value;

    RoleEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
