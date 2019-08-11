package com.linln.devtools.generate.utils.jAngel.nodes;

import com.linln.devtools.generate.utils.jAngel.JAngel;
import com.linln.devtools.generate.utils.jAngel.JAngelContainer;
import com.linln.devtools.generate.utils.jAngel.select.Nodes;
import com.linln.devtools.generate.utils.jAngel.select.Select;
import com.linln.devtools.generate.utils.jAngel.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * java节点对象
 * @author 小懒虫
 * @date 2019/3/28
 */
public class Node<T> {

    /** 节点名称 */
    protected String name;

    /** 节点注解 */
    protected List<String> annotation = new ArrayList<>();

    /** 节点注释 */
    protected String comments;

    /** 节点级别 */
    protected int level = 0;

    /** 父节点 */
    protected Node parent;

    /** 子节点列表 */
    protected Nodes children = new Nodes();

    /** 代码容器对象 */
    protected JAngelContainer container;

    /** 节点内容本体 */
    protected String body;

    /** 导入的包列表 */
    protected Set<String> imports = new TreeSet<>();

    public Node() {
    }

    public Node(String name) {
        this.name = name;
    }

    /**
     * 获取节点名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置节点名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 添加注解
     * @param anno 注解数据
     */
    public Node pushAnnotation(String anno) {
        annotation.add(0, anno);
        return this;
    }

    /**
     * 添加注解
     * @param anno 注解数据
     */
    public Node addAnnotation(String anno) {
        annotation.add(anno);
        return this;
    }

    /**
     * 添加注解
     * @param clazz 注解类
     */
    public Node addAnnotation(Class clazz) {
        importClass(clazz);
        annotation.add(clazz.getSimpleName());
        return this;
    }

    /**
     * 添加注解
     * @param clazz 注解类
     * @param format 参数版式(不需要加'()'括号)
     */
    public Node addAnnotation(Class clazz, Format format) {
        importClass(clazz);
        imports.addAll(format.getImports());
        this.annotation.add(clazz.getSimpleName() + "(" +format.getContent() + ")");
        return this;
    }

    /**
     * 获取注释
     */
    public String getComments() {
        return comments != null ? comments : "";
    }

    /**
     * 设置注释
     * @param comments 注释
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * 获取当前节点的级别
     */
    public int getLevel() {
        return level;
    }

    /**
     * 获取当前节点的缩进距离
     */
    public String getIndex() {
        int level = this.level == 0 ? 1 : this.level;
        return StringUtil.repeat(JAngel.tabBreak, level - 1);
    }

    /**
     * 设置当前节点的级别
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * 获取父节点
     */
    public Node getParent() {
        return parent;
    }

    /**
     * 设置父节点
     * @param parent 父节点
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * 上一个节点
     */
    public Node prev() {
        if (parent != null) {
            Nodes children = parent.children();
            try {
                return children.get(children.indexOf(this) - 1);
            } catch (IndexOutOfBoundsException ignored){
                return null;
            }
        }
        return null;
    }

    /**
     * 下一个节点
     */
    public Node next() {
        if (parent != null) {
            Nodes children = parent.children();
            try {
                return children.get(children.indexOf(this) + 1);
            } catch (IndexOutOfBoundsException ignored){
                return null;
            }
        }
        return null;
    }

    /**
     * 获取子节点
     */
    public Nodes children() {
        return children.stream()
                .filter(node -> node.content().trim().length() > 0)
                .collect(Collectors.toCollection(Nodes::new));
    }

    /**
     * 添加子节点到首位
     * @param node 节点对象
     */
    @SuppressWarnings("unchecked")
    public void insert(Node node) {
        children.add(0, node);
        node.setContainer(container);
        container.importClass(node.imports);
        node.imports = container.getImports();
        node.level = level + 1;
        node.parent = this;
    }

    /**
     * 从指定的位置添加子节点
     * @param index 位置
     * @param node 节点对象
     */
    @SuppressWarnings("unchecked")
    public void insert(int index, Node node) {
        children.add(index, node);
        node.setContainer(container);
        container.importClass(node.imports);
        node.imports = container.getImports();
        node.level = level + 1;
        node.parent = this;
    }

    /**
     * 从尾部添加子节点
     * @param node 节点对象
     */
    @SuppressWarnings("unchecked")
    public void append(Node node) {
        children.add(node);
        node.setContainer(container);
        container.importClass(node.imports);
        node.imports = container.getImports();
        node.level = level + 1;
        node.parent = this;
    }

    /**
     * 从尾部添加一个空行子节点
     */
    public void appendLine() {
        Node node = new Node();
        node.body(JAngel.lineBreak);
        children.add(node);
    }

    /**
     * 移除本节点
     */
    public void remove() {
        if (parent != null){
            int index = parent.children.indexOf(this);
            try {
                Node prev = parent.children.get(index - 1);
                Node next = parent.children.get(index + 1);
                if (prev.body().trim().length() == 0 && next.body().trim().length() == 0){
                    parent.children.remove(next);
                }
            } catch (IndexOutOfBoundsException ignored){
            } finally {
                parent.children.remove(this);
            }

        }
    }

    /**
     * 获取容器内容
     */
    public JAngelContainer getContainer() {
        return container;
    }

    /**
     * 设置容器内容，返回本对象
     * @param container 容器对象
     */
    public void setContainer(JAngelContainer container) {
        this.container = container;
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
     * @param clazz 类Class
     */
    public void importClass(Class<?> clazz){
        importClass(clazz.getName());
    }

    /**
     * 获取节点本体
     */
    public String body(){
        if(body != null){
            return body;
        }
        return childrenContent();
    }

    /**
     * 设置节点内容
     * @param body 内容
     */
    public void body(String body){
        this.body = body;
    }

    /**
     * 获取子节点内容
     */
    public String childrenContent(){
        StringBuilder builder = new StringBuilder();
        children.forEach(node -> {
            builder.append(node.toString());
        });
        return builder.toString();
    }

    /**
     * 获取节点内容
     */
    public String content(){
        StringBuilder builder = new StringBuilder();
        if(comments != null){
            builder.append(getIndex()).append(getComments().trim()).append(JAngel.lineBreak);
        }
        if(annotation.size() > 0){
            StringBuilder anno = new StringBuilder();
            annotation.forEach(item -> anno.append(getIndex()).append("@").append(item).append(JAngel.lineBreak));
            builder.append(anno.toString());
        }
        builder.append(body());
        return builder.toString();
    }

    /*= 查询子节点区域 */
    /**
     * 查询子孙节点对应名称的首个节点
     * @param name 节点名称
     */
    public Node getByName(String name) {
        return Select.getByName(this, name);
    }
    /**
     * 查询子孙节点对应名称的节点集合
     * @param name 节点名称
     */
    public Nodes getNodesByName(String name) {
        return Select.getNodesByName(this, name);
    }
    /**
     * 查询子节点对应名称的首个节点
     * @param name 节点名称
     */
    public Node getChildByName(String name) {
        return Select.getChildByName(this, name);
    }

    /**
     * 查询子节点对应名称的节点集合
     * @param name 节点名称
     */
    public Nodes getChildNodesByName(String name) {
        return Select.getChildNodesByName(this, name);
    }

    @Override
    public String toString() {
        return content();
    }
}
