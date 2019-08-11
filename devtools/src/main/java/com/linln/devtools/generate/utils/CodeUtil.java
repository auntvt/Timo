package com.linln.devtools.generate.utils;

/**
 * 代码操作工具
 * @author 小懒虫
 * @date 2019/3/26
 */
public class CodeUtil {

    /** 字符编码 */
    public static final String ENCODE = "UTF-8";

    /** 后台模块名称 */
    public static final String ADMIN = "admin";

    /** 业务模块名称 */
    public static final String MODULES = "modules";

    /** maven源码目录 */
    public static final String MAVEN_SOURCE_PATH = "/src/main";

    /** 系统默认的换行符 */
    public static String lineBreak = System.getProperty("line.separator");

    /** 制表符 */
    public static String tabBreak = blank(4);

    /**
     * 获取指定次数的空格符
     * @param repeat 拼接次数
     */
    public static String blank(int repeat){
        return repeat(" ", repeat);
    }

    /**
     * 重复拼接指定的字符串
     * @param str 字符串
     * @param repeat 拼接次数
     */
    public static String repeat(String str, int repeat){
        StringBuilder repeatStr = new StringBuilder("");
        for (int i = 0; i < repeat; i++) {
            repeatStr.append(str);
        }
        return repeatStr.toString();
    }

    /**
     * 将访问地址转换为权限字符
     * @param requestMapping 请求映射地址
     */
    public static String urlToPerms(String requestMapping){
        String auth = requestMapping.replace("/", ":");
        return auth.substring(1);
    }
}
