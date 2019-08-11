package com.linln.devtools.generate.enums;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代码生成-实体类字段类型
 * @author 小懒虫
 * @date 2018/10/21
 */
@Getter
public enum FieldType {
    /**
     * String 类型
     */
    String(1, String.class.getSimpleName()),
    /**
     * Byte 类型
     */
    Byte(2, Byte.class.getSimpleName()),
    /**
     * Short 类型
     */
    Short(3, Short.class.getSimpleName()),
    /**
     * Integer 类型
     */
    Integer(4, Integer.class.getSimpleName()),
    /**
     * Long 类型
     */
    Long(5, Long.class.getSimpleName()),
    /**
     * Float 类型
     */
    Float(6, Float.class.getSimpleName()),
    /**
     * Double 类型
     */
    Double(7, Double.class.getSimpleName()),
    /**
     * BigDecimal 高精度对象类型
     */
    BigDecimal(8, BigDecimal.class.getSimpleName()),
    /**
     * Boolean 高精度对象类型
     */
    Boolean(9, Boolean.class.getSimpleName()),
    /**
     * Character 高精度对象类型
     */
    Character(10, Character.class.getSimpleName()),
    /**
     * Text 对应数据库字段Text类型
     */
    Text(11, "Text"),
    /**
     * Date 时间类型
     */
    Date(12, Date.class.getSimpleName()),
    /**
     * 对象类型（该类型生成后可修改为其他对象类型）
     */
    Object(13, Object.class.getSimpleName());

    private Integer code;

    private String message;

    FieldType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}


