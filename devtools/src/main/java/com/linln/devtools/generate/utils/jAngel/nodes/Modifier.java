package com.linln.devtools.generate.utils.jAngel.nodes;

/**
 * 变量修饰符
 * @author 小懒虫
 * @date 2019/3/28
 */
public class Modifier extends Node{

    public static String PUBLIC = "public";
    public static String PROTECTED = "protected";
    public static String PRIVATE = "private";

    /** 变量类型 */
    protected String type;

    /** 访问控制符 */
    private String accessSym;

    /** 静态修饰符 */
    private boolean staticSym;

    /** 常量修饰符 */
    private boolean finalSym;

    /** 抽象修饰符 */
    private boolean abstractSym;

    /**
     * 获取变量类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置变量类型
     * @param type 变量类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 设置变量类型
     * @param clazz 类对象
     */
    @SuppressWarnings("unchecked")
    public void setType(Class<?> clazz) {
        importClass(clazz);
        this.type = clazz.getSimpleName();
    }

    /**
     * 访问控制修饰
     */
    public Modifier access(){
        accessSym = PUBLIC;
        return this;
    }

    /**
     * 访问控制修饰
     * @param symbol 符号
     */
    public Modifier accessSym(String symbol){
        accessSym = symbol;
        return this;
    }

    /**
     * 静态修饰
     */
    public Modifier staticSym(){
        staticSym = true;
        return this;
    }

    /**
     * 常量修饰
     */
    public Modifier finalSym(){
        finalSym = true;
        return this;
    }

    /**
     * 抽象修饰
     */
    public Modifier abstractSym(){
        abstractSym = true;
        return this;
    }

    /**
     * 获取需要的修饰符
     */
    protected String getModifier() {
        StringBuilder builder = new StringBuilder();
        if(accessSym != null){
            builder.append(accessSym).append(" ");
        }
        if(staticSym){
            builder.append("static ");
        }
        if(finalSym){
            builder.append("final ");
        }
        if(abstractSym){
            builder.append("abstract ");
        }
        if(type != null){
            builder.append(type).append(" ");
        }
        return builder.toString();
    }

}
