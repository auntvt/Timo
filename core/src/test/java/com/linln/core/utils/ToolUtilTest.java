package com.linln.core.utils;

import org.junit.Test;

public class ToolUtilTest {

    private String[] ignoreProperties = new String[]{
            "createDate",
            "updateDate",
            "createBy",
            "updateBy",
            "status"
    };

    @Test
    public void getRandomString() {
        //48-57 65-90 97-122
        /*Random rand = new Random();
        int range = 0;
        for (int i=0;i<10;i++){
            range = rand.nextInt(75)+48;
            range = range<97?(range<65?(range>57?114-range:range):(range>90?180-range:range)):range;

            System.out.println((char)range);
        }*/

        System.out.println(ToolUtil.getRandomString(4));

        String[] newIgnoreProperties = new String[]{
                "aa",
                "bb",
        };

        // 合并两个数组
        String[] jointIgnoreProperties = new String[ignoreProperties.length + newIgnoreProperties.length];
        System.arraycopy(ignoreProperties, 0, jointIgnoreProperties, 0, ignoreProperties.length);
        System.arraycopy(newIgnoreProperties, 0, jointIgnoreProperties, ignoreProperties.length, newIgnoreProperties.length);
        System.out.println(jointIgnoreProperties);
    }
}