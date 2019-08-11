package com.linln.component.fileUpload;

import com.linln.common.exception.ResultException;
import com.linln.common.utils.SpringContextUtil;
import com.linln.common.utils.ToolUtil;
import com.linln.component.fileUpload.config.properties.UploadProjectProperties;
import com.linln.component.fileUpload.enums.UploadResultEnum;
import com.linln.modules.system.domain.Upload;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传处理工具
 * @author 小懒虫
 * @date 2018/11/4
 */
public class FileUpload {

    /**
     * 创建一个Upload实体对象
     * @param multipartFile MultipartFile对象
     * @param modulePath 文件模块路径
     */
    public static Upload getFile(MultipartFile multipartFile, String modulePath){
        if (multipartFile.getSize() == 0){
            throw new ResultException(UploadResultEnum.NO_FILE_NULL);
        }
        Upload upload = new Upload();
        upload.setMime(multipartFile.getContentType());
        upload.setSize(multipartFile.getSize());
        upload.setName(FileUpload.genFileName(multipartFile.getOriginalFilename()));
        upload.setPath(getPathPattern() + modulePath + FileUpload.genDateMkdir("yyyyMMdd") + upload.getName());
        return upload;
    }

    /**
     * 判断文件是否为支持的格式
     * @param multipartFile MultipartFile对象
     * @param types 支持的文件类型数组
     */
    public static boolean isContentType(MultipartFile multipartFile, String[] types){
        List<String> typeList = Arrays.asList(types);
        return typeList.contains(multipartFile.getContentType());
    }

    /**
     * 获取文件上传保存路径
     */
    public static String getUploadPath(){
        UploadProjectProperties properties = SpringContextUtil.getBean(UploadProjectProperties.class);
        return properties.getFilePath();
    }

    /**
     * 获取文件上传目录的静态资源路径
     */
    public static String getPathPattern(){
        UploadProjectProperties properties = SpringContextUtil.getBean(UploadProjectProperties.class);
        return properties.getStaticPath().replace("/**", "");
    }

    /**
     * 生成随机且唯一的文件名
     */
    public static String genFileName(String originalFilename){
        String fileSuffix = ToolUtil.getFileSuffix(originalFilename);
        return UUID.randomUUID().toString().replace("-", "") + fileSuffix;
    }

    /**
     * 生成指定格式的目录名称(日期格式)
     */
    public static String genDateMkdir(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return "/" + sdf.format(new Date()) + "/";
    }

    /**
     * 获取目标文件对象
     * @param upload 上传实体类
     */
    public static File getDestFile(Upload upload) throws IOException {

        // 创建保存文件对象
        String path = upload.getPath().replace(getPathPattern(), "");
        String filePath = getUploadPath() + path;
        File dest = new File(filePath.replace("//", "/"));
        if(!dest.exists()){
            dest.getParentFile().mkdirs();
            dest.createNewFile();
        }

        return dest;
    }

    /**
     * 保存文件及获取文件MD5值和SHA1值
     * @param multipartFile MultipartFile对象
     * @param upload Upload
     */
    public static void transferTo(MultipartFile multipartFile, Upload upload) throws IOException, NoSuchAlgorithmException {

        byte[] buffer = new byte[4096];
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        try (OutputStream fos = Files.newOutputStream(getDestFile(upload).toPath()); InputStream fis = multipartFile.getInputStream()) {
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                md5.update(buffer, 0, len);
                sha1.update(buffer, 0, len);
            }
            fos.flush();
        }
        BigInteger md5Bi = new BigInteger(1, md5.digest());
        BigInteger sha1Bi = new BigInteger(1, sha1.digest());
        upload.setMd5(md5Bi.toString(16));
        upload.setSha1(sha1Bi.toString(16));
    }

    /**
     * 获取文件的SHA1值
     */
    public static String getFileSha1(MultipartFile multipartFile) {
        if (multipartFile.getSize() == 0){
            throw new ResultException(UploadResultEnum.NO_FILE_NULL);
        }
        byte[] buffer = new byte[4096];
        try (InputStream fis = multipartFile.getInputStream()) {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                sha1.update(buffer, 0, len);
            }
            BigInteger sha1Bi = new BigInteger(1, sha1.digest());
            return sha1Bi.toString(16);
        } catch (IOException | NoSuchAlgorithmException e) {
            return null;
        }
    }

}
