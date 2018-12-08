package com.linln.admin.core.web;

import com.linln.core.utils.HttpServletUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author 小懒虫
 * @date 2018/12/8
 */
public class PageSort {

    private static final Integer pageSizeDef = 10;
    private static final String orderByColumnDef = "createDate";
    private static final String isAscDef = "desc";

    /**
     * 创建分页排序对象
     */
    public static PageRequest pageRequest(){
        return pageRequest(pageSizeDef, orderByColumnDef, isAscDef);
    }

    /**
     * 创建分页排序对象
     * @param isAscDef 排序方式默认值
     */
    public static PageRequest pageRequest(String isAscDef){
        return pageRequest(pageSizeDef, orderByColumnDef, isAscDef);
    }

    /**
     * 创建分页排序对象
     * @param orderByColumnDef 排序字段名称默认值
     * @param isAscDef 排序方式默认值
     */
    public static PageRequest pageRequest(String orderByColumnDef, String isAscDef){
        return pageRequest(pageSizeDef, orderByColumnDef, isAscDef);
    }

    /**
     * 创建分页排序对象
     * @param pageSizeDef 分页数据数量默认值
     * @param orderByColumnDef 排序字段名称默认值
     * @param isAscDef 排序方式默认值
     */
    public static PageRequest pageRequest(Integer pageSizeDef, String orderByColumnDef, String isAscDef){
        Integer pageIndex = HttpServletUtil.getParameterInt("page", 1);
        Integer pageSize = HttpServletUtil.getParameterInt("size", pageSizeDef);
        String orderByColumn = HttpServletUtil.getParameter("orderByColumn", orderByColumnDef);
        String direction = HttpServletUtil.getParameter("isAsc", isAscDef);
        Sort sort = new Sort(Sort.Direction.fromString(direction), orderByColumn);
        return PageRequest.of(pageIndex-1, pageSize, sort);
    }
}
