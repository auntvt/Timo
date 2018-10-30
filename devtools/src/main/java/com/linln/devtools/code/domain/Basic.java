package com.linln.devtools.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 小懒虫
 * @date 2018/10/21
 */
@Data
public class Basic {
    private String projectPath;
    private String packagePath;
    private String author;
    private String genTitle;
    private String genModule;
    private Long genPMenu;
    private String tablePrefix;
    private String tableName;
    private String tableEntity;
}
