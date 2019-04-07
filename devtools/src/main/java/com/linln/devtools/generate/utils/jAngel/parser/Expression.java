package com.linln.devtools.generate.utils.jAngel.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板表达式#{}
 * @author 小懒虫
 * @date 2019/4/3
 */
public class Expression {

    private Map<String, String> labels = new HashMap<>();

    /**
     * 创建模板表达式对象
     */
    public static Expression of(){
        return new Expression();
    }

    /**
     * 创建模板表达式对象
     * @param name #{标签名称}
     * @param value 标签值
     */
    public static Expression of(String name, String value){
        Expression expression = new Expression();
        expression.label(name, value);
        return expression;
    }

    /**
     * 添加一个标签
     * @param name #{标签名称}
     * @param value 标签值
     */
    public Expression label(String name, String value){
        labels.put(name, value);
        return this;
    }

    /**
     * 匹配模板
     * @param temp 模板数据
     * @return 匹配完成的新模板数据
     */
    public String matcher(String temp){
        int begin = 0;
        int revise = 0;
        int boundary = 0;
        StringBuilder builder = new StringBuilder();
        char[] chars = temp.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '#'){
                begin = i;
                boundary = 1;
            }
            if (chars[i] == '{'){
                if (boundary == 1 && begin == i - 1){
                    boundary = 2;
                }
            }
            if (chars[i] == '}'){
                if (boundary == 2){
                    String name = builder.substring(begin + revise + 2);
                    builder.delete(begin + revise, builder.length());
                    String value = labels.get(name);
                    if (value == null){
                        value = "!NULL";
                    }
                    revise += value.length() - (i - begin + 1);
                    builder.append(value);
                    boundary = 0;
                    continue;
                }
            }
            builder.append(chars[i]);
        }
        return builder.toString();
    }
}
