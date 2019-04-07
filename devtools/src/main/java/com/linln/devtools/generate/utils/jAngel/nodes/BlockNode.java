package com.linln.devtools.generate.utils.jAngel.nodes;

import com.linln.devtools.generate.utils.jAngel.JAngel;

/**
 * 块节点
 * @author 小懒虫
 * @date 2019/4/2
 */
public class BlockNode extends Node{

    private String params;
    private boolean preBlock;
    private boolean sufBlock;

    /**
     * 获取块参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置为前块体
     */
    public void preBlock(){
        preBlock = true;
    }

    /**
     * 设置为后块体
     */
    public void sufBlock(){
        sufBlock = true;
    }

    /**
     * 设置块参数
     * @param params 参数
     */
    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String body() {
        if(body != null){
            return body;
        }
        StringBuilder builder = new StringBuilder();
        if (sufBlock){
            builder.append(" ");
        } else {
            builder.append(getIndex());
        }
        if(name != null){
            builder.append(name);
        }
        if(params != null){
            builder.append(" (").append(params).append(")");
        }
        builder.append(" {").append(JAngel.lineBreak);
        builder.append(childrenContent());
        builder.append(getIndex()).append("}");
        if (!preBlock) {
            builder.append(JAngel.lineBreak);
        }
        return builder.toString();
    }
}
