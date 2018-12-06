package com.linln.admin.system.repository;

import com.linln.admin.system.domain.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 小懒虫
 * @date 2018/11/02
 */
public interface UploadRepository extends JpaRepository<Upload, Long> {

    /**
     * 查找指定文件sha1记录
     * @param sha1 文件sha1值
     */
    public Upload findBySha1(String sha1);
}

