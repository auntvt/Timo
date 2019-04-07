package com.linln.devtools.generate.utils;

import com.linln.modules.system.domain.User;
import org.junit.Test;

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