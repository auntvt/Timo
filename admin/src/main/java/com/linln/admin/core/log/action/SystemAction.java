package com.linln.admin.core.log.action;

import com.linln.admin.core.log.action.base.ActionMap;
import com.linln.admin.core.log.action.base.ResetLog;
import com.linln.admin.core.log.action.model.BusinessMethod;
import com.linln.admin.core.log.action.model.SystemMethod;

/**
 * @author 小懒虫
 * @date 2018/10/19
 */
public class SystemAction extends ActionMap {

    public static final String RUNTIME_EXCEPTION = "runtime_exception";

    @Override
    public void init() {
        // 系统异常行为
        putMethod(RUNTIME_EXCEPTION, new SystemMethod("系统异常","runtimeException"));
    }

    // 系统异常行为方法
    public void runtimeException(ResetLog resetLog){
        RuntimeException runtime = (RuntimeException) resetLog.getParam("e");
        StringBuilder message = new StringBuilder();
        message.append(runtime.toString());
        StackTraceElement[] stackTrace = runtime.getStackTrace();
        for (StackTraceElement stack : stackTrace) {
            message.append("\n\t").append(stack.toString());
        }
        resetLog.getActionLog().setMessage(String.valueOf(message));
    }
}
