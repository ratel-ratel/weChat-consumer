package cn.vpclub.shm.shcmcc.consumer.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 数字类型枚举包装类
 * Created by dell on 2017/9/19.
 */
@Getter
@ToString
@AllArgsConstructor
public enum DataTypeEnum {

    DELETE_ONLINE(1),//删除标识  1:在线 2:删除
    DELETE(2),//删除标识  1:在线 2:删除
    STATUS_RELEASE(2),//文章状态  1:草稿;2:发布
    TYPE_PUBLIC(1),//文章类型(1:公开:2:内部员工)
    TYPE_PRIVATE(2),
    IN_BLACKLIST(1),//在黑名单里
    NO_BLACKLIST(2),//不在黑名单里
    FOLLOW(1),//是否关注（1：已关注；2：未关注）
    NO_FOLLOW(2);

    private Integer code;



}
