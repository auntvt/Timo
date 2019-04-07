package com.linln.devtools.generate.utils.parser;

import org.junit.Test;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2019/3/1
 */
public class XmlParseUtilTest {

    @Test
    public void getModules(){
        //XmlParseUtil.getModules();
    }

    @Test
    public void addModules(){
        XmlParseUtil.addPomModule("Abcdefg");
    }

    @Test
    public void getPomModuleList(){
        List<String> list = XmlParseUtil.getPomModuleList();
        System.out.println(list);
    }

    @Test
    public void getDependency(){
        System.out.println(XmlParseUtil.getDependency("order"));
    }
}