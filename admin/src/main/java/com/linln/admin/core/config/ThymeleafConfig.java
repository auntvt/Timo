package com.linln.admin.core.config;

import com.linln.admin.core.thymeleaf.TimoDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig {

    /**
     * 配置自定义的CusDialect，用于整合thymeleaf模板
     */
    @Bean
    public TimoDialect getTimoDialect(){
        return new TimoDialect();
    }
}
