package com.linln.devtools.code.utils;

import org.junit.Test;

import java.nio.file.FileAlreadyExistsException;

import static org.junit.Assert.*;

/**
 * @author 小懒虫
 * @date 2018/10/28
 */
public class GenerateUtilTest {

    @Test
    public void generateFile() {
        try {
            GenerateUtil.generateFile("C:/Users/LINLN/Desktop/qq/aa.txt", "12212\n15544");
        } catch (FileAlreadyExistsException e) {
            System.out.println(e.getMessage());
//            e.printStackTrace();
        }
    }
}