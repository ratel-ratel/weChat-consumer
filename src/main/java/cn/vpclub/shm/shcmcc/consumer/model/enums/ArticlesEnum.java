package cn.vpclub.shm.shcmcc.consumer.model.enums;

import cn.vpclub.moses.core.constant.MessageCodeConstant;

/**
 * 接口返回值枚举
 * <p>
 * Created by admin on 2016/1/23.
 */
public enum ArticlesEnum {
  CODE_0("0", MessageCodeConstant.MESSAGE_COMMON_SUCCESS),//
  USER_SUBSCRIBE("用户关注公众号","subscribe"),
  USER_UN_SUBSCRIBE("用户取消关注","unsubscribe"),
  TEXT_MESSAGE("文本消息类型","text"),
  SEND_MESSAGE_ERROR("openId is empty","openId is empty"),
//  DESCRIPTION_ONE("绑定手机号界面","绑定手机号界面"),
//  TITLE_ONE("绑定手机号","绑定手机号"),
  DESCRIPTION_TWO("去资讯平台","查看更多资讯"),
  TITLE_TWO("资讯平台","资讯平台"),

  DESCRIPTION_IOT("物联网欢迎你","物联网欢迎你"),
  TITLE_IOT("物联网欢迎你","物联网欢迎你"),
  DESCRIPTION_STATECOS("政企业务","政企业务"),
  TITLE_STATECOS("政企业务","政企业务"),
  MENU_IOT("物联网","MENU_KEY"),//
  MENU_STATECOS("政企业务","MENU_ZQZL"),
//  MENU_BIND_MOBILE("绑定手机号","MENU_BIND_MOBILE"),
  MENU_INFORMATION("查看资讯","MENU_INFORMATION");
  /**
   * 业务编号
   */
  private String code;

  /**
   * 业务值
   */
  private String value;

  private ArticlesEnum(String code, String value) {
    this.code = code;
    this.value = value;
  }

  public final String getCode() {
    return code;
  }

  public final String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this.code + "=" + this.value;
  }
}
