package com.linln.admin.system.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseRepositoryTest {

    @Autowired
    public UserRepository userRepository;

    @Test
    public void updateStatus() {
        List<Long> longList = new ArrayList<Long>();
        longList.add(36L);
        longList.add(34L);
        Integer count = userRepository.updateStatus((byte) 3, longList);
        System.out.println(count);
    }
}