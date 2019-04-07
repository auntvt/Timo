package com.linln.devtools.generate.utils.jAngel.select;

import com.linln.devtools.generate.utils.jAngel.nodes.Node;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 节点查询工具
 * @author 小懒虫
 * @date 2019/4/2
 */
public class Select {

    /**
     * 查询子孙节点对应名称的首个节点
     * @param name 节点名称
     */
    public static Node getByName(Node node, String name) {
        for (Node sub : node.children()) {
            if (sub.getName().equals(name)) {
                return sub;
            }
            Node nest = getByName(sub, name);
            if (nest != null){
                return nest;
            }
        }
        return null;
    }

    /**
     * 查询子孙节点对应名称的节点集合
     * @param name 节点名称
     */
    public static Nodes getNodesByName(Node node, String name) {
        Nodes nodes = new Nodes();
        for (Node sub : node.children()) {
            if (sub.getName().equals(name)) {
                nodes.add(sub);
            }
            nodes.addAll(getNodesByName(sub, name));
        }
        return nodes;
    }

    /**
     * 查询子节点对应名称的首个节点
     * @param name 节点名称
     */
    public static Node getChildByName(Node node, String name) {
        for (Node sub : node.children()) {
            if (sub.getName().equals(name)) {
                return sub;
            }
        }
        return null;
    }

    /**
     * 查询子节点对应名称的节点集合
     * @param name 节点名称
     */
    public static Nodes getChildNodesByName(Node node, String name) {
        return node.children().stream()
                .filter(sub -> sub.getName().equals(name))
                .collect(Collectors.toCollection(Nodes::new));
    }
}
