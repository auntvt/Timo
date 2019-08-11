package com.linln.modules.system.service;

import com.linln.common.enums.StatusEnum;
import com.linln.modules.system.domain.Dict;
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
     * @return 字典信息
     */
    Dict getByNameOk(String name);

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Dict> getPageList(Example<Dict> example);

    /**
     * 根据字典ID查询字典数据
     * @param id 字典ID
     * @return 字典信息
     */
    Dict getById(Long id);

    /**
     * 字典标识是否重复
     * @param dict 字典实体类
     * @return 是否重复
     */
    boolean repeatByName(Dict dict);

    /**
     * 保存字典
     * @param dict 字典实体类
     * @return 字典信息
     */
    Dict save(Dict dict);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     * @param statusEnum 数据状态
     * @param idList 数据ID列表
     * @return 操作结果
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}
