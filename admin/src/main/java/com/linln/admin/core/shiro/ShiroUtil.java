package com.linln.admin.core.shiro;

import com.linln.admin.system.domain.Role;
import com.linln.admin.system.domain.User;
import com.linln.core.utils.SpringContextUtil;
import com.linln.core.utils.ToolUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.util.ByteSource;

import java.util.Set;

/**
 * Shiro工具类
 * @author 小懒虫
 * @date 2018/8/14
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

    /**
     * 获取用户IP地址
     */
    public static String getIp()
    {
        return SecurityUtils.getSubject().getSession().getHost();
    }

    /**
     * 重置Cookie“记住我”序列化信息
     */
    public static void resetCookieRememberMe(){
        Set<Role> roles = getSubject().getRoles();
        getSubject().setRoles(null);
        RememberMeManager meManager = SpringContextUtil.getBean(RememberMeManager.class);
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setRememberMe(true);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        info.setPrincipals(SecurityUtils.getSubject().getPrincipals());
        meManager.onSuccessfulLogin(SecurityUtils.getSubject(), token, info);
        getSubject().setRoles(roles);
    }
}
