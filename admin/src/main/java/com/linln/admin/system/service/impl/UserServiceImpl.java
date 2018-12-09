package com.linln.admin.system.service.impl;

import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.core.web.PageSort;
import com.linln.admin.system.domain.Dept;
import com.linln.admin.system.domain.User;
import com.linln.admin.system.repository.UserRepository;
import com.linln.admin.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
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
     * @param user 实体对象
     * @param deptIn 部门in查询数据
     * @return 返回分页数据
     */
    @Override
    public Page<User> getPageList(User user, List<Long> deptIn) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest(Sort.Direction.ASC.toString());

        // 使用Specification复杂查询
        return userRepository.findAll(new Specification<User>(){

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> preList = new ArrayList<>();

                if(user.getUsername() != null){
                    preList.add(cb.equal(root.get("username").as(String.class), user.getUsername()));
                }
                if(user.getNickname() != null){
                    preList.add(cb.like(root.get("nickname").as(String.class), "%"+ user.getNickname() + "%"));
                }
                if(deptIn != null){
                    Join<User, Dept> join = root.join("dept", JoinType.INNER);
                    CriteriaBuilder.In<Long> in = cb.in(join.get("id").as(Long.class));
                    deptIn.forEach(in::value);
                    preList.add(in);
                }

                // 数据状态
                if(!user.getStatus().equals(StatusEnum.DELETE.getCode())){
                    preList.add(cb.equal(root.get("status").as(Byte.class), user.getStatus()));
                }

                Predicate[] pres = new Predicate[preList.size()];
                return query.where(preList.toArray(pres)).getRestriction();
            }

        }, page);
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
