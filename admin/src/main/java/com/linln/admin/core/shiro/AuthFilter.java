package com.linln.admin.core.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 暂时没有使用
 * @author 小懒虫
 * @date 2018/8/14
 */
public class AuthFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
        String url = shiroRequest.getServletPath();
        Subject subject = SecurityUtils.getSubject();
        if(subject.isPermitted(url)){
            return true;
        }
        return false;
    }
}
