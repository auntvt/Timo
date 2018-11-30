package com.linln.devtools.code.utils;

import com.linln.core.utils.ToolUtil;
import com.linln.devtools.code.DefaultValue;
import com.linln.devtools.code.domain.Generate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileAlreadyExistsException;

/**
 * @author 小懒虫
 * @date 2018/10/28
 */
public class GenerateUtil {

    public static String javaSuffix = ".java";
    public static String htmlSuffix = ".html";

    /**
     * 生成源码文件全路径
     * @param generate 生成对象
     * @param layer 业务层包名
     * @param fileName 文件名
     */
    public static  String getFilePath(Generate generate, String layer, String fileName){
        String projectPath = generate.getBasic().getProjectPath();
        String packagePath = generate.getBasic().getPackagePath();
        packagePath += "." + generate.getBasic().getGenModule() + "." + layer;
        packagePath = packagePath.replace(".", "/") + "/";
        return projectPath + DefaultValue.mavenSourcePath + "java/" + packagePath + fileName;
    }

    /**
     * 生成源码java文件全路径
     * @param generate 生成对象
     * @param layer 业务层包名
     * @param layerName 业务层文件后缀
     */
    public static  String getJavaFilePath(Generate generate, String layer, String layerName){
        return getFilePath(generate, layer, generate.getBasic().getTableEntity() + layerName + javaSuffix);
    }

    /**
     * 生成源码html文件全路径
     * @param generate 生成对象
     * @param fileName 文件名
     */
    public static  String getHtmlFilePath(Generate generate, String fileName){
        String projectPath = generate.getBasic().getProjectPath();
        String module = generate.getBasic().getGenModule();
        String entity = generate.getBasic().getTableEntity();
        return projectPath + DefaultValue.mavenSourcePath + "resources/templates/"
                + module + "/" + ToolUtil.lowerFirst(entity) + "/" + fileName + htmlSuffix;
    }

    /**
     * 文件存在异常
     * @param filePath 文件路径
     */
    public static String fileExist(String filePath){
        return "exist:" + filePath;
    }

    /**
     * 生成源码文件
     * @param filePath 文件路径
     * @param content 文件内容
     */
    public static void generateFile(String filePath, String content) throws FileAlreadyExistsException{
        File file = new File(filePath);
        if(file.exists()){
            throw new FileAlreadyExistsException(filePath + "文件已经存在");
        }else {
            FileOutputStream fos = null;
            OutputStreamWriter osw = null;
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                fos = new FileOutputStream(file);
                osw = new OutputStreamWriter(fos, DefaultValue.encode);
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
}
