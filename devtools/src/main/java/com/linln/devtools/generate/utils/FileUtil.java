package com.linln.devtools.generate.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 文件操作工具
 * @author 小懒虫
 * @date 2019/3/2
 */
public class FileUtil {

    /**
     * 获取模板文件路径
     * @param clazz 类对象
     */
    public static String templatePath(Class<?> clazz){
        return clazz.getResource("").getPath() + clazz.getSimpleName() + ".tpl";
    }

    /**
     * 保存文本文件
     * @param file 文件对象
     * @param content 文件内容
     */
    public static void saveWriter(File file, String content){
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            if (!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, CodeUtil.ENCODE);
            osw.write(content);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
