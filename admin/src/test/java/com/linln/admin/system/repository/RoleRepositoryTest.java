package com.linln.admin.system.repository;

import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.system.domain.Menu;
import com.linln.admin.system.domain.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    /*@Test
    public void findByUser_Id() {
        List<Role> byUser_id = roleRepository.findByUser_Id(1l);
        System.out.println(byUser_id);
    }*/

    @Test
    public void test(){
        Byte status = StatusEnum.OK.getCode();
        //List<Role> list = roleRepository.findByUser_IdAndUser_StatusAndMenu_StatusAndStatus((long) 1, status, status, status);
//        List<Role> list2 = roleRepository.findByUser_IdAndUser_Status((long) 1, status);
        //List<Role> list3 = roleRepository.findAll();
        Role role = new Role();
        Menu menu = new Menu();
        menu.setId((long) 3);
        role.getMenus().add(menu);
        roleRepository.delete(role);
//        System.out.println(list2);
    }
}