package com.linln.component.shiro.exception;

import com.linln.common.exception.advice.ResultExceptionAdvice;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 将异常通知添加到异常通知器中
 * @author 小懒虫
 * @date 2019/4/5
 */
@Configuration
public class AuthorizationExceptionAdviceConfig {

    @Bean
    public AuthorizationExceptionAdvice authorizationExceptionAdvice(ResultExceptionAdvice advice) {
        AuthorizationExceptionAdvice authorization = new AuthorizationExceptionAdvice();
        advice.putException(AuthorizationException.class, authorization);
        return authorization;
    }
}
