package com.linln.common.exception.advice;

import com.linln.common.utils.SpringContextUtil;
import com.linln.common.vo.ResultVo;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常通知器
 * {可将其他模块的异常整合起来，由全局统一异常处理}
 * @author 小懒虫
 * @date 2019/4/5
 */
@ControllerAdvice
public class ResultExceptionAdvice {
    // 异常通知集合
    private Map<Class<? extends Exception>, ExceptionAdvice> exceptions = new HashMap<>();
    // 运行切入程序集合
    private List<ExceptionAdvice> proceed = new ArrayList<>();

    // 添加通知异常
    public void putException(Class<? extends Exception> e, ExceptionAdvice advice){
        exceptions.put(e, advice);
    }

    // 添加切入程序
    public void putProceed(ExceptionAdvice advice){
        proceed.add(advice);
    }

    // 执行通知异常
    public ResultVo runtimeException(RuntimeException e){
        for (Class<? extends Exception> clazz : exceptions.keySet()) {
            if (clazz.isAssignableFrom(e.getClass())) {
                return exceptions.get(clazz).run(e);
            }
        }
        for (ExceptionAdvice ea : proceed) {
            ExceptionAdvice advice = SpringContextUtil.getBean(ea.getClass());
            advice.run(e);
        }
        return null;
    }
}
