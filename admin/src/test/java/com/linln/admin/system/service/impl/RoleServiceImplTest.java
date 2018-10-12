package com.linln.admin.system.service.impl;

import com.linln.admin.system.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceImplTest {

    @Autowired
    RoleService roleService;

    @Test
    public void findRoleList() {
        /*List<Role> roleList = roleService.findRoleList(1L);
        System.out.println(Arrays.asList(roleList));*/
    }
}