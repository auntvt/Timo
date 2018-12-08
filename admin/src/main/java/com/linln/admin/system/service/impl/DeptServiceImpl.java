package com.linln.admin.system.service.impl;

import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.system.domain.Dept;
import com.linln.admin.system.repository.DeptRepository;
import com.linln.admin.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/12/02
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptRepository deptRepository;

    /**
     * 根据部门管理ID查询部门管理数据
     * @param id 部门管理ID
     */
    @Override
    @Transactional
    public Dept getId(Long id) {
        Byte[] status = {StatusEnum.OK.getCode(), StatusEnum.FREEZED.getCode()};
        return deptRepository.findByIdAndStatusIn(id, status);
    }

    /**
     * 获取部门列表数据
     * @param example 查询实例
     * @param sort 排序对象
     */
    @Override
    public List<Dept> getList(Example<Dept> example, Sort sort) {
        return deptRepository.findAll(example, sort);
    }

    /**
     * 获取排序最大值
     * @param pid 父菜单ID
     */
    @Override
    public Integer getSortMax(Long pid){
        return deptRepository.findSortMax(pid);
    }

    /**
     * 根据父级部门ID获取本级全部部门
     * @param pid 父部门ID
     * @param notId 需要排除的部门ID
     */
    @Override
    public List<Dept> getPid(Long pid, Long notId){
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        Byte[] bytes = {StatusEnum.OK.getCode(), StatusEnum.FREEZED.getCode()};
        return deptRepository.findByPidAndIdNotAndStatusIn(sort, pid, notId, bytes);
    }

    /**
     * 根据ID查找子孙部门
     * @param id [id]形式
     */
    @Override
    public List<Dept> getPidsLike(Long id){
        return deptRepository.findByPidsLikeAndStatus("%["+id+"]%", StatusEnum.OK.getCode());
    }

    /**
     * 保存部门管理
     * @param dept 部门管理实体类
     */
    @Override
    public Dept save(Dept dept){
        return deptRepository.save(dept);
    }

    /**
     * 保存多个部门
     * @param deptList 部门实体类列表
     */
    @Override
    public List<Dept> save(List<Dept> deptList){
        return deptRepository.saveAll(deptList);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Integer updateStatus(StatusEnum statusEnum, List<Long> idList){
        // 获取与之关联的所有部门
        Set<Dept> treeDeptList = new HashSet<>();
        idList.forEach(id -> {
            Optional<Dept> dept = deptRepository.findById(id);
            List<Dept> list = deptRepository.findByPidsLikeAndStatus("%["+id+"]%", dept.get().getStatus());
            treeDeptList.add(dept.get());
            treeDeptList.addAll(list);
        });

        treeDeptList.forEach(dept -> {
            // 更新关联的所有部门状态
            dept.setStatus(statusEnum.getCode());
        });

        return treeDeptList.size();
    }
}

