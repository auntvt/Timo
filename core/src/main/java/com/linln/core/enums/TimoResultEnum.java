package com.linln.core.enums;

import lombok.Getter;

/**
 * 通用状态信息
 * @author 小懒虫
 * @date 2018/10/15
 */
@Getter
public enum TimoResultEnum {

    SUCCESS(200, "成功"),
    ERROR(400, "错误");

    private Integer code;

    private String message;

    TimoResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
