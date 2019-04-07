package com.linln.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 小懒虫
 * @date 2019/2/27
 */
public class ToolUtilTest {

    @Test
    public void lowerFirst() {
        String first = ToolUtil.lowerFirst("ABCD");
        System.out.println(first);
    }

    @Test
    public void upperFirst() {
        String first = ToolUtil.upperFirst("abcd");
        System.out.println(first);
    }
}