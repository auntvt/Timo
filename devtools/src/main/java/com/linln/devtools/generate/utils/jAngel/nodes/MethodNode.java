package com.linln.devtools.generate.utils.jAngel.nodes;

import com.linln.devtools.generate.utils.jAngel.JAngel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 方法节点
 * @author 小懒虫
 * @date 2019/3/28
 */
public class MethodNode extends Modifier{

    /** 方法参数列表 */
    private List<Format> params = new ArrayList<>();

    public MethodNode(){
    }

    /**
     * 创建一个方法节点
     * @param name 节点名称
     */
    public MethodNode(String name){
        this.name = name;
    }

    /**
     * 创建一个方法节点
     * @param name 节点名称
     * @param classType 方法类型
     */
    @SuppressWarnings("unchecked")
    public MethodNode(String name, Class<?> classType){
        this.name = name;
        this.type = classType.getSimpleName();
        importClass(classType);
    }

    /**
     * 获取方法参数
     */
    @SuppressWarnings("unchecked")
    public String getParamAll(){
        StringBuilder builder = new StringBuilder("(");
        if(params.size() > 0){
            params.forEach(format -> {
                builder.append(format.getContent()).append(", ");
                imports.addAll(format.getImports());
            });
            builder.delete(builder.length() - 2, builder.length());
        }
        return builder.append(")").toString();
    }

    /**
     * 添加方法参数
     * @param formats 参数版式集合
     */
    public void addParam(Format... formats){
        params.addAll(Arrays.asList(formats));
    }

    @Override
    public String body() {
        if(body != null){
            return body;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(getIndex()).append(getModifier()).append(name);
        builder.append(getParamAll()).append(" {").append(JAngel.lineBreak);
        builder.append(childrenContent());
        builder.append(getIndex()).append("}").append(JAngel.lineBreak);
        return builder.toString();
    }

}
