package com.linln.modules.system.enums;

import lombok.Getter;

/**
 * 字典类型枚举
 * @author 小懒虫
 * @date 2018/8/14
 */
@Getter
public enum DictTypeEnum {

    /**
     * 字符值类型
     */
    VALUE((byte)1, "字符值"),
    /**
     * 键值对类型
     */
    KEY_VALUE((byte)2, "键值对"),
    /**
     * 枚举类类型
     */
    ENUM_VALUE((byte)3, "枚举类");

    private Byte code;

    private String message;

    DictTypeEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}

