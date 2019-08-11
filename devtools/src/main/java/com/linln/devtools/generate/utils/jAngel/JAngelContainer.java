package com.linln.devtools.generate.utils.jAngel;

import java.util.Set;
import java.util.TreeSet;

/**
 * 数据容器，用于存储java源码数据
 * @author 小懒虫
 * @date 2019/3/28
 */
public class JAngelContainer {

    /** 导入的包列表 */
    private Set<String> imports = new TreeSet<>();

    /**
     * 获取包列表数据
     */
    public String getImportAll() {
        StringBuilder builder = new StringBuilder();
        StringBuilder javaBuilder = new StringBuilder();
        imports.forEach(name -> {
            if (name.startsWith("import java")) {
                javaBuilder.append(name);
            } else {
                builder.append(name);
            }
        });
        if(builder.length() > 0 && javaBuilder.length() > 0){
            builder.append(JAngel.lineBreak);
        }
        if(javaBuilder.length() > 0){
            builder.append(javaBuilder);
        }
        return builder.toString();
    }

    /** 获取列表集合对象 */
    public Set<String> getImports() {
        return imports;
    }

    /**
     * 添加一行包含import代码
     * @param className 类名称
     */
    public void importClass(String className){
        if(!className.startsWith("java.lang")){
            imports.add("import " + className + ";" + JAngel.lineBreak);
        }
    }

    /**
     * 添加一行包含import代码
     * @param imports import集合
     */
    public void importClass(Set<String> imports){
        this.imports.addAll(imports);
    }

    /**
     * 添加多个包含import代码
     */
    public void importClass(Class<?> clazz){
        this.importClass(clazz.getName());
    }
}
