package com.linln.devtools.generate.enums;

import lombok.Getter;

/**
 * 代码生成-模块结构类型
 * @author 小懒虫
 * @date 2019/4/6
 */
@Getter
public enum ModuleType {
    /**
     * 会在业务组(modules)中生成一个新的模块(业务模块)，可以给前台模块使用
     */
    ALONE(1, "独立模块"),
    /**
     * 将全部文件生成到后台模块(admin)中，复用性不强，如果只是开发后台管理项目，可直接使用这个结构
     */
    ADMIN(2, "后台模块");

    private Integer code;

    private String message;

    ModuleType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
