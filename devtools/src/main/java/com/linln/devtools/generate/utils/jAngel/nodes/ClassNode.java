package com.linln.devtools.generate.utils.jAngel.nodes;

import com.linln.devtools.generate.utils.jAngel.JAngel;

import java.util.ArrayList;
import java.util.List;

/**
 * java类节点
 * @author 小懒虫
 * @date 2019/3/28
 */
public class ClassNode extends Node{

    public static final String CLASS = "class";
    public static final String INTERFACE = "interface";
    public static final String ENUM = "enum";

    // 类的类型
    private String type = "class";
    // 继承的父类
    private String extClass;
    // 实现接口
    private List<String> implClass = new ArrayList<>();

    public ClassNode(String className) {
        this.name = className;
    }

    /**
     * 获取类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取继承的父类
     */
    public String getExtends() {
        return extClass;
    }

    /**
     * 设置继承的父类
     * @param extClass 父类名称
     */
    public void setExtends(String extClass) {
        this.extClass = extClass;
    }

    /**
     * 设置继承的父类
     * @param clazz 父类
     */
    public void setExtends(Class<?> clazz) {
        importClass(clazz);
        this.extClass = clazz.getSimpleName();
    }

    /**
     * 获取实现的接口集合
     */
    public List<String> getImplements() {
        return implClass;
    }

    /**
     * 设置实现的接口
     * @param impl 接口类名称
     */
    public void addImplements(String impl) {
        this.implClass.add(impl);
    }

    /**
     * 设置实现的接口
     * @param clazz 接口
     */
    public void addImplements(Class<?> clazz) {
        importClass(clazz);
        this.implClass.add(clazz.getSimpleName());
    }

    /**
     * 获取节点内容
     */
    @Override
    public String body(){
        StringBuilder builder = new StringBuilder();
        builder.append("public ").append(type).append(" ").append(name);
        builder.append(extClass != null ? " extends " + extClass : "");
        builder.append(implClass.size() > 0 ? " implements" : "");
        for (int i = 0; i < implClass.size(); i++) {
            builder.append(" ").append(implClass.get(i));
            if (i != implClass.size() - 1){
                builder.append(",");
            }
        }
        builder.append(" {").append(JAngel.lineBreak);
        builder.append(childrenContent());
        builder.append("}");
        return builder.toString();
    }

}
