package com.linln.devtools.generate.utils.jAngel.nodes;

import com.linln.devtools.generate.utils.jAngel.JAngel;

import java.util.ArrayList;
import java.util.List;

/**
 * java类节点
 * @author 小懒虫
 * @date 2019/3/28
 */
public class ClassNode extends Node {

    public static final String CLASS = "class";
    public static final String INTERFACE = "interface";
    public static final String ENUM = "enum";

    /** 类的类型 */
    private String type = "class";

    /** 继承的父类 */
    private List<String> extClass = new ArrayList<>();

    /** 实现接口 */
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
        return extClass.get(0);
    }

    /**
     * 获取继承的父类列表
     */
    public List<String> getExtendsList() {
        return extClass;
    }

    /**
     * 添加继承的父类
     * @param clazz 父类名称
     */
    public void addExtends(String clazz) {
        this.extClass.add(clazz);
    }

    /**
     * 添加继承的父类
     * @param clazz 父类
     */
    @SuppressWarnings("unchecked")
    public void addExtends(Class<?> clazz) {
        importClass(clazz);
        this.extClass.add(clazz.getSimpleName());
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
    @SuppressWarnings("unchecked")
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
        builder.append(extClass.size() > 0 ? " extends" : "");
        for (int i = 0; i < extClass.size(); i++) {
            builder.append(" ").append(extClass.get(i));
            if (i != extClass.size() - 1){
                builder.append(",");
            }
        }
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
