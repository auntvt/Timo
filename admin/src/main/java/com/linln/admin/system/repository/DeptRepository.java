package com.linln.admin.system.repository;

import com.linln.admin.system.domain.Dept;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/12/02
 */
public interface DeptRepository extends BaseRepository<Dept, Long> {

    /**
     * 获取排序最大值
     * @param pid 父部门ID
     */
    @Query("select max(sort) from Menu m where m.pid = ?1")
    public Integer findSortMax(long pid);

    /**
     * 根据父ID查找子孙部门
     * @param pids pid列表
     */
    public List<Dept> findByPidsLikeAndStatus(String pids, Byte status);

    /**
     * 根据父级部门ID获取本级全部部门
     * @param sort 排序对象
     * @param pid 父部门ID
     * @param notId 需要排除的部门ID
     */
    public List<Dept> findByPidAndIdNotAndStatusIn(Sort sort, long pid, long notId, Byte[] bytes);
}

