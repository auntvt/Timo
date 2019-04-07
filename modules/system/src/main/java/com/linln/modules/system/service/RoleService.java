package com.linln.modules.system.service;

import com.linln.common.enums.StatusEnum;
import com.linln.modules.system.domain.Role;
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
    Set<Role> getUserOkRoleList(Long id);

    /**
     * 判断指定的用户是否存在角色
     * @param id 用户ID
     */
    Boolean existsUserOk(Long id);

    /**
     * 根据角色ID查询角色数据
     * @param id 角色ID
     */
    @Transactional
    Role getById(Long id);

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
    List<Role> getListBySortOk(Sort sort);

    /**
     * 保存角色
     * @param role 角色实体类
     */
    Role save(Role role);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}
