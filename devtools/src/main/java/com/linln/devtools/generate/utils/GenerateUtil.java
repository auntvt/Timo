package com.linln.devtools.generate.utils;

import com.linln.devtools.generate.domain.Generate;
import com.linln.devtools.generate.enums.ModuleType;
import com.linln.devtools.generate.enums.TierType;
import com.linln.devtools.generate.template.PomXmlTemplate;
import com.linln.devtools.generate.utils.parser.JavaParseUtil;
import com.linln.devtools.generate.utils.parser.XmlParseUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/10/28
 */
public class GenerateUtil {

    private static final String JAVA_SUFFIX = ".java";
    private static final String HTML_SUFFIX = ".html";
    private static final TierType[] MODULE_TIER = {TierType.DOMAIN, TierType.DAO, TierType.SERVICE, TierType.SERVICE_IMPL};

    /**
     * 生成源码java文件全路径
     * @param generate 生成对象
     * @param tierType 业务模块类型
     */
    public static String getJavaFilePath(Generate generate, TierType tierType){
        String projectPath = generate.getBasic().getProjectPath();
        String module = generate.getBasic().getGenModule();
        Integer moduleType = generate.getBasic().getModuleType();
        // 获取类包路径
        String packageName = JavaParseUtil.getPackage(generate, tierType);
        String javaPath = packageName.replace(".", "/") + JAVA_SUFFIX;
        String mavenModule = CodeUtil.ADMIN;
        // maven模块方式
        if (moduleType.equals(ModuleType.ALONE.getCode())
                && Arrays.asList(MODULE_TIER).contains(tierType)){
            mavenModule = CodeUtil.MODULES + "/" + module;
        }
        return projectPath + mavenModule + CodeUtil.MAVEN_SOURCE_PATH + "/java/" + javaPath;
    }

    /**
     * 生成源码html文件全路径
     * @param generate 生成对象
     * @param tierType 业务模块类型
     */
    public static String getHtmlFilePath(Generate generate, TierType tierType){
        String projectPath = generate.getBasic().getProjectPath();
        String requestMapping = generate.getBasic().getRequestMapping();
        return projectPath + CodeUtil.ADMIN + CodeUtil.MAVEN_SOURCE_PATH + "/resources/templates"
                + requestMapping + "/" + tierType.name().toLowerCase() + HTML_SUFFIX;
    }

    /**
     * 生成maven业务模块
     */
    public static void genMavenModule(Generate generate){
        String module = generate.getBasic().getGenModule();
        List<String> moduleList = XmlParseUtil.getPomModuleList();
        if (moduleList != null && !moduleList.contains(module)){
            XmlParseUtil.addPomModule(module);
            // 创建业务模块pom文件
            String projectPath = generate.getBasic().getProjectPath();
            String pomPath = projectPath + CodeUtil.MODULES + "/" + module + "/pom.xml";
            PomXmlTemplate.generate(generate, pomPath);
            // 向后台模块添加依赖
            String packagePath = generate.getBasic().getPackagePath();
            String adminPom = projectPath + CodeUtil.ADMIN + "/pom.xml";
            try {
                Document document = XmlParseUtil.document(adminPom);
                String groupId = packagePath + "." + CodeUtil.MODULES;
                Elements dependencys = document.getElementsContainingText(groupId);
                Element lastDependency = dependencys.last();
                String dependency = XmlParseUtil.getDependency(module);
                if (lastDependency != null && "groupId".equals(lastDependency.tagName())){
                    dependency = CodeUtil.lineBreak + CodeUtil.tabBreak + dependency;
                    lastDependency.parent().after(dependency);
                } else {
                    Element des = document.getElementsByTag("dependencies").get(0);
                    des.append(dependency + CodeUtil.lineBreak + CodeUtil.tabBreak);
                }
                // 处理标签被转为小写问题
                String html = XmlParseUtil.html(document);
                html = html.replace("groupid", "groupId");
                html = html.replace("artifactid", "artifactId");
                FileUtil.saveWriter(new File(adminPom), html);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            FileUtil.saveWriter(file, content);
        }
    }
}
