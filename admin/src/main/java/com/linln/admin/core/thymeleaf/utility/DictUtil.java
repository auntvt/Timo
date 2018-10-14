package com.linln.admin.core.thymeleaf.utility;

import com.linln.admin.core.enums.DictTypeEnum;
import com.linln.admin.system.domain.Dict;
import com.linln.admin.system.service.DictService;
import com.linln.core.utils.SpringContextUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 字典提取工具对象
 */
public class DictUtil {
    // 缓存字典数据
    private static Map<String, String> dictValues = new HashMap<>();
    private static Map<String, Map<String, String>> dictKeyValues = new HashMap<>();
    private static Map<String, Map<String, String>> dictEnums = new HashMap<>();

    /**
     * 获取字典值
     * @param label 字典标识
     */
    public static String value(String label){
        String value = "";
        if(dictValues.containsKey(label)){
            value = dictValues.get(label);
        }else {
            DictService dictService = SpringContextUtil.getBean(DictService.class);
            Dict dict = dictService.getName(label);
            if(dict != null){
                value = dict.getValue();
                dictValues.put(dict.getName(), dict.getValue());
            }
        }
        return value;
    }

    /**
     * 获取键值对集合
     * @param label 字典标识
     */
    public static Map<String, String> keyValueList(String label){
        Map<String, String> value = null;
        if(dictKeyValues.containsKey(label)){
            value = dictKeyValues.get(label);
        }else {
            DictService dictService = SpringContextUtil.getBean(DictService.class);
            Dict dict = dictService.getName(label);
            if(dict != null && dict.getType().equals(DictTypeEnum.KEY_VALUE.getCode())){
                String dictValue = dict.getValue();
                String[] outerSplit = dictValue.split("\\r\\n");
                Map<String, String> map = new HashMap<>();
                for (String osp : outerSplit) {
                    String[] split = osp.split(":");
                    map.put(split[0], split[1]);
                }
                dictKeyValues.put(dict.getName(), map);
                value = dictKeyValues.get(label);
            }
        }
        return value;
    }

    /**
     * 获取键值对数据
     * @param label 字典标识
     * @param key 键值对标识
     */
    public static String keyValue(String label, String key){
        Map<String, String> list = DictUtil.keyValueList(label);
        if(list != null){
            return list.get(key);
        }else{
            return "";
        }
    }

    /**
     * 获取枚举集合
     * @param label 字典标识
     */
    public static Map<String, String> enumValueList(String label){
        Map<String, String> value = null;
        if(dictEnums.containsKey(label)){
            value = dictEnums.get(label);
        }else try {
            DictService dictService = SpringContextUtil.getBean(DictService.class);
            Dict dict = dictService.getName(label);
            if(dict != null && dict.getType().equals(DictTypeEnum.ENUM_VALUE.getCode())){
                Class<?> enumClass = Class.forName(dict.getValue());
                Object[] objects = enumClass.getEnumConstants();
                Method getCode = enumClass.getMethod("getCode");
                Method getMessage = enumClass.getMethod("getMessage");
                Map<String, String> map = new HashMap<>();
                for (Object obj : objects) {
                    Object iCode = getCode.invoke(obj);
                    Object iMessage = getMessage.invoke(obj);
                    map.put(String.valueOf(iCode), String.valueOf(iMessage));
                }
                dictEnums.put(dict.getName(), map);
                value = dictEnums.get(label);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return value;
    }

    /**
     * 获取枚举数据
     * @param label 字典标识
     * @param code 枚举code
     */
    public static String enumValue(String label, String code){
        Map<String, String> list = DictUtil.enumValueList(label);
        if(list != null){
            return list.get(code);
        }else{
            return "";
        }
    }

    /**
     * 封装数据状态字典
     * @param status 状态
     */
    public static String dataStatus(Byte status){
        String label = "DATA_STATUS";
        return DictUtil.keyValue(label, String.valueOf(status));
    }

    /**
     * 清除缓存中指定的数据
     * @param label 字典标识
     */
    public static void clearCache(String label){
        if (dictValues.containsKey(label)){
            dictValues.remove(label);
        }else if (dictKeyValues.containsKey(label)){
            dictKeyValues.remove(label);
        }else dictEnums.remove(label);
    }
}
