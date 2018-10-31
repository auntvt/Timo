package com.linln.core.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 通用方法工具类
 * @author 小懒虫
 * @date 2018/10/15
 */
public class ToolUtil {

    /**
     * 获取随机位数的字符串
     * @param length 随机位数
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // 获取ascii码中的字符 数字48-57 小写65-90 大写97-122
            int range = random.nextInt(75)+48;
            range = range<97?(range<65?(range>57?114-range:range):(range>90?180-range:range)):range;
            sb.append((char)range);
        }
        return sb.toString();
    }

    /**
     * 首字母转小写
     */
    public static String lowerFirst(String word){
        if(Character.isLowerCase(word.charAt(0)))
            return word;
        else
            return (new StringBuilder()).append(Character.toLowerCase(word.charAt(0))).append(word.substring(1)).toString();
    }

    /**
     * 首字母转大写
     */
    public static String upperFirst(String word){
        if(Character.isUpperCase(word.charAt(0)))
            return word;
        else
            return (new StringBuilder()).append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).toString();
    }

    /**
     * 获取项目根路径
     */
    public static String getProjectPath(){
        char[] filePath = ToolUtil.class.getResource("").getPath().toCharArray();
        char[] projectPath = ToolUtil.class.getResource("/").getPath().toCharArray();
        StringBuilder path = new StringBuilder();
        for (int i = 0; i < filePath.length; i++) {
            if(projectPath.length > i && filePath[i] == projectPath[i]){
                path.append(filePath[i]);
            }else {
                break;
            }
        }
        File file = new File(path.toString());
        return file.getAbsolutePath().replaceAll("\\\\","/");
    }

    /**
     * 将枚举转成List集合
     * @param enumClass 枚举类
     */
    public static Map<Long, String> enumToMap(Class<?> enumClass){
        Map<Long, String> map = new TreeMap<>();
        try {
        Object[] objects = enumClass.getEnumConstants();
        Method getCode = enumClass.getMethod("getCode");
        Method getMessage = enumClass.getMethod("getMessage");
        for (Object obj : objects) {
            Object iCode = getCode.invoke(obj);
            Object iMessage = getMessage.invoke(obj);
            map.put(Long.valueOf(String.valueOf(iCode)), String.valueOf(iMessage));
        }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return map;
    }

    /**
     * 根据枚举code获取枚举对象
     * @param enumClass 枚举类
     * @param code code值
     */
    public static Object enumCode(Class<?> enumClass, Object code){
        try {
            Object[] objects = enumClass.getEnumConstants();
            Method getCode = enumClass.getMethod("getCode");
            for (Object obj : objects) {
                Object iCode = getCode.invoke(obj);
                if(iCode.equals(code)){
                    return obj;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        }
        return "";
    }
}
