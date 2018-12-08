package com.linln.admin.system.service;


import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.system.domain.Role;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
public interface RoleService {

    /**
     * 获取用户角色列表
     * @param id 用户ID
     */
    @Transactional
    Set<Role> getUserRoleList(Long id);

    /**
     * 根据角色ID查询角色数据
     * @param id 角色ID
     */
    @Transactional
    Role getId(Long id);

    /**
     * 根据角色ID列表查询多个角色数据
     * @param ids 角色ID列表
     */
    @Transactional
    List<Role> getIdList(List<Long> ids);

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Role> getPageList(Example<Role> example);

    /**
     * 获取角色列表数据
     * @param sort 排序对象
     */
    List<Role> getList(Sort sort);

    /**
     * 保存角色
     * @param role 角色实体类
     */
    Role save(Role role);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Integer updateStatus(StatusEnum statusEnum, List<Long> idList);
}
