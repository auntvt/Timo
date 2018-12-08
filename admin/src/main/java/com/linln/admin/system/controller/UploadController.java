package com.linln.admin.system.controller;

import com.linln.admin.core.enums.ResultEnum;
import com.linln.admin.core.exception.ResultException;
import com.linln.admin.core.web.FileUpload;
import com.linln.admin.system.domain.Upload;
import com.linln.admin.system.service.UploadService;
import com.linln.core.utils.ResultVoUtil;
import com.linln.core.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author 小懒虫
 * @date 2018/11/02
 */
@Controller
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传web格式图片
     */
    @PostMapping("/upload/image")
    @ResponseBody
    public ResultVo uploadImage(@RequestParam("image") MultipartFile multipartFile) {

        // 创建Upload实体对象
        Upload upload = FileUpload.getFile(multipartFile, "/images");
        try {
            return saveImage(multipartFile, upload);
        } catch (IOException | NoSuchAlgorithmException e) {
            return ResultVoUtil.error("上传图片失败");
        }
    }

    /**
     * 上传web格式头像
     */
    @PostMapping("/upload/picture")
    @ResponseBody
    public ResultVo uploadPicture(@RequestParam("picture") MultipartFile multipartFile) {

        // 创建Upload实体对象
        Upload upload = FileUpload.getFile(multipartFile, "/picture");
        try {
            return saveImage(multipartFile, upload);
        } catch (IOException | NoSuchAlgorithmException e) {
            return ResultVoUtil.error("上传头像失败");
        }
    }

    /**
     * 保存上传的web格式图片
     */
    private ResultVo saveImage(MultipartFile multipartFile, Upload upload) throws IOException, NoSuchAlgorithmException {
        // 判断是否为支持的图片格式
        String[] types = {
                "image/gif",
                "image/jpg",
                "image/jpeg",
                "image/png"
        };
        if(!FileUpload.isContentType(multipartFile, types)){
            throw new ResultException(ResultEnum.NO_FILE_TYPE);
        }

        // 判断图片是否存在
        Upload isFile = uploadService.isFile(FileUpload.getFileSHA1(multipartFile));
        if (isFile != null) {
            return ResultVoUtil.success(isFile);
        }

        FileUpload.transferTo(multipartFile, upload);
        // 将文件信息保存到数据库中
        uploadService.save(upload);
        return ResultVoUtil.success(upload);
    }

}
