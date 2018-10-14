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
    @Transactional
    Set<Role> getUserRoleList(Long id);

    @Transactional
    Role getId(Long id);

    @Transactional
    List<Role> getIdList(List<Long> ids);

    Page<Role> getPageList(Example<Role> example, Integer pageIndex, Integer pageSize);

    List<Role> getList(Sort sort);

    Role save(Role role);

    @Transactional
    Integer updateStatus(StatusEnum statusEnum, List<Long> idList);
}
