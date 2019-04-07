package com.linln.common.enums;

import com.linln.common.constant.StatusConst;
import lombok.Getter;

/**
 * 数据库字段状态枚举
 * @author 小懒虫
 * @date 2018/8/14
 */
@Getter
public enum StatusEnum {

    OK(StatusConst.OK, "启用"),
    FREEZED(StatusConst.FREEZED, "冻结"),
    DELETE(StatusConst.DELETE, "删除");

    private Byte code;

    private String message;

    StatusEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}

