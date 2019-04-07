package com.linln.modules.system.service.impl;

import com.linln.modules.system.domain.Upload;
import com.linln.modules.system.repository.UploadRepository;
import com.linln.modules.system.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 小懒虫
 * @date 2018/11/02
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private UploadRepository uploadRepository;

    /**
     * 获取文件sha1值的记录
     */
    @Override
    public Upload getBySha1(String sha1) {
        return uploadRepository.findBySha1(sha1);
    }

    /**
     * 保存文件上传
     * @param upload 文件上传实体类
     */
    @Override
    public Upload save(Upload upload){
        return uploadRepository.save(upload);
    }
}

