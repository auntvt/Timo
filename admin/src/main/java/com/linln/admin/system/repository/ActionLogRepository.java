package com.linln.admin.system.repository;

import com.linln.admin.system.domain.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {

    /**
     * 根据模型和数据ID查询日志列表
     * @param model 模型（表名）
     * @param recordId 数据ID
     */
    public List<ActionLog> findByModelAndRecordId(String model, Long recordId);
}
