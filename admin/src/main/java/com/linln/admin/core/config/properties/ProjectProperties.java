package com.linln.admin.core.config.properties;

import com.linln.core.utils.ToolUtil;
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
    private String fileUploadPath;
    private String staticPathPattern = "/upload/";

    public String getFileUploadPath() {
        if(fileUploadPath == null){
            return ToolUtil.getProjectPath() + "/upload/";
        }
        return fileUploadPath;
    }
}
