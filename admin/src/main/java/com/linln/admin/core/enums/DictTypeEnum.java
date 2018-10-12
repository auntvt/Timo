package com.linln.admin.core.enums;

import lombok.Getter;

/**
 * 数据库字段状态枚举
 */
@Getter
public enum DictTypeEnum {

    VALUE((byte)1, "字符串"),
    KEY_VALUE((byte)2, "键值对"),
    ENUM_VALUE((byte)3, "枚举类");

    private Byte code;

    private String message;

    DictTypeEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}

