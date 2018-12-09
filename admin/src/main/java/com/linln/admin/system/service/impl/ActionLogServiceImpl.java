package com.linln.admin.system.service.impl;

import com.linln.admin.core.web.PageSort;
import com.linln.admin.system.domain.ActionLog;
import com.linln.admin.system.repository.ActionLogRepository;
import com.linln.admin.system.service.ActionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActionLogServiceImpl implements ActionLogService {

    @Autowired
    private ActionLogRepository actionLogRepository;

    /**
     * 根据日志ID查询日志数据
     * @param id 日志ID
     */
    @Override
    @Transactional
    public ActionLog getId(Long id) {
        return actionLogRepository.findById(id).get();
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<ActionLog> getPageList(Example<ActionLog> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return actionLogRepository.findAll(example, page);
    }

    /**
     * 获取数据的日志列表
     * @param model 模型（表名）
     * @param recordId 数据ID
     */
    @Override
    public List<ActionLog> getDataLogList(String model, Long recordId){
        return actionLogRepository.findByModelAndRecordId(model, recordId);
    }

    /**
     * 保存日志
     * @param actionLog 日志实体类
     */
    @Override
    public ActionLog save(ActionLog actionLog){
        return actionLogRepository.save(actionLog);
    }

    /**
     * 删除指指定ID日志
     */
    @Override
    @Transactional
    public void deleteId(Long id){
        actionLogRepository.deleteById(id);
    }

    /**
     * 清空日志
     */
    @Override
    @Transactional
    public void emptyLog(){
        actionLogRepository.deleteAll();
    }
}
