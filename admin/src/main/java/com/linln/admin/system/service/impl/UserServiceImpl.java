package com.linln.admin.system.service.impl;

import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.system.domain.User;
import com.linln.admin.system.repository.UserRepository;
import com.linln.admin.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @param status 用户状态
     * @return 用户数据
     */
    @Override
    @Transactional
    public User getByName(String username, Byte... status) {
        Byte[] newStatus = new Byte[status.length + 1];
        newStatus[0] = StatusEnum.OK.getCode();
        System.arraycopy(status, 0, newStatus, 1, status.length);
        return userRepository.findByUsernameAndStatusIn(username, newStatus);
    }

    /**
     * 根据用户ID查询用户数据
     * @param id 用户ID
     */
    @Override
    public User getId(Long id) {
        Byte[] status = {StatusEnum.OK.getCode(), StatusEnum.FREEZED.getCode()};
        return userRepository.findByIdAndStatusIn(id, status);
    }

    /**
     * 根据用户ID列表查询多个用户数据
     * @param ids 用户ID列表
     */
    @Override
    @Transactional
    public List<User> getIdList(List<Long> ids) {
        return userRepository.findByIdInAndStatus(ids, StatusEnum.OK.getCode());
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @param pageIndex 页码
     * @param pageSize 获取列表长度
     * @return 返回分页数据
     */
    @Override
    public Page<User> getPageList(Example<User> example, Integer pageIndex, Integer pageSize) {
        // 创建分页对象
        Sort sort = new Sort(Sort.Direction.ASC, "createDate");
        PageRequest page = PageRequest.of(pageIndex-1, pageSize, sort);
        return userRepository.findAll(example, page);
    }

    /**
     * 保存用户
     * @param user 用户实体类
     */
    @Override
    public User save(User user){
        return userRepository.save(user);
    }

    /**
     * 保存用户列表
     * @param userList 用户实体类
     */
    @Override
    @Transactional
    public List<User> save(List<User> userList){
        return userRepository.saveAll(userList);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Integer updateStatus(StatusEnum statusEnum, List<Long> idList){
        // 删除用户时取消与角色的关联
        if(statusEnum == StatusEnum.DELETE){
            idList.forEach(id -> {
                Optional<User> optional = userRepository.findById(id);
                optional.ifPresent(user -> user.setRoles(null));
            });
        }
        return userRepository.updateStatus(statusEnum.getCode(),idList);
    }
}
