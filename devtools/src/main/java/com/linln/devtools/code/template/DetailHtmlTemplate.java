package com.linln.devtools.code.template;

import com.linln.core.utils.ToolUtil;
import com.linln.devtools.code.domain.Field;
import com.linln.devtools.code.domain.Generate;
import com.linln.devtools.code.utils.CodeUtil;
import com.linln.devtools.code.utils.GenerateUtil;
import com.linln.devtools.code.utils.TemplateUtil;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 小懒虫
 * @date 2018/10/25
 */
public class DetailHtmlTemplate {

    private static final String trTab = CodeUtil.lineBreak + "            ";
    private static final String tdTab = CodeUtil.lineBreak + "                ";
    private static final String trLabel = "<tr>%s</tr>";
    private static final String thLabel = "<th>%s</th>";
    private static final String tdLabel = "<td th:text=\"${%s}\"></td>";

    /**
     * 生成页面
     */
    private static String genHtmlBody(Generate generate) {
        // 构建数据
        String var = ToolUtil.lowerFirst(generate.getBasic().getTableEntity());
        String filePath = DetailHtmlTemplate.class.getResource("").getPath()
                + DetailHtmlTemplate.class.getSimpleName() + ".code";

        // 提取html页面
        String htmlTarget = TemplateUtil.getTemplate(filePath, "html");

        // 遍历字段
        StringBuilder fieldBuilder = new StringBuilder();
        StringBuilder tempBuilder = new StringBuilder();
        AtomicInteger count = new AtomicInteger();
        List<Field> fields = generate.getFields();
        final boolean[] fieldRemark = {false};
        fields.forEach(field -> {
            if(field.getName().equals("createBy") || field.getName().equals("createDate")){
                fieldBuilder.append(String.format(trTab + trLabel, tempBuilder + trTab));
                tempBuilder.setLength(0);
                count.set(0);
            }
            String text = "";
            switch (field.getName()) {
                case "createBy":
                case "updateBy":
                    text = "%s.%s?.nickname";
                    break;
                case "createDate":
                case "updateDate":
                    text = "#dates.format(%s.%s, 'yyyy-MM-dd HH:mm:ss')";
                    break;
                case "remark":
                    count.decrementAndGet();
                    fieldRemark[0] = true;
                    break;
                case "status":
                    count.decrementAndGet();
                    break;
                default:
                    text = "%s.%s";
            }
            if (!text.isEmpty()) {
                tempBuilder.append(String.format(tdTab + thLabel, field.getTitle()));
                tempBuilder.append(String.format(tdTab + tdLabel, String.format(text, var, field.getName())));
            }
            if (count.incrementAndGet() == 2 || (field.equals(fields.get(fields.size() - 1)) && tempBuilder.length() != 0)) {
                fieldBuilder.append(String.format(trTab + trLabel, tempBuilder + trTab));
                tempBuilder.setLength(0);
                count.set(0);
            }
        });

        // 判断是否需要remark字段
        if (fieldRemark[0]){
            tempBuilder.append(String.format(tdTab + thLabel, "备注"));
            tempBuilder.append(String.format(tdTab + "<td th:text=\"${%s.remark}\" colspan=\"4\"></td>", var));
            fieldBuilder.append(String.format(trTab + trLabel, tempBuilder + trTab));
        }

        // 替换基本数据
        htmlTarget = htmlTarget.replace("#{field.detail}", fieldBuilder + trTab);
        return htmlTarget;
    }

    /**
     * 生成列表页面模板
     */
    public static String generate(Generate generate) {
        String filePath = GenerateUtil.getHtmlFilePath(generate, "detail");
        String content = DetailHtmlTemplate.genHtmlBody(generate);
        try {
            GenerateUtil.generateFile(filePath, content);
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        }
        return filePath;
    }
}
