package com.linln;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@SpringBootApplication
@EnableJpaAuditing                  // 使用jpa自动赋值
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
