package com.linln.component.thymeleaf.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.linln.component.thymeleaf.TimoDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Configuration
public class ThymeleafConfig {

    /**
     * 配置自定义的CusDialect，用于整合thymeleaf模板
     */
    @Bean
    public TimoDialect getTimoDialect() {
        return new TimoDialect();
    }

    /**
     * 配置shiro扩展标签，用于控制权限按钮的显示
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
