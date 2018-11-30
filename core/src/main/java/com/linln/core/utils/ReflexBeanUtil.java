package com.linln.core.utils;

import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 反射获取JavaBean对象信息
 * @author 小懒虫
 * @date 2018/10/15
 */
public class ReflexBeanUtil {

    /**
     * 根据字段名获取Bean对象值
     * @param beanObject Bean对象
     * @param fieldName 字段名
     * @return Object对象
     */
    public static Object getField(Object beanObject, String fieldName) throws InvocationTargetException, IllegalAccessException {
        PropertyDescriptor beanObjectPd = BeanUtils.getPropertyDescriptor(beanObject.getClass(), fieldName);
        if(beanObjectPd != null){
            Method readMethod = beanObjectPd.getReadMethod();
            if(readMethod != null){
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                return readMethod.invoke(beanObject);
            }
        }
        return null;
    }
}
