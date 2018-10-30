package com.linln.devtools.code.template;

import com.linln.core.utils.ToolUtil;
import com.linln.devtools.code.domain.Generate;
import com.linln.devtools.code.enums.FieldType;
import com.linln.devtools.code.enums.FieldVerify;
import com.linln.devtools.code.utils.CodeUtil;
import com.linln.devtools.code.utils.GenerateUtil;
import com.linln.devtools.code.utils.TemplateUtil;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.nio.file.FileAlreadyExistsException;
import java.util.Date;
import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/10/24
 */
public class ValidatorTemplate {

    /**
     * 生成需要导入的包
     */
    public static void genImport(Generate generate) {
        String tableEntity = generate.getBasic().getTableEntity();
        CodeUtil.importLine(TemplateUtil.getPath(generate) + ".domain." + tableEntity);
        CodeUtil.importLine(Data.class);
        CodeUtil.importLine("javax.validation.constraints.*");
        CodeUtil.importLine(Serializable.class);
    }

    /**
     * 生成类体
     */
    private static void getClazzBody(Generate generate) {
        // 构建数据
        String obj = generate.getBasic().getTableEntity();

        // 生成类
        CodeUtil.lineNo("@Data");
        CodeUtil.lineNo("public class #{obj}Form extends #{obj} implements Serializable {".replace("#{obj}", obj));

        // 生成验证字段
        generate.getFields().forEach(field -> {
            List<Integer> verifys = field.getVerify();
            if(verifys.size() > 0){
                FieldType typeEnum = (FieldType) ToolUtil.enumCode(FieldType.class, field.getType());
                verifys.forEach(verifyCode -> {
                    FieldVerify verifyEnum = (FieldVerify) ToolUtil.enumCode(FieldVerify.class, verifyCode);

                    if(verifyEnum.equals(FieldVerify.NoNull)){
                        String message = "(message = \""+ field.getTitle() +"不能为空\")";
                        if(typeEnum.equals(FieldType.String)){
                            CodeUtil.tabLineNo(1, "@NotEmpty" + message);
                        }else {
                            CodeUtil.tabLineNo(1, "@NotNull" + message);
                        }
                    }

                    if(verifyEnum.equals(FieldVerify.Email)){
                        CodeUtil.tabLineNo(1, "@Email(message = \"邮箱格式不正确\")");
                    }

                    if(verifyEnum.equals(FieldVerify.Phone)){
                        CodeUtil.tabLineNo(1, "@Pattern(regexp = \"^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$\" ,message = \"手机号码格式不正确\")");
                    }

                    if(verifyEnum.equals(FieldVerify.Number)){
                        CodeUtil.tabLineNo(1, "@Digits(integer = 12,fraction = 2,message = \""+ field.getTitle() +"不是数字\")");
                    }

                    if(verifyEnum.equals(FieldVerify.Date)){
                        CodeUtil.tabLineNo(1, "@Pattern(regexp = \"[0-9]{4}-[0-9]{2}-[0-9]{2}\", message = \"日期格式不对\")");
                    }

                    if(verifyEnum.equals(FieldVerify.Url)){
                        CodeUtil.tabLineNo(1, "@URL(message = \"URL地址格式不对\")");
                        CodeUtil.importLine(URL.class);
                    }

                    if(verifyEnum.equals(FieldVerify.IdKey)){
                        CodeUtil.tabLineNo(1, "@Pattern(regexp=\"(^(\\\\d{14}|\\\\d{17})(\\\\d|[xX])$)?\" ,message=\"身份证号码错误\")");
                    }

                });
                CodeUtil.tabLine(1, "private "+ typeEnum.getMessage() +" "+ field.getName());

                // 追加部分包
                if(typeEnum.equals(FieldType.Date)){
                    CodeUtil.importLine(Date.class);
                }
            }
        });

        // 结束类
        CodeUtil.lineNo("}");
    }

    /**
     * 生成认证类模板
     */
    public static String generate(Generate generate) {
        CodeUtil.create();
        CodeUtil.setPackageName(TemplateUtil.getPackage(generate, "validator"));
        CodeUtil.addImportAll("javax.validation.constraints");
        TemplateUtil.genAuthor(generate);
        ValidatorTemplate.genImport(generate);
        ValidatorTemplate.getClazzBody(generate);

        // 生成文件
        String filePath = GenerateUtil.getJavaFilePath(generate, "validator", "Form");
        try {
            GenerateUtil.generateFile(filePath, CodeUtil.save());
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        }
        return filePath;
    }
}
