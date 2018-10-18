package com.linln.admin.core.log.action;

import com.linln.admin.core.enums.ResultEnum;
import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.core.exception.ResultException;
import com.linln.admin.core.log.action.base.ActionMap;
import com.linln.admin.core.log.action.base.ResetLog;
import com.linln.admin.system.domain.ActionLog;
import com.linln.core.utils.ReflexBeanUtil;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Table;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 通用：记录数据状态的行为
 *
 * @author 小懒虫
 * @date 2018/10/14
 */
public class StatusAction extends ActionMap {

    @Override
    public void init() {
        // 记录数据状态改变日志
        putMethod("default", "defaultMethod");
    }

    /**
     * 重新包装保存的数据行为方法
     *
     * @param resetLog ResetLog对象数据
     */
    @SuppressWarnings("unchecked")
    public static void defaultMethod(ResetLog resetLog) {
        if(resetLog.isSuccessRecord()){
            String param = (String) resetLog.getParam("param");
            StatusEnum statusEnum = StatusEnum.valueOf(param.toUpperCase());
            List<Long> idList = (List<Long>) resetLog.getParam("idList");
            resetLog.getActionLog().setMessage(statusEnum.getMessage() + "ID：" + idList.toString());
        }
    }
}
