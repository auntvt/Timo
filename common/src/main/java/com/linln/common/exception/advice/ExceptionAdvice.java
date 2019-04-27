package com.linln.common.exception.advice;

/**
 * 异常通知器接口
 * @author 小懒虫
 * @date 2019/4/5
 */
public interface ExceptionAdvice {
    public void run(RuntimeException e);
}
