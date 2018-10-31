package com.linln.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminApplicationTests {

    @Test
    public void contextLoads() {
        List<Long> longs = new ArrayList<>();
        longs.add(null);
        System.out.println(Arrays.asList(1));
    }

}
