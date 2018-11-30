package com.linln.admin.system.service;

import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.system.domain.Menu;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
public interface MenuService {

    /**
     * 根据菜单ID列表查询多个菜单数据
     * @param ids 菜单ID列表
     */
    @Transactional
    List<Menu> getIdList(List<Long> ids);

    /**
     * 获取菜单列表数据
     * @param example 查询实例
     * @param sort 排序对象
     */
    List<Menu> getList(Example<Menu> example, Sort sort);

    /**
     * 根据菜单ID查询菜单数据
     * @param id 菜单ID
     */
    Menu getId(Long id);

    /**
     * 根据菜单url查询菜单数据
     * @param url 菜单url
     */
    Menu getUrl(String url);

    /**
     * 获取菜单列表数据
     * @param sort 排序对象
     */
    List<Menu> getList(Sort sort);

    /**
     * 获取排序最大值
     * @param pid 父菜单ID
     */
    Integer getSortMax(Long pid);

    /**
     * 根据父级菜单ID获取本级全部菜单
     * @param pid 父菜单ID
     * @param notId 需要排除的菜单ID
     */
    List<Menu> getPid(Long pid, Long notId);

    /**
     * 保存菜单
     * @param menu 菜单实体类
     */
    Menu save(Menu menu);

    /**
     * 保存多个菜单
     * @param menuList 菜单实体类列表
     */
    List<Menu> save(List<Menu> menuList);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Integer updateStatus(StatusEnum statusEnum, List<Long> idList);
}
