package com.linln.modules.system.repository;

import com.linln.common.constant.StatusConst;
import com.linln.modules.system.domain.Role;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
public interface RoleRepository extends BaseRepository<Role,Long> {

    /**
     * 查找多个角色
     * @param ids id列表
     */
    public List<Role> findByIdIn(List<Long> ids);

    /**
     * 查找相应状态的角色
     * @param sort 排序对象
     */
    public List<Role> findAllByStatus(Sort sort, Byte status);

    /**
     * 查询指定用户的角色列表
     * @param id 用户ID
     * @param status 角色状态
     */
    public Set<Role> findByUsers_IdAndStatus(Long id, Byte status);

    /**
     * 判断指定的用户是否存在角色
     * @param id 用户ID
     * @param status 角色状态
     */
    public Boolean existsByUsers_IdAndStatus(Long id, Byte status);

    /**
     * 取消角色与用户之间的关系
     * @param ids 角色ID
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sys_user_role WHERE role_id in ?1", nativeQuery = true)
    public Integer cancelUserJoin(List<Long> ids);

    /**
     * 取消角色与菜单之间的关系
     * @param ids 角色ID
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sys_role_menu WHERE role_id in ?1", nativeQuery = true)
    public Integer cancelMenuJoin(List<Long> ids);
}
