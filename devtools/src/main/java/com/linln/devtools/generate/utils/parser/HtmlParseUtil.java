package com.linln.devtools.generate.utils.parser;

import com.linln.devtools.generate.utils.CodeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 代码生成：html解析工具
 * @author 小懒虫
 * @date 2019/3/26
 */
public class HtmlParseUtil {

    private static String lineBreak = CodeUtil.lineBreak;
    private static String docType = "<!DOCTYPE html>" + lineBreak;

    /**
     * 获取Jsoup文档对象
     * @param path 文件路径
     */
    public static Document document(String path) throws IOException {
        Document document = Jsoup.parse(new File(path), CodeUtil.ENCODE);
        Document.OutputSettings outputSettings = document.outputSettings();
        outputSettings.prettyPrint(false);
        return document;
    }

    /**
     * 获取指定jsoup属性值的节点
     * @param jsoup 属性值
     */
    public static Element getJsoup(Document document, String jsoup){
        Elements elements = document.getElementsByAttributeValue("jsoup", jsoup);
        Element element = elements.get(0);
        element.removeAttr("jsoup");
        return element;
    }

    /**
     * 根据所在节点位置获取制表符格式
     * @param element Jsoup节点对象
     */
    public static String tabBreak(Element element){
        String preTab = ((Node) element).previousSibling().toString();
        if(preTab.trim().length() > 0){
            int count = 0;
            boolean hasWhile = true;
            while (hasWhile){
                if(element.hasParent() || "html".equals(element.tagName())){
                    element = element.parent();
                    count ++;
                }else {
                    hasWhile = false;
                }
            }
            return CodeUtil.lineBreak + CodeUtil.repeat(CodeUtil.tabBreak, count - 1);
        }
        return preTab;
    }

    /**
     * 获取文档内容
     * @param document Jsoup文档对象
     */
    public static String html(Document document){
        // 拼接html标签及属性
        Element htmlEle = document.head().parent();
        StringBuilder html = new StringBuilder("<html");
        List<Attribute> hAttrs = htmlEle.attributes().asList();
        for (int i = 0; i < hAttrs.size(); i++) {
            if(i == 0){
                html.append(" ");
            }else{
                html.append(lineBreak).append(CodeUtil.blank(6));
            }
            html.append(hAttrs.get(i).getKey()).append("=\"").append(hAttrs.get(i).getValue()).append("\"");
        }
        html.append(">").append(lineBreak);

        // 获取头部标签内容
        String head = document.head().toString() + lineBreak;

        // 拼接body标签及属性
        Element bodyEle = document.body();
        StringBuilder bodyJoint = new StringBuilder("<body");
        bodyEle.attributes().forEach(attr -> {
            bodyJoint.append(" ").append(attr.getKey()).append("=\"").append(attr.getValue()).append("\"");
        });
        bodyJoint.append(">").append(lineBreak);
        bodyJoint.append(CodeUtil.tabBreak).append(bodyEle.html().trim()).append(lineBreak);
        bodyJoint.append("</body>").append(lineBreak);
        // 去除空行
        String body = bodyJoint.toString().replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1");

        return docType + html + head + body + "</html>";
    }

}
