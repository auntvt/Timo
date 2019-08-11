package com.linln.devtools.generate.enums;

import lombok.Getter;

/**
 * 代码生成-字段验证方式
 * @author 小懒虫
 * @date 2018/10/21
 */
@Getter
public enum FieldVerify {

    /**
     * 非字符串空处理
     */
    NotNull(1, "必填"),
    /**
     * 邮箱验证
     */
    Email(2, "邮箱"),
    /**
     * 手机验证
     */
    Phone(3, "手机"),
    /**
     * 数字验证
     */
    Number(4, "数字"),
    /**
     * 日期验证
     */
    Date(5, "日期"),
    /**
     * 网址验证
     */
    Url(6, "网址"),
    /**
     * 身份证验证
     */
    IdKey(7, "身份证");

    private Integer code;

    private String message;

    FieldVerify(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean equals(FieldVerify fieldVerify){
        String name = name();
        return name.equals(fieldVerify.name());
    }
}