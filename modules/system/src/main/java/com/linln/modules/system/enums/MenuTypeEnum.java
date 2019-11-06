package com.linln.modules.system.enums;

import lombok.Getter;

/**
 * 菜单类型枚举
 * @author 小懒虫
 * @date 2018/8/14
 */
@Getter
public enum MenuTypeEnum {

    /**
     * 目录类型
     */
    DIRECTORY((byte)1, "目录"),
    /**
     * 菜单类型
     */
    MENU((byte)2, "菜单"),
    /**
     * 按钮类型
     */
    BUTTON((byte)3, "按钮"),

    /**
     * 一级菜单
     * {该枚举已过期，请使用目录类型}
     */
    @Deprecated
    TOP_LEVEL((byte)1, "一级菜单"),
    /**
     * 子级菜单
     * {该枚举已过期，请使用菜单类型}
     */
    @Deprecated
    SUB_LEVEL((byte)2, "子级菜单"),
    /**
     * 按钮类型
     * {该枚举已过期，请使用按钮类型}
     */
    @Deprecated
    NOT_MENU((byte)3, "不是菜单");

    private Byte code;

    private String message;

    MenuTypeEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}

