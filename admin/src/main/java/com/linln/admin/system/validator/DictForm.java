package com.linln.admin.system.validator;

import com.linln.admin.system.domain.Dict;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Data
public class DictForm extends Dict implements Serializable {
    private Object entity;
    @NotEmpty(message = "字典标识不能为空")
    private String name;
    @NotEmpty(message = "字典标题不能为空")
    private String title;
    @NotNull(message = "字典类型不能为空")
    private Byte type;
    @NotEmpty(message = "字典值不能为空")
    private String value;
}
