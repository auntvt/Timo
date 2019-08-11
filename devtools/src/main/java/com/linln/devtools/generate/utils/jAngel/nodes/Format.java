package com.linln.devtools.generate.utils.jAngel.nodes;

import com.linln.devtools.generate.utils.jAngel.JAngel;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 版式实体，使用占位符方式处理数据
 * @author 小懒虫
 * @date 2019/3/28
 */
public class Format {

    /** 类标志 */
    private static final String SIGN_T = "$T";

    /** 字符串标志 */
    private static final String SIGN_S = "$S";

    /** 正则字符串标志 */
    private static final String SIGN_R = "$R";

    /** 普通的占位标志 */
    private static final String SIGN_O = "$O";

    /** 处理后的数据 */
    private String content;

    /** 导入的包列表 */
    private Set<String> imports = new TreeSet<>();

    /**
     * 创建一个版式对象
     * @param format 模板数据
     * @param args 参数列表
     */
    public Format(String format, Object... args){
        StringBuffer buffer = new StringBuffer() ;
        Pattern pattern = Pattern.compile("\\$[A-Z]");
        Matcher matcher = pattern.matcher(format);
        int index = 0;
        while(matcher.find()){
            switch (matcher.group()){
                case SIGN_T:
                    Class<?> clazz = (Class<?>) args[index++];
                    if(!clazz.getName().startsWith("java.lang")){
                        imports.add("import " + clazz.getName() + ";" + JAngel.lineBreak);
                    }
                    matcher.appendReplacement(buffer, clazz.getSimpleName());
                    break;
                case SIGN_S:
                    matcher.appendReplacement(buffer, "\"" + String.valueOf(args[index++]) + "\"");
                    break;
                case SIGN_R:
                    String value = String.valueOf(args[index++]);
                    value = value.replace("\\", "\\\\\\\\");
                    value = value.replace("$", "\\$");
                    matcher.appendReplacement(buffer, "\"" + value + "\"");
                    break;
                case SIGN_O:
                default:
                    matcher.appendReplacement(buffer, String.valueOf(args[index++]));
            }
        }
        matcher.appendTail(buffer);
        content = buffer.toString();
    }

    /**
     * 创建一个版式对象
     * @param format 模板数据
     * @param args 参数列表
     */
    public static Format of(String format, Object... args){
        return new Format(format, args);
    }

    /**
     * 处理后的数据
     */
    public String getContent() {
        return content;
    }

    /**
     * 获取导入包的列表
     */
    public Set<String> getImports() {
        return imports;
    }

}
