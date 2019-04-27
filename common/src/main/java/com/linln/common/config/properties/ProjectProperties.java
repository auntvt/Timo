package com.linln.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目配置项
 * @author 小懒虫
 * @date 2018/11/6
 */
@Data
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {
    // 是否开启验证码
    private boolean captchaOpen = false;
    // xss防护设置
    private ProjectProperties.Xxs xxs = new ProjectProperties.Xxs();

    /**
     *  xss防护设置
     */
    @Data
    public static class Xxs {
        // xss防护开关
        private boolean enabled = true;
        // 拦截规则，可通过“,”隔开多个
        private String urlPatterns = "/*";
        // 忽略规则，可通过“,”隔开多个
        private String excludes = "/favicon.ico,/img/*,/js/*,/css/*,/lib/*";
    }
}
