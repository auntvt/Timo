package com.linln.component.shiro.remember;

import com.linln.common.utils.EntityBeanUtil;
import com.linln.component.shiro.AuthRealm;
import com.linln.component.shiro.ShiroUtil;
import com.linln.modules.system.domain.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.hibernate.collection.internal.PersistentSet;

import java.util.Arrays;

/**
 * 记住我管理器
 *
 * @author 小懒虫
 * @date 2019/10/28
 */
public class RememberMeManager extends CookieRememberMeManager {

    /**
     * “记住我”二次加密索引，无需修改
     */
    private final String CIPHER_KEY = "(～￣▽￣)～";
    /**
     * 二次加密密码盐长度
     */
    private final int ENCRYPT_LENGTH = 64;

    /**
     * 重写“记住我”实体类【系列化】数据
     *
     * @param principals the principals to remember for retrieval later.
     */
    @Override
    protected byte[] serialize(PrincipalCollection principals) {

        // 获取用户信息
        User user = (User) principals.getPrimaryPrincipal();

        // 克隆一个Principal对象，隐藏用户密码及密码盐，消除部门及角色数据
        String[] ignores = {"password", "salt", "dept", "roles"};
        User principal = (User) EntityBeanUtil.cloneBean(user, ignores);

        // 二次加密用户密码
        String password = ShiroUtil.encrypt(user.getPassword(), user.getSalt());
        principal.setPassword(password);

        // 定义简单的SimplePrincipal对象
        SimplePrincipalCollection collection = new SimplePrincipalCollection(principal, AuthRealm.class.getName());
        return confusion(super.serialize(collection), password);
    }

    /**
     * 重写“记住我”实体类【反系列化】数据
     *
     * @param serializedIdentity the previously serialized {@code PrincipalCollection} as a byte array
     */
    @Override
    @SuppressWarnings("unchecked")
    protected PrincipalCollection deserialize(byte[] serializedIdentity) {

        // 获取“记住我”缓存中的用户对象
        PrincipalCollection collection = super.deserialize(extSerializeData(serializedIdentity));
        User principal = (User) collection.getPrimaryPrincipal();

        // 提取二次加密密码盐数据
        byte[] encrypt = new byte[ENCRYPT_LENGTH];
        System.arraycopy(serializedIdentity, 0, encrypt, 0, encrypt.length);

        // 判断二次加密密码盐是否正确
        String password = principal.getPassword();
        byte[] verifyEncrypt = ShiroUtil.encrypt(password, CIPHER_KEY).getBytes();
        if (!Arrays.equals(encrypt, verifyEncrypt)) {
            throw new AuthenticationException();
        }

        // 更新“记住我”用户数据，使部门及角色超时
        principal.setDept(new RememberMeDept());
        principal.setRoles(new PersistentSet());
        return collection;
    }

    /**
     * 混淆系列化数据
     * {格式：二次加密密码盐(64位)+系列化数据}
     */
    private byte[] confusion(byte[] serializeData, String password) {
        byte[] encrypt = ShiroUtil.encrypt(password, CIPHER_KEY).getBytes();
        // 合并两个数组
        byte[] confusionData = new byte[ENCRYPT_LENGTH + serializeData.length];
        System.arraycopy(encrypt, 0, confusionData, 0, ENCRYPT_LENGTH);
        System.arraycopy(serializeData, 0, confusionData, ENCRYPT_LENGTH, serializeData.length);

        return confusionData;
    }

    /**
     * 提取系列化数据
     * {格式：二次加密密码盐(64位)+系列化数据}
     */
    private byte[] extSerializeData(byte[] serializedIdentity) {
        if (serializedIdentity.length > ENCRYPT_LENGTH) {
            // 提取系列化数据
            byte[] serializeData = new byte[serializedIdentity.length - ENCRYPT_LENGTH];
            System.arraycopy(serializedIdentity, ENCRYPT_LENGTH, serializeData, 0, serializeData.length);

            return serializeData;
        }
        return serializedIdentity;
    }

}
