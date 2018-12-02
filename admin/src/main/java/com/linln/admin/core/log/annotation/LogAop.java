package com.linln.admin.core.log.annotation;

import com.linln.admin.core.log.action.base.ResetLog;
import com.linln.admin.core.log.action.model.ActionModel;
import com.linln.admin.core.log.action.model.BusinessMethod;
import com.linln.admin.core.log.action.model.BusinessType;
import com.linln.admin.core.shiro.ShiroUtil;
import com.linln.admin.system.domain.ActionLog;
import com.linln.admin.core.log.action.base.ActionMap;
import com.linln.admin.system.service.ActionLogService;
import com.linln.core.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LogAop {
    private final static String defaultActionName = "default";

    @Pointcut("@annotation(com.linln.admin.core.log.annotation.ActionLog)")
    public void actionLog() {};

    @Around("actionLog()")
    public Object recordLog(ProceedingJoinPoint point) throws Throwable {
        // 先执行切入点，获取返回值
        Object proceed = point.proceed();


        /* 读取ActionLog注解消息 */
        Method targetMethod = ((MethodSignature)(point.getSignature())).getMethod();
        com.linln.admin.core.log.annotation.ActionLog anno =
                targetMethod.getAnnotation(com.linln.admin.core.log.annotation.ActionLog.class);
        // 获取name值
        String name = anno.name();
        // 获取message值
        String message = anno.message();
        // 获取key值
        String key = anno.key();
        // 获取行为模型
        Class<? extends ActionMap> action = anno.action();
        String name1 = action.getName();
        ActionMap instance = action.newInstance();
        Object actionModel = instance.get(!key.isEmpty() ? key : defaultActionName);
        Assert.notNull(actionModel, "无法获取日志的行为方法，请检查："+point.getSignature());


        // 封装日志实例对象
        ActionLog actionLog = new ActionLog();
        actionLog.setIpaddr(ShiroUtil.getIp());
        actionLog.setClazz(point.getTarget().getClass().getName());
        actionLog.setMethod(targetMethod.getName());
        actionLog.setType(((ActionModel) actionModel).getType());
        actionLog.setName(!name.isEmpty() ? name : ((ActionModel) actionModel).getName());
        actionLog.setMessage(message);

        //判断是否为普通实例对象
        if(actionModel instanceof BusinessType){
            actionLog.setMessage(((BusinessType) actionModel).getMessage());
        }else {
            // 重置日志-自定义日志数据
            ResetLog resetLog = new ResetLog();
            resetLog.setActionLog(actionLog);
            resetLog.setRetValue(proceed);
            resetLog.setJoinPoint(point);
            try {
                Method method = action.getDeclaredMethod(((BusinessMethod)actionModel).getMethod(), ResetLog.class);
                method.invoke(instance, resetLog);
                if(!resetLog.getRecord()) return proceed;
            } catch (NoSuchMethodException e) {
                log.error("获取行为对象方法错误！请检查方法名称是否正确！", e);
                e.printStackTrace();
            }
        }

        // 保存日志
        ActionLogService actionLogService =
                (ActionLogService) SpringContextUtil.getBean(ActionLogService.class);
        actionLogService.save(actionLog);

        return proceed;
    }
}
