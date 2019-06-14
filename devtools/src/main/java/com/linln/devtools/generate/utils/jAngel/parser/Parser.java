package com.linln.devtools.generate.utils.jAngel.parser;

import com.linln.devtools.generate.utils.jAngel.JAngel;
import com.linln.devtools.generate.utils.jAngel.nodes.*;
import com.linln.devtools.generate.utils.jAngel.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析模板
 * @author 小懒虫
 * @date 2019/3/29
 */
public class Parser {

    private Document document;
    private Set<String> imports = new TreeSet<>();
    private List<Node> pushNode = new ArrayList<>();
    private List<String> pushLine = new ArrayList<>();

    /**
     * 获取文档对象
     */
    public Document getDocument() {
        return document;
    }

    /**
     * 解析一行的数据
     * @param line 行数据
     */
    public void line(String line) {
        pushLine.add(line);
        // 判断该行是否为类声明
        if (document == null) {
            parseClass(line);
            parseImport(line);
        }else if(document.getClazz() != null){
            if(fieldNode(line)){
                return;
            }
            if(lineNode(line)){
                return;
            }
            if(methodNode(line)){
                return;
            }
            if(blockNode(line)){
                return;
            }
            if(StringUtil.matcher(line, '}') != -1){
                pushNode.remove(pushNode.size() - 1);
                if (StringUtil.matcher(line, '@') == -1){
                    pushLine.clear();
                }
            }
            if(line.trim().length() == 0){
                if (pushNode.size() >= 1){
                    pushNode.get(pushNode.size() - 1).appendLine();
                }
            }
        }
    }

    /**
     * 解析文档主类
     */
    private void parseClass(String line){
        String regex="\\b(class|interface|enum)\\b\\s.*\\{";
        Matcher m=Pattern.compile(regex).matcher(line);
        if(m.find()){
            String group = m.group();
            List<String> words = StringUtil.extWord(group.substring(0, group.length() - 1), ',');
            document = JAngel.create(words.get(1));
            document.getContainer().importClass(imports);
            ClassNode clazz = document.getClazz();
            clazz.setType(words.get(0));
            int ext = words.indexOf("extends");
            int impl = words.indexOf("implements");
            if (ext != -1){
                if (clazz.getType().equals(ClassNode.INTERFACE)) {
                    int size = impl != -1 ? impl : words.size();
                    for (int i = ext + 1; i < size; i++) {
                        clazz.addExtends(words.get(i));
                    }
                } else {
                    clazz.addExtends(words.get(ext + 1));
                }
            }
            if (impl != -1){
                for (int i = impl + 1; i < words.size(); i++) {
                    clazz.addImplements(words.get(i));
                }
            }
            pushNode.add(clazz);
            before(clazz);
        }
    }

    /**
     * 解析文档导入包
     */
    private void parseImport(String line){
        String regex="\\bimport\\bs*.*;";
        Matcher m=Pattern.compile(regex).matcher(line);
        if(m.find()){
            imports.add(m.group() + JAngel.lineBreak);
        }
    }

    /**
     * 解析类节点-属性
     */
    private boolean fieldNode(String line){
        int matcher = StringUtil.matcher(line, ';');
        if(matcher != -1 && !line.trim().startsWith("return")){
            String beforeAll = getBeforeAll();
            String regex="[\\w\\t ]*([\\w!\\[\\]]*(<.*>)?)?\\s+[\\w!]+\\s*(=+.+)?;";
            Matcher m=Pattern.compile(regex, Pattern.DOTALL).matcher(beforeAll);
            if(m.find()){
                FieldNode node = new FieldNode();
                String group = m.group();
                int key = StringUtil.matcher(group, '=');
                if(key != -1){
                    node.setValue(group.substring(key + 1, group.length() - 1).trim());
                    matcher = key;
                }
                List<String> list = StringUtil.extWord(group.substring(0, matcher));
                pushNode.get(pushNode.size() - 1).append(node);
                modifier(node, list);
                before(node);
                return true;
            }
        }
        return false;
    }

    /**
     * 解析类节点-行元素
     */
    private boolean lineNode(String line){
        int matcher = StringUtil.matcher(line, ';');
        if(matcher != -1){
            String beforeAll = getBeforeAll(true);
            LineNode node = new LineNode();
            // 设置行元素名称
            int index;
            if (beforeAll.trim().startsWith("return")){
                node.setName("return");
            }else if (beforeAll.contains(" new ") && (index = beforeAll.indexOf('(')) != -1){
                List<String> words = StringUtil.extWord(beforeAll.substring(0, index));
                node.setName(words.get(words.size() - 1));
            }else if ((index = StringUtil.matcher(beforeAll, '.')) != -1){
                node.setName(beforeAll.substring(0, index));
            }else if ((index = beforeAll.indexOf('(')) != -1){
                List<String> words = StringUtil.extWord(beforeAll.substring(0, index));
                node.setName(words.get(words.size() - 1));
            }else {
                List<String> words = StringUtil.extWord(beforeAll);
                node.setName(words.get(0));
            }
            node.body(beforeAll);
            pushNode.get(pushNode.size() - 1).append(node);
            before(node);
            return true;
        }
        return false;
    }

    /**
     * 解析类节点-方法
     */
    private boolean methodNode(String line){
        Node lastNode = pushNode.get(pushNode.size() - 1);
        if (lastNode instanceof ClassNode && StringUtil.matcher(line, '{') != -1){
            String beforeAll = getBeforeAll();
            String regex="[\\w\\t\\[\\] !<>,]*\\s+[\\w!]+\\s*\\(.*\\)\\s*\\{";
            Matcher m=Pattern.compile(regex, Pattern.DOTALL).matcher(beforeAll);
            if(m.find()){
                MethodNode node = new MethodNode();
                String group = m.group();
                List<String> list = StringUtil.extWord(group.substring(0, group.indexOf("(")));
                String params = group.substring(group.indexOf('(') + 1, group.lastIndexOf(')'));
                lastNode.append(node);
                node.addParam(new Format(params));
                modifier(node, list);
                before(node);
                pushNode.add(node);
                return true;
            }
        }
        return false;
    }

    /**
     * 解析类节点-块元素
     */
    private boolean blockNode(String line){
        boolean bool = false;
        int matcher = StringUtil.matcher(line, '{');
        if (matcher != -1){
            if (StringUtil.matcher(line, '@') != -1){
                Node node = new Node();
                node.setContainer(document.getContainer());
                pushNode.add(node);
            }else {
                Node lastNode = pushNode.get(pushNode.size() - 1);
                BlockNode node = new BlockNode();
                String trim = line.trim();
                if (trim.startsWith("}")) {
                    ((BlockNode) lastNode).preBlock();
                    node.sufBlock();
                    node.setName(trim.substring(1, StringUtil.matcher(trim, '{')).trim());
                    pushNode.remove(pushNode.size() - 1);
                    lastNode = pushNode.get(pushNode.size() - 1);
                    bool = true;
                }
                String regex="^\\w+|\\(.*\\)";
                Matcher m=Pattern.compile(regex).matcher(trim);
                int i = 1;
                while (m.find()){
                    String group = m.group();
                    if (i == 1 && !bool){
                        node.setName(group);
                    }
                    if (i == 2){
                        node.setParams(group.substring(1, group.length() - 1));
                    }
                    i++;
                }
                lastNode.append(node);
                pushNode.add(node);
                before(node);
            }
        }
        return bool;
    }

    /**
     * 添加修饰
     * @param node 节点
     * @param wordList 单词列表
     */
    private void modifier(Modifier node, List<String> wordList){
        if (wordList.size() > 0){
            node.setName(wordList.get(wordList.size() - 1));
            if (wordList.size() >= 2){
                node.setType(wordList.get(wordList.size() - 2));
            }
            if (wordList.contains("public")){
                node.accessSym("public");
            }
            if (wordList.contains("protected")){
                node.accessSym("protected");
            }
            if (wordList.contains("private")){
                node.accessSym("private");
            }
            if (wordList.contains("static")){
                node.staticSym();
            }
            if (wordList.contains("final")){
                node.finalSym();
            }
            if (wordList.contains("abstract")){
                node.abstractSym();
            }
        }
    }

    /**
     * 获取前面的全部数据
     */
    private String getBeforeAll(){
        return getBeforeAll(false);
    }

    /**
     * 获取前面的全部数据
     * @param anno 是否检测注解
     */
    private String getBeforeAll(boolean anno){
        StringBuilder builder = new StringBuilder();
        for (int i = pushLine.size() - 1; i >= 0; i--) {
            String trim = pushLine.get(i).trim();
            if (trim.length() == 0 || trim.startsWith("/") || trim.startsWith("*")){
                break;
            }
            if (anno && trim.startsWith("@")){
                break;
            }
            builder.insert(0, pushLine.get(i));
        }
        return builder.toString();
    }

    /**
     * 解析注解及注释
     */
    private void before(Node node){
        int size = pushLine.size() - 1;
        while (size > 0){
            String line = pushLine.get(--size);
            String push = line.trim();
            if(push.length() > 0 && !(push.endsWith(";") || push.endsWith("{") || push.endsWith("}"))){
                if(push.startsWith("@")){
                    String regex="\\)\\s+\\w+";
                    Matcher m=Pattern.compile(regex).matcher(push);
                    if(!m.find()){
                        node.pushAnnotation(push.substring(1));
                    }
                }else if (push.startsWith("/")){
                    node.setComments(push + JAngel.lineBreak + node.getComments());
                }else if (push.startsWith("*")){
                    node.setComments(line + node.getComments());
                }
            }else {
                break;
            }
        }
        pushLine.clear();
    }

}
