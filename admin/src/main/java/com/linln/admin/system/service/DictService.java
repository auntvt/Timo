package com.linln.admin.system.service;

import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.system.domain.Dict;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
public interface DictService {

    /**
     * 根据字典标识获取字典数据
     * @param name 字典标识
     */
    Dict getName(String name);

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Dict> getPageList(Example<Dict> example);

    /**
     * 根据字典ID查询字典数据
     * @param id 字典ID
     */
    Dict getId(Long id);

    /**
     * 保存字典
     * @param dict 字典实体类
     */
    Dict save(Dict dict);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Integer updateStatus(StatusEnum statusEnum, List<Long> idList);
}
