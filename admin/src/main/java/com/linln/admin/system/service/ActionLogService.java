package com.linln.admin.system.service;

import com.linln.admin.system.domain.ActionLog;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     */
    ActionLog getId(Long id);

    /**
     * 获取数据的日志列表
     * @param model 模型（表名）
     * @param recordId 数据ID
     */
    List<ActionLog> getDataLogList(String model, Long recordId);

    /**
     * 保存日志
     * @param actionLog 日志实体类
     */
    ActionLog save(ActionLog actionLog);

    /**
     * 删除指指定ID日志
     */
    @Transactional
    void deleteId(Long id);

    /**
     * 清空日志
     */
    @Transactional
    void emptyLog();
}
