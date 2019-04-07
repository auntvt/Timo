package com.linln.devtools.generate.enums;

import lombok.Getter;

/**
 * 模块结构类型
 * @author 小懒虫
 * @date 2019/4/6
 */
@Getter
public enum ModuleType {
    ALONE(1, "独立模块"),
    ADMIN(2, "后台模块");

    private Integer code;

    private String message;

    ModuleType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
