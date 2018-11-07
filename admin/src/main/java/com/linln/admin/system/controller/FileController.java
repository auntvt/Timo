package com.linln.admin.system.controller;

import com.linln.admin.core.enums.ResultEnum;
import com.linln.admin.core.exception.ResultException;
import com.linln.admin.core.utils.FileUpload;
import com.linln.admin.system.domain.File;
import com.linln.admin.system.service.FileService;
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
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传web格式图片
     */
    @PostMapping("/upload/image")
    @ResponseBody
    public ResultVo uploadImage(@RequestParam("image") MultipartFile multipartFile) {

        // 创建File实体对象
        File file = FileUpload.getFile(multipartFile);
        file.setPath("/images" + file.getPath());

        return saveImage(multipartFile, file);
    }

    /**
     * 上传web格式头像
     */
    @PostMapping("/upload/picture")
    @ResponseBody
    public ResultVo uploadPicture(@RequestParam("picture") MultipartFile multipartFile) {

        // 创建File实体对象
        File file = FileUpload.getFile(multipartFile);
        file.setPath("/picture" + file.getPath());

        return saveImage(multipartFile, file);
    }

    /**
     * 保存上传的web格式图片
     */
    private ResultVo saveImage(MultipartFile multipartFile, File file) {
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
        File isFile = fileService.isFile(FileUpload.getFileSHA1(multipartFile));
        if (isFile != null) {
            return ResultVoUtil.success(isFile);
        }

        try {
            FileUpload.transferTo(multipartFile, file);
        } catch (IOException | NoSuchAlgorithmException e) {
            return ResultVoUtil.error("上传头像失败");
        }

        // 将文件信息保存到数据库中
        fileService.save(file);
        return ResultVoUtil.success(file);
    }

}
