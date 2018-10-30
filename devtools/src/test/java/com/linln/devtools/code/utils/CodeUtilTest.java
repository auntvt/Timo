package com.linln.devtools.code.utils;

import com.linln.admin.system.domain.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 小懒虫
 * @date 2018/10/24
 */
public class CodeUtilTest {
    @Test
    public void test(){
        Package aPackage = User.class.getPackage();
        System.out.println(aPackage.getImplementationTitle());
    }
}