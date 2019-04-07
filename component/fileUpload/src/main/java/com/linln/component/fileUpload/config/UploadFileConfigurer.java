package com.linln.component.fileUpload.config;

import com.linln.common.config.properties.ProjectProperties;
import com.linln.common.utils.SpringContextUtil;
import com.linln.component.fileUpload.FileUpload;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 小懒虫
 * @date 2018/11/3
 */
@Configuration
public class UploadFileConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ProjectProperties properties = SpringContextUtil.getBean(ProjectProperties.class);
        registry.addResourceHandler(properties.getStaticPathPattern()).addResourceLocations("file:" + FileUpload.getUploadPath());
    }
}
