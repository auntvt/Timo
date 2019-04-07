package com.linln.devtools.generate;

import com.linln.common.utils.ToolUtil;
import com.linln.devtools.generate.domain.Basic;
import com.linln.devtools.generate.domain.Field;
import com.linln.devtools.generate.enums.FieldQuery;
import com.linln.devtools.generate.enums.FieldType;
import com.linln.devtools.generate.enums.FieldVerify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/10/21
 */
public class DefaultValue {

    /**
     * 基本信息
     */
    public static Basic getBasic(){
        Basic basic = new Basic();
        basic.setProjectPath(ToolUtil.getProjectPath() + "/");
        basic.setPackagePath("com.linln");
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
        fields.add(new Field("title", "标题", FieldType.String.getCode(), FieldQuery.Like.getCode(),true, Arrays.asList(new Integer[]{FieldVerify.NotNull.getCode()})));
        fields.add(new Field("remark", "备注", FieldType.String.getCode(), 0, false,null));
        fields.add(new Field("createDate", "创建时间", FieldType.Date.getCode(), 0, true,null));
        fields.add(new Field("updateDate", "更新时间", FieldType.Date.getCode(), 0, true,null));
        fields.add(new Field("createBy", "创建者", FieldType.Object.getCode(), 0, false,null));
        fields.add(new Field("updateBy", "更新者", FieldType.Object.getCode(), 0, false,null));
        fields.add(new Field("status", "数据状态", FieldType.Byte.getCode(), 0, true,null));
        return fields;
    }
}
