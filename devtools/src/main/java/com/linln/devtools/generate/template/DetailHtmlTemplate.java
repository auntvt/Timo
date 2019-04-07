package com.linln.devtools.generate.template;

import com.linln.common.utils.ToolUtil;
import com.linln.devtools.generate.domain.Field;
import com.linln.devtools.generate.domain.Generate;
import com.linln.devtools.generate.enums.TierType;
import com.linln.devtools.generate.utils.CodeUtil;
import com.linln.devtools.generate.utils.FileUtil;
import com.linln.devtools.generate.utils.GenerateUtil;
import com.linln.devtools.generate.utils.parser.HtmlParseUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;

/**
 * @author 小懒虫
 * @date 2018/10/25
 */
public class DetailHtmlTemplate {

    private static String[] fixedSort = {"user", "date", "remark"};
    private static String[] ignore = {"status"};

    /**
     * 创建节点-格式化字表符
     * @param element Jsoup元素对象
     * @param name 节点名称
     * @return 新建的节点对象
     */
    private static Element appendElement(Element element, String name){
        element.append(HtmlParseUtil.tabBreak(element) + CodeUtil.tabBreak);
        return element.appendElement(name);
    }

    /**
     * 创建固定组合节点-格式化字表符
     * @param fixeds 固定组合节点集合
     * @param groupName 组名称
     * @return 新建的节点对象
     */
    private static Element fixedElement(Element fixeds, String groupName){
        Element element = fixeds.getElementById(groupName);
        if(element == null){
            fixeds.append(HtmlParseUtil.tabBreak(fixeds) + CodeUtil.tabBreak);
            element = fixeds.appendElement("tr").attr("id", groupName);
        }
        return element;
    }

    /**
     * 生成页面
     */
    private static String genHtmlBody(Generate generate) throws IOException {
        // 构建数据
        String var = ToolUtil.lowerFirst(generate.getBasic().getTableEntity());
        String path = FileUtil.templatePath(DetailHtmlTemplate.class);

        // 获取Jsoup文档对象
        Document document = HtmlParseUtil.document(path);

        // 遍历字段
        Element detailNode = HtmlParseUtil.getJsoup(document, "detail");
        String tabBreak = HtmlParseUtil.tabBreak(detailNode);
        Element lastNode = null;
        for (Field field : generate.getFields()) {
            Element trNode = null;
            String thText = "${%s.%s}";
            switch (field.getName()){
                case "createBy":
                case "updateBy":
                    trNode = fixedElement(detailNode, fixedSort[0]);
                    thText = "${%s.%s?.nickname}";
                    break;
                case "createDate":
                case "updateDate":
                    trNode = fixedElement(detailNode, fixedSort[1]);
                    thText = "${#dates.format(%s.%s, 'yyyy-MM-dd HH:mm:ss')}";
                    break;
                case "remark":
                    trNode = fixedElement(detailNode, fixedSort[2]);
                    break;
                default:
                    if(!Arrays.asList(ignore).contains(field.getName())){
                        if(!(lastNode != null && lastNode.children().size() < 4)){
                            lastNode = appendElement(detailNode, "tr");
                        }
                        trNode = lastNode;
                    }
            }

            if(trNode != null){
                appendElement(trNode, "th").text(field.getTitle());
                appendElement(trNode, "td").attr("th:text", String.format(thText, var, field.getName()));
                trNode.append(tabBreak + CodeUtil.tabBreak);
            }
        }

        // 重组列表排序
        for (String fixed : fixedSort) {
            Element node = detailNode.getElementById(fixed);
            if(node != null){
                if(node.children().size() < 4 && !fixed.equals(fixedSort[2])){
                    if(lastNode == null || lastNode.children().size() >= 4) {
                        lastNode = appendElement(detailNode, "tr");
                    }
                    lastNode.append(node.html());
                    node.remove();
                }else {
                    node.appendTo(detailNode.append(HtmlParseUtil.tabBreak(detailNode) + CodeUtil.tabBreak)).removeAttr("id");
                }
            }
        }
        detailNode.append(tabBreak);

        // 跨列操作
        for (int i = 0; i < 2; i++) {
            if(lastNode != null && lastNode.children().size() < 4){
                lastNode.children().last().attr("colspan", "3");
            }
            lastNode = detailNode.children().last();
        }

        // 替换基本数据
        return HtmlParseUtil.html(document);
    }

    /**
     * 生成列表页面模板
     */
    public static String generate(Generate generate) {
        String filePath = GenerateUtil.getHtmlFilePath(generate, TierType.DETAIL);
        try {
            String content = DetailHtmlTemplate.genHtmlBody(generate);
            GenerateUtil.generateFile(filePath, content);
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
