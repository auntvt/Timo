package com.linln.devtools.code.template;

import com.linln.admin.system.repository.BaseRepository;
import com.linln.devtools.code.domain.Generate;
import com.linln.devtools.code.utils.CodeUtil;
import com.linln.devtools.code.utils.GenerateUtil;
import com.linln.devtools.code.utils.TemplateUtil;

import java.nio.file.FileAlreadyExistsException;

/**
 * @author 小懒虫
 * @date 2018/10/25
 */
public class RepositoryTemplate {

    /**
     * 生成需要导入的包
     */
    private static void genImport(Generate generate) {
        String tableEntity = generate.getBasic().getTableEntity();
        CodeUtil.importLine(TemplateUtil.getPath(generate) + ".domain." + tableEntity);
        CodeUtil.importLine(BaseRepository.class);
    }

    /**
     * 生成类字段
     */
    private static void genClazzBody(Generate generate) {
        // 构建数据
        String obj = generate.getBasic().getTableEntity();
        String filePath = RepositoryTemplate.class.getResource("").getPath()
                + RepositoryTemplate.class.getSimpleName() + ".code";

        // 生成Class部分
        String clazzTarget = TemplateUtil.getTemplate(filePath, "Class");
        clazzTarget = clazzTarget.replace("#{obj}", obj);
        CodeUtil.append(clazzTarget).append(CodeUtil.lineBreak);
    }

    /**
     * 生成Dao层模板
     */
    public static String generate(Generate generate) {
        CodeUtil.create();
        CodeUtil.setPackageName(TemplateUtil.getPackage(generate, "repository"));
        TemplateUtil.genAuthor(generate);
        RepositoryTemplate.genImport(generate);
        RepositoryTemplate.genClazzBody(generate);

        // 生成文件
        String filePath = GenerateUtil.getJavaFilePath(generate, "repository", "Repository");
        try {
            GenerateUtil.generateFile(filePath, CodeUtil.save());
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        }
        return filePath;
    }
}
