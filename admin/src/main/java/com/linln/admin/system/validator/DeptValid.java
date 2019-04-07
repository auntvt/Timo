package com.linln.admin.system.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 小懒虫
 * @date 2018/12/02
 */
@Data
public class DeptValid implements Serializable {
	@NotEmpty(message = "部门名称不能为空")
	private String title;
    @NotNull(message = "父级部门不能为空")
    private Long pid;
}
