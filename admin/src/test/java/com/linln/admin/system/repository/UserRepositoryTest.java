package com.linln.admin.system.repository;

import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.system.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByIdInAndStatus() {
        List<Long> users = new ArrayList<>();
        users.add((long) 82);
        users.add((long) 83);
        users.add((long) 84);
        List<User> userList = userRepository.findByIdInAndStatus(users, StatusEnum.OK.getCode());
        System.out.println(userList);
    }
}