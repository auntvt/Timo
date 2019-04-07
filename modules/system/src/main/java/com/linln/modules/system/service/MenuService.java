package com.linln.modules.system.service;

import com.linln.common.enums.StatusEnum;
import com.linln.modules.system.domain.Menu;
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
     * 获取菜单列表数据
     * @param example 查询实例
     * @param sort 排序对象
     */
    List<Menu> getListByExample(Example<Menu> example, Sort sort);

    /**
     * 根据菜单对象的Example判断是否存在
     * @param menu 菜单对象
     */
    Menu getByMenuToExample(Menu menu);

    /**
     * 根据菜单ID查询菜单数据
     * @param id 菜单ID
     */
    Menu getById(Long id);

    /**
     * 根据菜单url查询菜单数据
     * @param url 菜单url
     */
    Menu getByUrl(String url);

    /**
     * 获取菜单列表数据
     */
    List<Menu> getListBySortOk();

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
    List<Menu> getListByPid(Long pid, Long notId);

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
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}
