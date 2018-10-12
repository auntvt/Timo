package com.linln.admin.core.shiro;

import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;

public class ShiroUtilTest {

    @Test
    public void encrypt() {
        String pwd = ShiroUtil.encrypt("123456", "ha12");
        System.out.println(pwd);

        Random ranGen = new SecureRandom();
        byte[] aesKey = new byte[4];
        ranGen.nextBytes(aesKey);
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < aesKey.length; i++) {
            String hex = Integer.toHexString(0xff & aesKey[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        System.out.println(hexString);
    }
}