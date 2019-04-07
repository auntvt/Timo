package com.linln.devtools.generate.template;

import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.ToolUtil;
import com.linln.devtools.generate.domain.Generate;
import com.linln.devtools.generate.enums.TierType;
import com.linln.devtools.generate.utils.FileUtil;
import com.linln.devtools.generate.utils.GenerateUtil;
import com.linln.devtools.generate.utils.jAngel.JAngelContainer;
import com.linln.devtools.generate.utils.jAngel.nodes.Document;
import com.linln.devtools.generate.utils.jAngel.parser.Expression;
import com.linln.devtools.generate.utils.parser.JavaParseUtil;

import java.nio.file.FileAlreadyExistsException;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/10/25
 */
public class ServiceImplTemplate {

    /**
     * 生成需要导入的包
     */
    private static Set<String> genImports(Generate generate) {
        JAngelContainer container = new JAngelContainer();
        container.importClass(JavaParseUtil.getPackage(generate, TierType.DOMAIN));
        container.importClass(JavaParseUtil.getPackage(generate, TierType.SERVICE));
        container.importClass(JavaParseUtil.getPackage(generate, TierType.DAO));
        container.importClass(StatusEnum.class);
        container.importClass(PageSort.class);
        return container.getImports();
    }

    /**
     * 生成类体
     */
    private static Document genClazzBody(Generate generate) {
        // 构建数据-模板表达式
        Expression expression = new Expression();
        expression.label("name", ToolUtil.lowerFirst(generate.getBasic().getTableEntity()));
        expression.label("entity", generate.getBasic().getTableEntity());
        String path = FileUtil.templatePath(ServiceImplTemplate.class);

        // 获取jAngel文档对象
        Document document = JavaParseUtil.document(path, expression, generate, TierType.SERVICE_IMPL);
        document.getContainer().importClass(genImports(generate));

        return document;
    }

    /**
     * 生成服务实现层模板
     */
    public static String generate(Generate generate) {
        // 生成文件
        String filePath = GenerateUtil.getJavaFilePath(generate, TierType.SERVICE_IMPL);
        try {
            Document document = genClazzBody(generate);
            GenerateUtil.generateFile(filePath, document.content());
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        }
        return filePath;
    }
}
