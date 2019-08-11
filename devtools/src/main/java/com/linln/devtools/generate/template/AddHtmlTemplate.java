package com.linln.devtools.generate.template;

import com.linln.common.utils.ToolUtil;
import com.linln.devtools.generate.domain.Field;
import com.linln.devtools.generate.domain.Generate;
import com.linln.devtools.generate.enums.TierType;
import com.linln.devtools.generate.utils.FileUtil;
import com.linln.devtools.generate.utils.GenerateUtil;
import com.linln.devtools.generate.utils.parser.HtmlParseUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/10/25
 */
public class AddHtmlTemplate {

    /**
     * 生成页面
     */
    private static String genHtmlBody(Generate generate) throws IOException {
        // 构建数据
        String var = ToolUtil.lowerFirst(generate.getBasic().getTableEntity());
        String baseUrl = generate.getBasic().getRequestMapping();
        String path = FileUtil.templatePath(AddHtmlTemplate.class);

        // 获取Jsoup文档对象
        Document document = HtmlParseUtil.document(path);

        // 遍历字段
        Element fieldNode = HtmlParseUtil.getJsoup(document, "field");
        StringBuilder fieldBuilder = new StringBuilder();
        String[] ignore = {"id", "remark", "createDate", "updateDate", "createBy", "updateBy", "status"};
        List<String> ignoreList = Arrays.asList(ignore);
        boolean fieldRemark = false;
        for (Field field : generate.getFields()) {
            if(!ignoreList.contains(field.getName())){
                String temp = fieldNode.toString();
                temp = temp.replace("#{field.title}", field.getTitle());
                temp = temp.replace("#{field.name}", field.getName());
                fieldBuilder.append(HtmlParseUtil.tabBreak(fieldNode)).append(temp);
            }
            if("remark".equals(field.getName())){
                fieldRemark = true;
            }
        }
        fieldNode.after(fieldBuilder.toString());
        fieldNode.remove();

        // 判断是否需要remark字段
        Element remarkNode = HtmlParseUtil.getJsoup(document, "remark");
        if (!fieldRemark){
            remarkNode.remove();
        }

        // 替换基本数据
        String html = HtmlParseUtil.html(document);
        html = html.replace("#{var}", var);
        html = html.replace("#{baseUrl}", baseUrl);
        return html;
    }

    /**
     * 生成列表页面模板
     */
    public static String generate(Generate generate) {
        String filePath = GenerateUtil.getHtmlFilePath(generate, TierType.ADD);
        try {
            String content = AddHtmlTemplate.genHtmlBody(generate);
            GenerateUtil.generateFile(filePath, content);
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
