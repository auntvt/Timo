package com.linln.admin.system.service;

import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.system.domain.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
public interface UserService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @param pageIndex 页码
     * @param pageSize 获取列表长度
     * @return 返回分页数据
     */
    Page<User> getPageList(Example<User> example, Integer pageIndex, Integer pageSize);

    /**
     * 保存用户
     * @param user 用户实体类
     */
    User save(User user);

    /**
     * 保存用户列表
     * @param userList 用户实体类
     */
    List<User> save(List<User> userList);

    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @param statusEnums 用户状态
     * @return 用户数据
     */
    User getByName(String username, Byte... statusEnums);

    /**
     * 根据用户ID查询用户数据
     * @param id 用户ID
     */
    User getId(Long id);

    /**
     * 根据用户ID列表查询多个用户数据
     * @param ids 用户ID列表
     */
    List<User> getIdList(List<Long> ids);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Integer updateStatus(StatusEnum statusEnum, List<Long> idList);
}
