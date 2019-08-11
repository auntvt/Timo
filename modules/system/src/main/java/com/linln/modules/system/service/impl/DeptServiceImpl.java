package com.linln.modules.system.service.impl;

import com.linln.common.enums.ResultEnum;
import com.linln.common.enums.StatusEnum;
import com.linln.common.exception.ResultException;
import com.linln.modules.system.domain.Dept;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.repository.DeptRepository;
import com.linln.modules.system.repository.UserRepository;
import com.linln.modules.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/12/02
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 根据部门管理ID查询部门管理数据
     * @param id 部门管理ID
     */
    @Override
    @Transactional
    public Dept getById(Long id) {
        return deptRepository.findById(id).orElse(null);
    }

    /**
     * 获取部门列表数据
     * @param example 查询实例
     * @param sort 排序对象
     */
    @Override
    public List<Dept> getListByExample(Example<Dept> example, Sort sort) {
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
    public List<Dept> getListByPid(Long pid, Long notId){
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        return deptRepository.findByPidAndIdNot(sort, pid, notId);
    }

    /**
     * 根据ID查找子孙部门
     * @param id [id]形式
     */
    @Override
    public List<Dept> getListByPidLikeOk(Long id){
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
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> ids){
        // 获取与之关联的所有部门
        Set<Dept> treeDepts = new HashSet<>();
        List<Dept> depts = deptRepository.findByIdIn(ids);
        depts.forEach(dept -> {
            treeDepts.add(dept);
            treeDepts.addAll(deptRepository.findByPidsLikeAndStatus("%[" + dept.getId() + "]%", dept.getStatus()));
        });

        treeDepts.forEach(dept -> {
            if(statusEnum == StatusEnum.DELETE){
                List<User> users = userRepository.findByDept(dept);
                if(users.size() > 0){
                    throw new ResultException(ResultEnum.DEPT_EXIST_USER);
                }
            }
            // 更新关联的所有部门状态
            dept.setStatus(statusEnum.getCode());
        });

        return treeDepts.size() > 0;
    }
}

