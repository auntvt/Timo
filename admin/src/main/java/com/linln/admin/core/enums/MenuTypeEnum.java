package com.linln.admin.core.enums;

import lombok.Getter;

/**
 * 数据库字段状态枚举
 * @author 小懒虫
 * @date 2018/8/14
 */
@Getter
public enum MenuTypeEnum {

    TOP_LEVEL((byte)1, "一级菜单"),
    SUB_LEVEL((byte)2, "子级菜单"),
    NOT_MENU((byte)3, "不是菜单");

    private Byte code;

    private String message;

    MenuTypeEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}

