package com.linln.core.utils;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 * 通用方法工具类
 * @author 小懒虫
 * @date 2018/10/15
 */
public class ToolUtil {

    /**
     * 获取随机位数的字符串
     * @param length 随机位数
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            // 获取ascii码中的字符 数字48-57 小写65-90 大写97-122
            int range = random.nextInt(75)+48;
            range = range<97?(range<65?(range>57?114-range:range):(range>90?180-range:range)):range;
            sb.append((char)range);
        }
        return sb.toString();
    }

    /**
     * 获取项目根路径
     */
    public static String getProjectPath(){
        try {
            String path = ResourceUtils.getURL("classpath:").getPath();
            path = path.replace("/WEB-INF/classes/", "");
            path = path.replace("/target/classes/", "");
            path = path.replace("file:/", "");
            return path;
        } catch (FileNotFoundException e) {
            return "";
        }
    }
}
