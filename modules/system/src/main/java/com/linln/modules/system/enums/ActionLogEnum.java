package com.linln.modules.system.enums;

import lombok.Getter;

/**
 * @author 小懒虫
 * @date 2018/10/14
 */
@Getter
public enum ActionLogEnum {

    /**
     * 业务日志行为
     */
    BUSINESS((byte) 1, "业务"),
    /**
     * 用户登录日志行为
     */
    LOGIN((byte) 2, "登录"),
    /**
     * 系统日志行为（报错信息）
     */
    SYSTEM((byte) 3, "系统");

    private Byte code;

    private String message;

    ActionLogEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}
