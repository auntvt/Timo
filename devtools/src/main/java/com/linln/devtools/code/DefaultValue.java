package com.linln.devtools.code;

import com.linln.core.utils.ToolUtil;
import com.linln.devtools.code.domain.Basic;
import com.linln.devtools.code.domain.Field;
import com.linln.devtools.code.enums.FieldQuery;
import com.linln.devtools.code.enums.FieldType;
import com.linln.devtools.code.enums.FieldVerify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/10/21
 */
public class DefaultValue {

    // 字符编码
    public static final String encode = "UTF-8";
    // maven源码目录
    public static final String mavenSourcePath = "src/main/";

    /**
     * 基本信息
     */
    public static Basic getBasic(){
        Basic basic = new Basic();
        basic.setProjectPath(ToolUtil.getProjectPath() + "/admin/");
        basic.setPackagePath("com.linln.admin");
        basic.setAuthor("小懒虫");
        basic.setGenModule("order");
        basic.setTablePrefix("or_");
        return basic;
    }

    /**
     * 字段列表
     */
    public static List<Field> fieldList(){
        List<Field> fields = new ArrayList<>();
        fields.add(new Field("id", "主键ID", FieldType.Long.getCode(), 0, true,null));
        fields.add(new Field("title", "标题", FieldType.String.getCode(), FieldQuery.Like.getCode(),true, Arrays.asList(new Integer[]{FieldVerify.NoNull.getCode()})));
        fields.add(new Field("remark", "备注", FieldType.String.getCode(), 0, false,null));
        fields.add(new Field("createDate", "创建时间", FieldType.Date.getCode(), 0, true,null));
        fields.add(new Field("updateDate", "更新时间", FieldType.Date.getCode(), 0, true,null));
        fields.add(new Field("createBy", "创建者", FieldType.Object.getCode(), 0, false,null));
        fields.add(new Field("updateBy", "更新者", FieldType.Object.getCode(), 0, false,null));
        fields.add(new Field("status", "数据状态", FieldType.Byte.getCode(), 0, true,null));
        return fields;
    }
}
