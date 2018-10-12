package com.linln.admin.system.controller;

import org.junit.Test;

import java.util.TreeMap;

public class MainControllerTest {

    @Test
    public void main() {
        TreeMap<Integer, TreeMap<String, Object>> menu = new TreeMap<>();
        TreeMap<String, Object> item = new TreeMap<>();
        item.put("menu","首页");
        item.put("subMenu",null);
        menu.put(1,item);

        TreeMap<String, Object> item2 = new TreeMap<>();
        item2.put("menu","用户管理");
        TreeMap<Integer, TreeMap<String, Object>> subMenu = new TreeMap<>();
        TreeMap<String, Object> subItem = new TreeMap<>();
        subItem.put("menu","列表");
        subItem.put("subMenu",null);
        subMenu.put(1,subItem);
        TreeMap<String, Object> subItem2 = new TreeMap<>();
        subItem2.put("menu","添加");
        subItem2.put("subMenu",null);
        subMenu.put(2,subItem2);
        item2.put("subMenu",subMenu);
        menu.put(2,item2);
        System.out.println("啦啦");
    }
}