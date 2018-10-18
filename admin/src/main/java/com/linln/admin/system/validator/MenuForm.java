package com.linln.admin.system.validator;

import com.linln.admin.system.domain.Menu;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Data
public class MenuForm extends Menu implements Serializable {
    private Object entity;
    @NotEmpty(message = "标题不能为空")
    private String title;
    @NotNull(message = "父级菜单不能为空")
    private Long pid;
    @NotEmpty(message = "url地址不能为空")
    private String url;
    @NotNull(message = "菜单类型不能为空")
    private Byte type;
}
