package com.linln.devtools.code.template;

import com.linln.admin.core.enums.ResultEnum;
import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.core.exception.ResultException;
import com.linln.admin.core.web.TimoExample;
import com.linln.core.utils.FormBeanUtil;
import com.linln.core.utils.ResultVoUtil;
import com.linln.core.utils.ToolUtil;
import com.linln.core.vo.ResultVo;
import com.linln.devtools.code.domain.Generate;
import com.linln.devtools.code.enums.FieldQuery;
import com.linln.devtools.code.utils.CodeUtil;
import com.linln.devtools.code.utils.GenerateUtil;
import com.linln.devtools.code.utils.TemplateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/10/24
 */
public class ControllerTemplate {

    /**
     * 生成需要导入的包
     */
    private static void genImport(Generate generate) {
        String tableEntity = generate.getBasic().getTableEntity();
        CodeUtil.importLine(TemplateUtil.getPath(generate) + ".domain." + tableEntity);
        CodeUtil.importLine(TemplateUtil.getPath(generate) + ".service." + tableEntity + "Service");
        CodeUtil.importLine(TemplateUtil.getPath(generate) + ".validator." + tableEntity + "Form");
        CodeUtil.importLine(ResultEnum.class);
        CodeUtil.importLine(StatusEnum.class);
        CodeUtil.importLine(ResultException.class);
        CodeUtil.importLine(TimoExample.class);
        CodeUtil.importLine(FormBeanUtil.class);
        CodeUtil.importLine(ResultVoUtil.class);
        CodeUtil.importLine(ResultVo.class);
        CodeUtil.importLine(RequiresPermissions.class);
        CodeUtil.importLine(Autowired.class);
        CodeUtil.importLine(Example.class);
        CodeUtil.importLine(ExampleMatcher.class);
        CodeUtil.importLine(Page.class);
        CodeUtil.importLine(Controller.class);
        CodeUtil.importLine(Model.class);
        CodeUtil.importLine(Validated.class);
        CodeUtil.importLine("org.springframework.web.bind.annotation.*");
        CodeUtil.importLine(List.class);
    }

    /**
     * 生成类体
     */
    private static void getClazzBody(Generate generate) {
        // 构建数据
        String var = ToolUtil.lowerFirst(generate.getBasic().getTableEntity());
        String obj = generate.getBasic().getTableEntity();
        String title = generate.getBasic().getGenTitle();
        String module = generate.getBasic().getGenModule();
        String filePath = ControllerTemplate.class.getResource("").getPath()
                + ControllerTemplate.class.getSimpleName() + ".code";

        // 生成Class前部分
        String clazzTarget = TemplateUtil.getTemplate(filePath, "Class");
        clazzTarget = clazzTarget.replace("#{var}", var);
        clazzTarget = clazzTarget.replace("#{obj}", obj);
        clazzTarget = clazzTarget.replace("#{title}", title);
        CodeUtil.append(clazzTarget).append(CodeUtil.lineBreak);

        // 生成index列表
        if(generate.getTemplate().isIndex()){
            String indexTarget = TemplateUtil.getTemplate(filePath, "index");
            indexTarget = indexTarget.replace("#{var}", var);
            indexTarget = indexTarget.replace("#{obj}", obj);
            indexTarget = indexTarget.replace("#{title}", title);
            indexTarget = indexTarget.replace("#{module}", module);
            // 生成模糊查询字段
            StringBuilder matcher = new StringBuilder();
            generate.getFields().forEach(field -> {
                if(field.getQuery() == FieldQuery.Like.getCode()){
                    matcher.append(".").append(CodeUtil.lineBreak);
                    matcher.append("\t\t\t\twithMatcher(\"").append(field.getName()).append("\", match -> match.contains())");
                }
            });

            indexTarget = indexTarget.replace("#{matcher}", matcher);
            CodeUtil.append(indexTarget).append(CodeUtil.lineBreak);
        }

        // 添加操作和编辑操作
        if(generate.getTemplate().isAdd()){
            String addTarget = TemplateUtil.getTemplate(filePath, "add");
            addTarget = addTarget.replace("#{var}", var);
            addTarget = addTarget.replace("#{obj}", obj);
            addTarget = addTarget.replace("#{module}", module);
            CodeUtil.append(addTarget).append(CodeUtil.lineBreak);
        }

        // 详细页面
        if(generate.getTemplate().isDetail()){
            String detailTarget = TemplateUtil.getTemplate(filePath, "detail");
            detailTarget = detailTarget.replace("#{var}", var);
            detailTarget = detailTarget.replace("#{obj}", obj);
            detailTarget = detailTarget.replace("#{module}", module);
            CodeUtil.append(detailTarget).append(CodeUtil.lineBreak);
        }

        // 状态操作
        if(generate.getTemplate().isIndex()) {
            String statusTarget = TemplateUtil.getTemplate(filePath, "status");
            statusTarget = statusTarget.replace("#{var}", var);
            statusTarget = statusTarget.replace("#{obj}", obj);
            CodeUtil.append(statusTarget).append(CodeUtil.lineBreak);
        }

        // 结束类
        CodeUtil.lineNo("}");
    }

    /**
     * 生成控制器模板
     */
    public static String generate(Generate generate) {
        CodeUtil.create();
        CodeUtil.setPackageName(TemplateUtil.getPackage(generate, "controller"));
        CodeUtil.addImportAll("org.springframework.web.bind.annotation");
        TemplateUtil.genAuthor(generate);
        ControllerTemplate.genImport(generate);
        ControllerTemplate.getClazzBody(generate);

        // 生成文件
        String filePath = GenerateUtil.getJavaFilePath(generate, "controller", "Controller");
        try {
            GenerateUtil.generateFile(filePath, CodeUtil.save());
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        }
        return filePath;
    }
}
