package com.linln.common.exception.interfaces;

/**
 * 结果枚举接口
 * @author 小懒虫
 * @date 2019/2/13
 */
public interface ResultInterface {

    /**
     * 获取状态编码
     * @return 编码
     */
    Integer getCode();

    /**
     * 获取提示信息
     * @return 提示信息
     */
    String getMessage();

}
