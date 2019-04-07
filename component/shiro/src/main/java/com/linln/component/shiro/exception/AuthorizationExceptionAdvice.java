package com.linln.component.shiro.exception;

import com.linln.common.enums.ResultEnum;
import com.linln.common.exception.advice.ExceptionAdvice;
import com.linln.common.utils.HttpServletUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.SpringContextUtil;
import com.linln.common.vo.ResultVo;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截访问权限异常通知器
 * @author 小懒虫
 * @date 2018/8/14
 */
public class AuthorizationExceptionAdvice implements ExceptionAdvice {

    @Override
    public ResultVo run(RuntimeException e) {
        Integer code = ResultEnum.NO_PERMISSIONS.getCode();
        String msg = ResultEnum.NO_PERMISSIONS.getMessage();

        // 获取异常信息
        Throwable cause = e.getCause();
        String message = cause.getMessage();
        Class<ResultVo> resultVoClass = ResultVo.class;

        // 判断无权限访问的方法返回对象是否为ResultVo
        if(!message.contains(resultVoClass.getName())){
            HttpServletRequest request = HttpServletUtil.getRequest();
            HttpServletResponse response = HttpServletUtil.getResponse();
            try {
                // 重定向到无权限页面
                String contextPath = request.getContextPath();
                ShiroFilterFactoryBean shiroFilter = SpringContextUtil.getBean(ShiroFilterFactoryBean.class);
                response.sendRedirect(contextPath + shiroFilter.getUnauthorizedUrl());
            } catch (IOException e1) {
                return ResultVoUtil.error(code, msg);
            }
        }
        return ResultVoUtil.error(code, msg);
    }
}
