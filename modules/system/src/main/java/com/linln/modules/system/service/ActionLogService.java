package com.linln.modules.system.service;

import com.linln.modules.system.domain.ActionLog;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/10/19
 */
public interface ActionLogService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<ActionLog> getPageList(Example<ActionLog> example);

    /**
     * 根据日志ID查询日志数据
     * @param id 日志ID
     * @return 日志数据
     */
    ActionLog getById(Long id);

    /**
     * 获取数据的日志列表
     * @param model 模型（表名）
     * @param recordId 数据ID
     * @return 日志列表
     */
    List<ActionLog> getDataLogList(String model, Long recordId);

    /**
     * 保存日志
     * @param actionLog 日志实体类
     * @return 日志信息
     */
    ActionLog save(ActionLog actionLog);

    /**
     * 删除指指定ID日志
     * @param id 日志ID
     */
    void deleteId(Long id);

    /**
     * 清空日志
     */
    @Transactional
    void emptyLog();
}
