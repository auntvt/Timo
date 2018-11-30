package com.linln.admin.system.service.impl;

import com.linln.admin.system.domain.File;
import com.linln.admin.system.repository.FileRepository;
import com.linln.admin.system.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 小懒虫
 * @date 2018/11/02
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    /**
     * 获取文件sha1值的记录
     */
    @Override
    public File isFile(String sha1) {
        return fileRepository.findBySha1(sha1);
    }

    /**
     * 保存文件上传
     * @param file 文件上传实体类
     */
    @Override
    public File save(File file){
        return fileRepository.save(file);
    }
}

