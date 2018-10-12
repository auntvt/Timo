package com.linln.admin.core.enums;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class StatusEnumTest {

    @Test
    public void test(){
        try {
            Class<?> enumClass = Class.forName("com.linln.admin.core.enums.StatusEnum");
            Object[] objects = enumClass.getEnumConstants();
            Method getCode = enumClass.getMethod("getCode");
            Method getName = enumClass.getMethod("getMessage");
            for (Object obj : objects){
                // 3.调用对应方法，得到枚举常量中字段的值
                System.out.println("code=" + getCode.invoke(obj) + "; name=" + getName.invoke(obj));
            }

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("【系统异常】", e);
        }
    }
}