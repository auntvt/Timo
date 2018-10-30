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

    @Transactional
    List<Menu> getIdList(List<Long> ids);

    List<Menu> getList(Example<Menu> example, Sort sort);

    Menu getId(Long id);

    Menu getUrl(String url);

    List<Menu> getList(Sort sort);

    Integer getSortMax(Long pid);

    Menu save(Menu menu);

    @Transactional
    Integer updateStatus(StatusEnum statusEnum, List<Long> idList);
}
