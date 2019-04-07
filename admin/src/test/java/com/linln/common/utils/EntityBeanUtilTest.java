package com.linln.common.utils;

import com.linln.modules.system.domain.User;
import org.junit.Test;

/**
 * @author 小懒虫
 * @date 2019/2/27
 */
public class EntityBeanUtilTest {

    @Test
    public void getId() {
        User user = new User();
        user.setId(125L);
        Object[] id = EntityBeanUtil.getId(user);
        System.out.println(id);
    }
}