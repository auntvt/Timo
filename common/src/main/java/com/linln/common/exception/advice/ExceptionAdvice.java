package com.linln.common.exception.advice;

import com.linln.common.vo.ResultVo;

/**
 * 异常通知器接口
 * @author 小懒虫
 * @date 2019/4/5
 */
public interface ExceptionAdvice {
    public ResultVo run(RuntimeException e);
}
