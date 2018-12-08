package com.linln.core.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 复制表单验证对象数据
 * @author 小懒虫
 * @date 2018/10/15
 */
public class FormBeanUtil {
    // 忽略的Bean属性
    private static String[] defaultIgnoreProperties = new String[]{
            "createDate",
            "updateDate",
            "createBy",
            "updateBy",
            "status"
    };

    /**
     * 封装spring的BeanUtils工具对象，忽略部分Bean属性
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        BeanUtils.copyProperties(source, target, defaultIgnoreProperties);
        referenceEntity(source, target);
    }

    /**
     * 封装spring的BeanUtils工具对象，自定义忽略Bean属性
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
        // 合并两个数组
        String[] jointIgnoreProperties = new String[defaultIgnoreProperties.length + ignoreProperties.length];
        System.arraycopy(defaultIgnoreProperties, 0, jointIgnoreProperties, 0, defaultIgnoreProperties.length);
        System.arraycopy(ignoreProperties, 0, jointIgnoreProperties, defaultIgnoreProperties.length, ignoreProperties.length);
        BeanUtils.copyProperties(source, target, jointIgnoreProperties);
        referenceEntity(source, target);
    }


    /**
     * 将实体对象装入验证对象中
     * @param source 源对象
     * @param target 目标对象
     */
    private static void referenceEntity(Object source, Object target){
        PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), "entity");
        if(sourcePd != null){
            Method writeMethod = sourcePd.getWriteMethod();
            if(writeMethod != null){
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }
                try {
                    writeMethod.invoke(source, target);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new FatalBeanException("无法将实体对象装入认证对象中");
                }
            }
        }
    }
}
