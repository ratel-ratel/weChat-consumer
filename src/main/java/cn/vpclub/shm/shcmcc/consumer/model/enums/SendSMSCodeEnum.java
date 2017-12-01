package cn.vpclub.shm.shcmcc.consumer.model.enums;

import cn.vpclub.moses.core.constant.MessageCodeConstant;

/**
 * 接口返回值枚举
 * <p>
 * Created by admin on 2016/1/23.
 */
public enum SendSMSCodeEnum {
  CODE_00000("00000","短信验证码下发成功");// 数据处理成功


  /**
   * 业务编号
   */
  private String code;

  /**
   * 业务值
   */
  private String value;

  private SendSMSCodeEnum(String code, String value) {
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
