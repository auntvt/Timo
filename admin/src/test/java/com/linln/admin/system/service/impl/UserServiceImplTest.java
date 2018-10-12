package com.linln.admin.system.service.impl;

import com.linln.admin.system.domain.User;
import com.linln.admin.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void findByName() {
        User admin = userService.getByName("admin");
        admin.getId();

    }
}