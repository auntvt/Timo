package com.linln.devtools.code.enums;

import lombok.Getter;

/**
 * @author 小懒虫
 * @date 2018/10/21
 */
@Getter
public enum FieldVerify {

    NoNull(1, "必填"),
    Email(2, "邮箱"),
    Phone(3, "手机"),
    Number(4, "数字"),
    Date(5, "日期"),
    Url(6, "网址"),
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