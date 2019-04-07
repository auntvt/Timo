package com.linln.admin.system.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Data
public class MenuValid implements Serializable {
    @NotEmpty(message = "标题不能为空")
    private String title;
    @NotNull(message = "父级菜单不能为空")
    private Long pid;
    @NotEmpty(message = "url地址不能直接为空，可以输入#代替！")
    private String url;
    @NotEmpty(message = "权限标识不能直接为空，可以输入#代替！")
    private String perms;
    @NotNull(message = "菜单类型不能为空")
    private Byte type;
}
