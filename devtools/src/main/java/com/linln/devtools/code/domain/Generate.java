package com.linln.devtools.code.domain;

import com.linln.devtools.domain.Basic;
import lombok.Data;

import java.util.List;

/**
 * 封装生成数据
 * @author 小懒虫
 * @date 2018/10/23
 */
@Data
public class Generate {
    private com.linln.devtools.domain.Basic basic = new Basic();
    private List<Field> fields;
    private Template template;
}
