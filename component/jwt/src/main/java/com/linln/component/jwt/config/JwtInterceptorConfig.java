package com.linln.component.jwt.config;

import com.linln.component.jwt.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * jwt权限配置拦截器
 * @author 小懒虫
 * @date 2019/4/12
 */
@Configuration
@ConditionalOnProperty(name = "project.jwt.pattern-path", havingValue = "true")
public class JwtInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/api/**");
    }
}
