package com.linln.admin.core.exception;

import com.linln.admin.core.enums.ResultEnum;
import lombok.Getter;

/**
 * 自定义异常对象
 * @author 小懒虫
 * @date 2018/8/14
 */
@Getter
public class ResultException extends RuntimeException {

    private Integer code;

    /**
     * 统一异常处理
     * @param resultEnum 状态枚举
     */
    public ResultException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    /**
     * 统一异常处理
     * @param code 状态码
     * @param message 提示信息
     */
    public ResultException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
