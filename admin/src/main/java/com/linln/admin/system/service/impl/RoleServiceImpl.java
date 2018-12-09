package com.linln.admin.system.service.impl;

import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.core.web.PageSort;
import com.linln.admin.system.domain.Role;
import com.linln.admin.system.repository.RoleRepository;
import com.linln.admin.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 获取用户角色列表
     * @param id 用户ID
     */
    @Override
    @Transactional
    public Set<Role> getUserRoleList(Long id) {
        Byte status = StatusEnum.OK.getCode();
        return roleRepository.findByUsers_IdAndStatus(id, status);
    }

    /**
     * 根据角色ID查询角色数据
     * @param id 角色ID
     */
    @Override
    @Transactional
    public Role getId(Long id) {
        Byte[] status = {StatusEnum.OK.getCode(), StatusEnum.FREEZED.getCode()};
        return roleRepository.findByIdAndStatusIn(id, status);
    }

    /**
     * 根据角色ID列表查询多个角色数据
     * @param ids 角色ID列表
     */
    @Override
    @Transactional
    public List<Role> getIdList(List<Long> ids) {
        return roleRepository.findByIdInAndStatus(ids, StatusEnum.OK.getCode());
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Role> getPageList(Example<Role> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest(Sort.Direction.ASC.toString());
        return roleRepository.findAll(example, page);
    }

    /**
     * 获取角色列表数据
     * @param sort 排序对象
     */
    @Override
    public List<Role> getList(Sort sort) {
        return roleRepository.findAllByStatus(sort, StatusEnum.OK.getCode());
    }


    /**
     * 保存角色
     * @param role 角色实体类
     */
    @Override
    public Role save(Role role){
        return roleRepository.save(role);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Integer updateStatus(StatusEnum statusEnum, List<Long> idList){
        // 删除角色时取消与角色和菜单的关联
        if(statusEnum == StatusEnum.DELETE){
            idList.forEach(id -> {
                Optional<Role> optional = roleRepository.findById(id);
                optional.ifPresent(role -> {
                    role.setMenus(null);
                    role.getUsers().forEach(user -> user.getRoles().remove(role));
                });
            });
        }
        return roleRepository.updateStatus(statusEnum.getCode(),idList);
    }
}
