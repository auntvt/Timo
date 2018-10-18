package com.linln.admin.system.validator;

import com.linln.admin.system.domain.ActionLog;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class ActionLogForm extends ActionLog implements Serializable {
    @NotEmpty(message = "标题不能为空")
    private String title;
}
