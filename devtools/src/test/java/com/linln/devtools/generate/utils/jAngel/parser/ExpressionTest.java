package com.linln.devtools.generate.utils.jAngel.parser;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 小懒虫
 * @date 2019/4/3
 */
public class ExpressionTest {

    @Test
    public void matcher() {
        String tmpl = "我的qq群是：!#{qq}，它很##  # #{{{{{xs}}}";
        Expression expression = Expression.of("qq", "QQ群:941209502")
                .label("xs", "多人");
        System.out.println(expression.matcher(tmpl));
    }
}