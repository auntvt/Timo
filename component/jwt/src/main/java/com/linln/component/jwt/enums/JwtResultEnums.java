package com.linln.component.jwt.enums;

import com.linln.common.exception.interfaces.ResultInterface;
import lombok.Getter;

/**
 * jwt结果集枚举
 * @author 小懒虫
 * @date 2019/4/13
 */
@Getter
public enum JwtResultEnums implements ResultInterface {

    /**
     * token问题
     */
    TOKEN_ERROR(301, "token无效"),
    TOKEN_EXPIRED(302, "token已过期"),

    /**
     * 账号问题
     */
    AUTH_REQUEST_ERROR(401, "用户名或密码错误"),
    AUTH_REQUEST_LOCKED(402, "该账号已被冻结"),
    ;

    private Integer code;

    private String message;

    JwtResultEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
