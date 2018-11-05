package com.linln.admin.system.service;

import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.system.domain.File;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/11/02
 */
public interface FileService {
    File isFile(String sha1);

    File save(File file);
}

