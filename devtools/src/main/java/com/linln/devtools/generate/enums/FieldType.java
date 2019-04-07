package com.linln.devtools.generate.enums;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 小懒虫
 * @date 2018/10/21
 */
@Getter
public enum FieldType {
    String(1, String.class.getSimpleName()),
    Byte(2, Byte.class.getSimpleName()),
    Short(3, Short.class.getSimpleName()),
    Integer(4, Integer.class.getSimpleName()),
    Long(5, Long.class.getSimpleName()),
    Float(6, Float.class.getSimpleName()),
    Double(7, Double.class.getSimpleName()),
    BigDecimal(8, BigDecimal.class.getSimpleName()),
    Boolean(9, Boolean.class.getSimpleName()),
    Character(10, Character.class.getSimpleName()),
    Text(11, "Text"),
    Date(12, Date.class.getSimpleName()),
    Object(13, Object.class.getSimpleName());

    private Integer code;

    private String message;

    FieldType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}


