package com.linln.admin.core.shiro;

import com.linln.admin.system.domain.User;
import com.linln.core.utils.ToolUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Shiro工具类
 */
public class ShiroUtil {

    /**
     * 加密算法
     */
    public final static String hashAlgorithmName = "SHA-256";

    /**
     * 循环次数
     */
    public final static int hashIterations = 1024;

    /**
     * 加密处理
     * @param password 密码
     * @param salt 密码盐
     */
    public static String encrypt(String password, String salt){
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        return new SimpleHash(hashAlgorithmName, password, byteSalt, hashIterations).toString();
    }

    /**
     * 获取随机盐值
     */
    public static String getRandomSalt(){
        return ToolUtil.getRandomString(6);
    }

    /**
     * 获取ShiroUser对象
     */
    public static User getSubject(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}
