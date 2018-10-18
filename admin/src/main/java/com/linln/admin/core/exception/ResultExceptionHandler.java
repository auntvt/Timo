package com.linln.admin.core.exception;

import com.linln.admin.core.enums.ResultEnum;
import com.linln.admin.core.log.action.SystemAction;
import com.linln.admin.core.log.annotation.ActionLog;
import com.linln.core.utils.ResultVoUtil;
import com.linln.core.utils.SpringContextUtil;
import com.linln.core.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局统一异常处理
 * @author 小懒虫
 * @date 2018/8/14
 */
@ControllerAdvice
@Slf4j
public class ResultExceptionHandler {

    // 拦截自定义异常
    @ExceptionHandler(ResultException.class)
    @ResponseBody
    public ResultVo resultException(ResultException e){
        return ResultVoUtil.error(e.getCode(), e.getMessage());
    }

    // 拦截表单验证异常
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultVo bindException(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        return ResultVoUtil.error(bindingResult.getFieldError().getDefaultMessage());
    }

    // 拦截访问权限异常
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public ResultVo authorizationException(AuthorizationException e, HttpServletRequest request,
                                           HttpServletResponse response){
        Integer code = ResultEnum.NO_PERMISSIONS.getCode();
        String msg = ResultEnum.NO_PERMISSIONS.getMessage();

        // 获取异常信息
        Throwable cause = e.getCause();
        String message = cause.getMessage();
        Class<ResultVo> resultVoClass = ResultVo.class;

        // 判断无权限访问的方法返回对象是否为ResultVo
        if(!message.contains(resultVoClass.getName())){
            try {
                // 重定向到无权限页面
                String contextPath = request.getContextPath();
                ShiroFilterFactoryBean shiroFilter = SpringContextUtil.getBean(ShiroFilterFactoryBean.class);
                response.sendRedirect(contextPath+shiroFilter.getUnauthorizedUrl());
            } catch (IOException e1) {
                return ResultVoUtil.error(code, msg);
            }
        }
        return ResultVoUtil.error(code, msg);
    }

    // 拦截未知的运行时异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ActionLog(key = SystemAction.RUNTIME_EXCEPTION, action = SystemAction.class)
    public ResultVo runtimeException(RuntimeException e) {
        log.error("【系统异常】", e);
        return ResultVoUtil.error(500, "未知错误：EX4399");
    }
}
