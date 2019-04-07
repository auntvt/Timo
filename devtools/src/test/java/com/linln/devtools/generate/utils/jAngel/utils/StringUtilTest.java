package com.linln.devtools.generate.utils.jAngel.utils;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 小懒虫
 * @date 2019/4/3
 */
public class StringUtilTest {

    @Test
    public void extWord() {
        List<String> strings = StringUtil.extWord("private  #[obj]Repository  BaseRepository<#{obj}, Long>                 #[var]Repository , asfa , afsf", ',');
        System.out.println(strings);
    }
}