package com.linln.devtools.generate.utils.jAngel.nodes;

import com.linln.devtools.generate.utils.jAngel.JAngel;

/**
 * java代码文档
 * @author 小懒虫
 * @date 2019/3/28
 */
public class Document extends Node{

    /** 包路径（包名） */
    private String packageName;

    /** 类节点 */
    private ClassNode clazz;

    public Document() {
    }

    /**
     * 获取包名
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * 获取文档的标准类
     */
    public ClassNode getClazz() {
        return clazz;
    }

    /**
     * 设置文档的标准类
     */
    public void setClazz(ClassNode clazz) {
        append(clazz);
        this.clazz = clazz;
        clazz.setContainer(container);
    }

    /**
     * 设置包名
     * @param packageName 包名
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String content() {
        container.importClass(imports);
        StringBuilder packageBuilder = new StringBuilder("package ");
        packageBuilder.append(this.packageName).append(";").append(JAngel.lineBreak);
        packageBuilder.append(JAngel.lineBreak);
        String importAll = container.getImportAll();
        if(importAll.length() > 0){
            packageBuilder.append(importAll).append(JAngel.lineBreak);
        }
        return packageBuilder + super.content();
    }
}
