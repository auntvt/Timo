package com.linln.common.exception.advice;

/**
 * 异常通知器接口
 * @author 小懒虫
 * @date 2019/4/5
 */
public interface ExceptionAdvice {

    /**
     * 运行
     * @param e 异常对象
     */
    void run(RuntimeException e);
}
