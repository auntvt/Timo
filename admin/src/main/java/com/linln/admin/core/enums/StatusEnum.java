package com.linln.admin.core.enums;

import lombok.Getter;

/**
 * 数据库字段状态枚举
 * @author 小懒虫
 * @date 2018/8/14
 */
@Getter
public enum StatusEnum {

    OK((byte)1, "启用"),
    FREEZED((byte)2, "冻结"),
    DELETE((byte)3, "删除");

    private Byte code;

    private String message;

    StatusEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}

