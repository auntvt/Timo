package com.linln.devtools.generate.utils.jAngel.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串处理工具
 * @author 小懒虫
 * @date 2019/3/28
 */
public class StringUtil {

    /**
     * 获取指定次数的空格符
     * @param repeat 拼接次数
     */
    public static String blank(int repeat){
        return repeat(" ", repeat);
    }

    /**
     * 重复拼接指定的字符串
     * @param str 字符串
     * @param repeat 拼接次数
     */
    public static String repeat(String str, int repeat){
        StringBuilder repeatStr = new StringBuilder("");
        for (int i = 0; i < repeat; i++) {
            repeatStr.append(str);
        }
        return repeatStr.toString();
    }

    /**
     * 匹配非java字符串下的字符
     */
    public static int matcher(String str ,char input){
        char[] value = str.toCharArray();
        int quotes = 0;
        for (int i = 0; i < str.length(); i++) {
            if(value[i] == '"'){
                if(++quotes == 2){
                    quotes = 0;
                }
            }
            if(value[i] == input && quotes == 0){
                return i;
            }
        }
        return -1;
    }

    /**
     * 提取字符串中的单词
     * @param text 字符串
     * @param splits 自定义分割的字符
     * @return 单词列表
     */
    public static List<String> extWord(String text, Character... splits){
        char[] chars = text.toCharArray();
        List<String> wordList = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        boolean section = true;
        for (char c : chars) {
            if (c == '<'){
                section = false;
            }
            if (c == '>'){
                section = true;
            }
            if (section && (c == 32 || c == 9 || arrayContains(splits, c))){
                if (word.length() > 0){
                    wordList.add(word.toString());
                }
                word.delete(0, word.length());
            }else {
                word.append(c);
            }
        }
        if (word.length() > 0){
            wordList.add(word.toString());
        }
        return wordList;
    }

    /**
     * 判断数组中是否存在某个元素
     * @param arr 数组
     * @param c 元素
     */
    public static boolean arrayContains(Object[] arr, Object c){
        for(Object o : arr){
            if(o.equals(c)) {
                return true;
            }
        }
        return false;
    }
}
