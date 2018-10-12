package com.linln.admin.system.service.impl;

import com.linln.admin.system.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceImplTest {

    @Autowired
    MenuService menuService;

    @Test
    public void findList() {
        //List<Menu> list = menuService.findList();
    }
}