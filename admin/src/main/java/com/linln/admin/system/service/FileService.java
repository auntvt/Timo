package com.linln.admin.system.service;

import com.linln.admin.system.domain.File;

/**
 * @author 小懒虫
 * @date 2018/11/02
 */
public interface FileService {

    /**
     * 获取文件sha1值的记录
     */
    File isFile(String sha1);

    /**
     * 保存文件上传
     * @param file 文件上传实体类
     */
    File save(File file);
}

