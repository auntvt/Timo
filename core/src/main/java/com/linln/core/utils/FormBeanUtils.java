package com.linln.core.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

/**
 * 复制表单验证对象数据
 */
public class FormBeanUtils {
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
        BeanUtils.copyProperties(source,target,defaultIgnoreProperties);
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
        BeanUtils.copyProperties(source,target,jointIgnoreProperties);
    }
}
