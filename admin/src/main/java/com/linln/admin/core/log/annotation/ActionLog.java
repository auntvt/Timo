package com.linln.admin.core.log.annotation;


import com.linln.admin.core.log.action.base.ActionMap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ActionLog {
    // 日志名称
    String name() default "";
    // 日志消息
    String message() default "";
    // 行为key
    String key() default "";
    // 行为类
    Class<? extends ActionMap> action() default ActionMap.class;
}
