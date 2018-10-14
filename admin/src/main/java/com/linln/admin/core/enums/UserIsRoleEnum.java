package com.linln.admin.core.enums;

import lombok.Getter;

/**
 * 数据库字段状态枚举
 * @author 小懒虫
 * @date 2018/8/14
 */
@Getter
public enum UserIsRoleEnum {

    YES((byte)1, "是后台管理员"),
    NO((byte)2, "不是后台管理员");

    private Byte code;
    private String message;

    UserIsRoleEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}

