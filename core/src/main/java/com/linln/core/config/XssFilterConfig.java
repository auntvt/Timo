package com.linln.core.config;

import com.linln.core.config.properties.ProjectProperties;
import com.linln.core.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * xss过滤拦截器
 * @author 小懒虫
 * @date 2018/12/9
 */
@Configuration
public class XssFilterConfig {
    private static final int FILTER_ORDER = 1;

    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean(ProjectProperties properties) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new XssFilter());
        registration.setOrder(FILTER_ORDER);
        registration.setEnabled(properties.isXssEnabled());
        registration.addUrlPatterns(properties.getXssUrlPatterns().split(","));
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", properties.getXssExcludes());
        registration.setInitParameters(initParameters);
        return registration;
    }
}
