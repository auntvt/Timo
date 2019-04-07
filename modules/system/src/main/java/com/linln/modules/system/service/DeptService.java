package com.linln.modules.system.service;

import com.linln.common.enums.StatusEnum;
import com.linln.modules.system.domain.Dept;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/12/02
 */
public interface DeptService {

    /**
     * 获取部门列表数据
     * @param example 查询实例
     * @param sort 排序对象
     */
    List<Dept> getListByExample(Example<Dept> example, Sort sort);

    /**
     * 获取排序最大值
     * @param pid 父菜单ID
     */
    Integer getSortMax(Long pid);

    /**
     * 根据父级部门ID获取本级全部部门
     * @param pid 父部门ID
     * @param notId 需要排除的部门ID
     */
    List<Dept> getListByPid(Long pid, Long notId);

    /**
     * 保存多个部门
     * @param deptList 部门实体类列表
     */
    List<Dept> save(List<Dept> deptList);

    /**
     * 根据部门管理ID查询部门管理数据
     * @param id 部门管理ID
     */
    Dept getById(Long id);

    /**
     * 根据ID查找子孙部门
     * @param id [id]形式
     */
    List<Dept> getListByPidLikeOk(Long id);

    /**
     * 保存部门管理
     * @param dept 部门管理实体类
     */
    Dept save(Dept dept);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}

