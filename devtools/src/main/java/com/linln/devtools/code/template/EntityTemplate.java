package com.linln.devtools.code.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.system.domain.User;
import com.linln.core.utils.ToolUtil;
import com.linln.devtools.code.domain.Field;
import com.linln.devtools.code.domain.Generate;
import com.linln.devtools.code.enums.FieldType;
import com.linln.devtools.code.utils.CodeUtil;
import com.linln.devtools.code.utils.GenerateUtil;
import com.linln.devtools.code.utils.TemplateUtil;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.file.FileAlreadyExistsException;
import java.util.Date;
import java.util.Map;

/**
 * @author 小懒虫
 * @date 2018/10/23
 */
public class EntityTemplate {

    public String templateSuffix = "";

    /**
     * 生成类体
     */
    private static void genClazzBody(Generate Generate){
        CodeUtil.annoBr(Entity.class);
        CodeUtil.append(CodeUtil.anno(Table.class)).append("(name=\"").append(Generate.getBasic().
                getTablePrefix()).append(Generate.getBasic().getTableName()).append("\")").append(CodeUtil.lineBreak);
        CodeUtil.annoBr(Data.class);
        CodeUtil.append(CodeUtil.anno(EntityListeners.class)).append("(").
                append(CodeUtil.clazz(AuditingEntityListener.class)).append(".class)").append(CodeUtil.lineBreak);
        CodeUtil.append("public class ").append(Generate.getBasic().getTableEntity()).append(" implements ").
                append(CodeUtil.clazz(Serializable.class)).append(" {").append(CodeUtil.lineBreak);
        Map<Long, String> fieldType = ToolUtil.enumToMap(FieldType.class);
        for (Field field: Generate.getFields()) {
            genField(field, fieldType);
        }
        CodeUtil.lineNo("}");
    }

    /**
     * 生成类字段
     * @param field 字段对象
     */
    private static void genField(Field field, Map<Long, String> fieldType){
        String type = fieldType.get((long) field.getType());
        switch (field.getName()){
            case "id":
                CodeUtil.tabLineNo(1, CodeUtil.anno(Id.class));
                CodeUtil.tabLineNo(1, CodeUtil.anno(GeneratedValue.class)
                        + "(strategy = " + CodeUtil.clazz(GenerationType.class) + ".IDENTITY)");
                CodeUtil.tabLineNo(1, "private " + type + " id;");
                break;
            case "createDate":
                CodeUtil.tabLineNo(1, "// " + field.getTitle());
                CodeUtil.tabLineNo(1, CodeUtil.anno(CreatedDate.class));
                CodeUtil.tabLineNo(1, "private " + CodeUtil.clazz(Date.class) + " createDate;");
                break;
            case "updateDate":
                CodeUtil.tabLineNo(1, "// " + field.getTitle());
                CodeUtil.tabLineNo(1, CodeUtil.anno(LastModifiedDate.class));
                CodeUtil.tabLineNo(1, "private " + CodeUtil.clazz(Date.class) + " updateDate;");
                break;
            case "createBy":
                CodeUtil.tabLineNo(1, "// " + field.getTitle());
                CodeUtil.tabLineNo(1, CodeUtil.anno(CreatedBy.class));
                CodeUtil.tabLineNo(1, CodeUtil.anno(ManyToOne.class)
                        + "(fetch="+ CodeUtil.clazz(FetchType.class) +".LAZY)");
                CodeUtil.tabLineNo(1, CodeUtil.anno(JoinColumn.class) + "(name=\"create_by\")");
                CodeUtil.tabLineNo(1, CodeUtil.anno(JsonIgnore.class));
                CodeUtil.tabLineNo(1, "private " + CodeUtil.clazz(User.class) + " createBy;");
                break;
            case "updateBy":
                CodeUtil.tabLineNo(1, "// " + field.getTitle());
                CodeUtil.tabLineNo(1, CodeUtil.anno(LastModifiedBy.class));
                CodeUtil.tabLineNo(1, CodeUtil.anno(ManyToOne.class)
                        + "(fetch="+ CodeUtil.clazz(FetchType.class) +".LAZY)");
                CodeUtil.tabLineNo(1, CodeUtil.anno(JoinColumn.class) + "(name=\"update_by\")");
                CodeUtil.tabLineNo(1, CodeUtil.anno(JsonIgnore.class));
                CodeUtil.tabLineNo(1, "private " + CodeUtil.clazz(User.class) + " updateBy;");
                break;
            case "status":
                CodeUtil.tabLineNo(1, "// " + field.getTitle());
                CodeUtil.tabLineNo(1, "private Byte status = " + CodeUtil.clazz(StatusEnum.class) + ".OK.getCode();");
                break;
            default:
                if(type.equals(FieldType.Text.getMessage())){
                    CodeUtil.tabLineNo(1, CodeUtil.anno(Lob.class) + " " + CodeUtil.anno(Column.class) + "(columnDefinition=\"TEXT\")");
                }
                CodeUtil.tabLineNo(1, "// " + field.getTitle());
                CodeUtil.tabLine(1, "private " + type + " " + field.getName());
        }
    }

    /**
     * 生成实体类模板
     */
    public static String generate(Generate generate) {
        CodeUtil.create();
        CodeUtil.setPackageName(TemplateUtil.getPackage(generate, "domain"));
        CodeUtil.addImportAll("javax.persistence");
        TemplateUtil.genAuthor(generate);
        EntityTemplate.genClazzBody(generate);

        // 生成文件
        String filePath = GenerateUtil.getJavaFilePath(generate, "domain", "");
        try {
            GenerateUtil.generateFile(filePath, CodeUtil.save());
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        }
        return filePath;
    }
}
