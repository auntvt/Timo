package com.linln.devtools.generate.template;

import com.linln.common.utils.ToolUtil;
import com.linln.devtools.generate.domain.Generate;
import com.linln.devtools.generate.enums.FieldType;
import com.linln.devtools.generate.enums.FieldVerify;
import com.linln.devtools.generate.enums.TierType;
import com.linln.devtools.generate.utils.GenerateUtil;
import com.linln.devtools.generate.utils.jAngel.nodes.*;
import com.linln.devtools.generate.utils.parser.JavaParseUtil;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.FileAlreadyExistsException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小懒虫
 * @date 2018/10/24
 */
public class ValidatorTemplate {

    /**
     * 验证规则列表
     */
    private static Map<FieldVerify, Format> verifyRule(){
        Map<FieldVerify, Format> verify = new HashMap<>(16);

        // 验证规则(字符串出现$时需要转义)
        verify.put(FieldVerify.NotNull, Format.of("$T(message = $S)", NotNull.class, "#T不能为空"));
        verify.put(FieldVerify.Email, Format.of("$T(message = $S)", Email.class, "邮箱格式不正确"));
        verify.put(FieldVerify.Phone, Format.of("$T(regexp = $R, message = $S)", Pattern.class, "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", "手机号码格式不正确"));
        verify.put(FieldVerify.Number, Format.of("$T(integer = 12, fraction = 2, message = $S)", Digits.class, "#T不是数字"));
        verify.put(FieldVerify.Date, Format.of("$T(regexp = $R, message = $S)", Pattern.class, "[0-9]{4}-[0-9]{2}-[0-9]{2}", "日期格式不对"));
        verify.put(FieldVerify.Url, Format.of("$T(message = $S)", URL.class, "URL地址格式不对"));
        verify.put(FieldVerify.IdKey, Format.of("$T(regexp = $R, message = $S)", Pattern.class, "(^(\\d{14}|\\d{17})(\\d|[xX])$)?", "身份证号码错误"));

        return verify;
    }

    /**
     * 生成类体
     */
    private static Document genClazzBody(Generate generate){
        // 获取jAngel文档对象
        Document document = JavaParseUtil.document(generate, TierType.VALID);
        ClassNode clazz = document.getClazz();

        // 实现接口及注解
        clazz.addImplements(Serializable.class);
        clazz.addAnnotation(Data.class);

        // 生成类字段
        Map<FieldVerify, Format> ruleList = ValidatorTemplate.verifyRule();
        generate.getFields().forEach(field -> {
            List<Integer> verifys = field.getVerify();
            if(verifys.size() > 0){
                // 创建字段节点
                String name = field.getName();
                FieldNode node = new FieldNode(name);

                // 验证规则集合
                FieldType typeEnum = (FieldType) ToolUtil.enumCode(FieldType.class, field.getType());
                verifys.forEach(verifyCode -> {
                    FieldVerify verifyEnum = (FieldVerify) ToolUtil.enumCode(FieldVerify.class, verifyCode);

                    // 获取验证规则
                    Format format = ruleList.get(verifyEnum);

                    // 特殊处理-字符串类型的空注解
                    if(verifyEnum.equals(FieldVerify.NotNull) && typeEnum.equals(FieldType.String)){
                        format = Format.of("$T(message = $S)", NotEmpty.class, "#T不能为空");
                    }

                    // 加入验证规则注解
                    if (format != null) {
                        document.getContainer().importClass(format.getImports());
                        String content = format.getContent().replace("#T", field.getTitle());
                        node.addAnnotation(content);
                    }
                });
                // 判断如果类型为Text则进行注解处理
                if (typeEnum.equals(FieldType.Text)){
                    node.setType(String.class);
                }

                // 处理高精度浮点型BigDecimal
                if (typeEnum.equals(FieldType.BigDecimal)){
                    document.getContainer().importClass(BigDecimal.class);
                }

                // 将字段节点附加到验证类上
                if (typeEnum.equals(FieldType.Date)) {
                    document.getContainer().importClass(Date.class);
                    // 格式化时间类型
                    node.addAnnotation(DateTimeFormat.class, Format.of("pattern=$S", "yyyy-MM-dd HH:mm:ss"));
                }

                if (node.getType() == null){
                    node.setType(typeEnum.getMessage());
                }
                node.accessSym(Modifier.PRIVATE);
                clazz.append(node);
            }
        });

        return document;
    }

    /**
     * 生成认证类模板
     */
    public static String generate(Generate generate) {
        // 生成文件
        String filePath = GenerateUtil.getJavaFilePath(generate, TierType.VALID);
        try {
            Document document = genClazzBody(generate);
            GenerateUtil.generateFile(filePath, document.content());
        } catch (FileAlreadyExistsException e) {
            return GenerateUtil.fileExist(filePath);
        }
        return filePath;
    }
}
