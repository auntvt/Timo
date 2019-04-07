package com.linln.devtools.generate.template;

import com.linln.devtools.generate.domain.Generate;
import com.linln.devtools.generate.enums.TierType;
import com.linln.devtools.generate.utils.FileUtil;
import com.linln.devtools.generate.utils.GenerateUtil;
import com.linln.devtools.generate.utils.parser.HtmlParseUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

/**
 * @author 小懒虫
 * @date 2018/10/25
 */
public class IndexHtmlTemplate {

    /**
     * 生成页面
     */
    private static String genHtmlBody(Generate generate) throws IOException {
        // 构建数据
        String title = generate.getBasic().getGenTitle();
        String baseUrl = generate.getBasic().getRequestMapping();
        String path = FileUtil.templatePath(IndexHtmlTemplate.class);

        // 获取Jsoup文档对象
        Document document = HtmlParseUtil.document(path);

        // 添加/编辑模块入口
        Element addNode = HtmlParseUtil.getJsoup(document, "add");
        Element editNode = HtmlParseUtil.getJsoup(document, "edit");
        if(!generate.getTemplate().isAdd()){
            addNode.remove();
            editNode.remove();
        }

        // 详细模块入口
        Element detailNode = HtmlParseUtil.getJsoup(document, "detail");
        if(!generate.getTemplate().isDetail()){
            detailNode.remove();
        }

        // 拼接搜索模块
        Element searchNode = HtmlParseUtil.getJsoup(document, "search");
        StringBuilder searchBuilder = new StringBuilder();
        generate.getFields().forEach(field -> {
            if (field.getQuery() != null && field.getQuery() > 0) {
                String temp = searchNode.toString();
                temp = temp.replace("#{search.title}", field.getTitle());
                temp = temp.replace("#{search.name}", field.getName());
                searchBuilder.append(HtmlParseUtil.tabBreak(searchNode)).append(temp);
            }
        });
        searchNode.after(searchBuilder.toString());
        searchNode.remove();

        // 拼接列表数据
        Element tableTh = HtmlParseUtil.getJsoup(document, "table-th");
        Element tableList = HtmlParseUtil.getJsoup(document, "table-list");
        StringBuilder tableThBuilder = new StringBuilder();
        StringBuilder tableListBuilder = new StringBuilder();
        generate.getFields().forEach(field -> {
            if(field.isShow()){
                // 表头标题信息
                tableThBuilder.append(HtmlParseUtil.tabBreak(tableTh)).append(tableTh.text(field.getTitle()));

                // 列表信息
                switch (field.getName()) {
                    case "status":
                        tableList.attr("th:text", "${#dicts.dataStatus(item.status)}");
                        break;
                    case "createDate":
                    case "updateDate":
                        tableList.attr("th:text", String.format("${#dates.format(item.%s, 'yyyy-MM-dd HH:mm:ss')}", field.getName()));
                        break;
                    default:
                        tableList.attr("th:text", String.format("${item.%s}", field.getName()));
                }
                tableListBuilder.append(HtmlParseUtil.tabBreak(tableList)).append(tableList.text(field.getTitle()));
            }
        });
        tableTh.after(tableThBuilder.toString());
        tableTh.remove();
        tableList.after(tableListBuilder.toString());
        tableList.remove();

        // 替换基本数据
        String html = HtmlParseUtil.html(document);
        html = html.replace("#{title}", title);
        html = html.replace("#{baseUrl}", baseUrl);
        return html;
    }

    /**
     * 生成列表页面模板
     */
    public static String generate(Generate generate) {
        String filePath = GenerateUtil.getHtmlFilePath(generate, TierType.INDEX);
        try {
            String content = IndexHtmlTemplate.genHtmlBody(generate);
            GenerateUtil.generateFile(filePath, content);
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
