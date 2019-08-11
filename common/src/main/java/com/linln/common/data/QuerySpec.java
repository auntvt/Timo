package com.linln.common.data;

import com.linln.common.enums.ResultEnum;
import com.linln.common.enums.StatusEnum;
import com.linln.common.exception.ResultException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author 小懒虫
 * @date 2018/12/7
 */
public class QuerySpec {
    /* 查询规则 */
    /** 精确查询(=) */
    public static final Long EQUAL = 0L;
    /** 模糊查询(*XX*) */
    public static final Long LIKE = 1L;
    /** 左模糊查询(*XX) */
    public static final Long LEFT_LIKE = 2L;
    /** 右模糊查询(XX*) */
    public static final Long RIGHT_LIKE = 3L;
    /** 不等于(!=) */
    public static final Long NOT_EQUAL = 4L;
    /** 大于(>) */
    public static final Long GT = 5L;
    /** 大于等于(>=) */
    public static final Long GE = 6L;
    /** 小于(<) */
    public static final Long LT = 7L;
    /** 小于等于(<=) */
    public static final Long LE = 8L;
    /** 多值(in) */
    public static final Long IN = 9L;
    /** 区间查询(between) */
    public static final Long BETWEEN = 10L;

    /** 字段规则列表 */
    private Map<String, Long> fieldRules;
    /** 忽视字段 */
    private String[] ignoredPaths;
    /** 多值in查询方式值列表 */
    private Map<String, List<Object>> inValues;
    /** 区间between查询方式值列表 */
    private Map<String, Long[]> betweenValues;
    /** 状态字段名称 */
    private String status = "status";

    /**
     * 无参构造方法，初始化数据
     */
    private QuerySpec(){
        fieldRules = new HashMap<>();
        inValues = new HashMap<>();
        betweenValues = new HashMap<>();
    }

    /**
     * 创建查询匹配器
     */
    public static QuerySpec matching(){
        return new QuerySpec();
    }

    /**
     * 添加一条匹配规则
     * @param propertyPath 需要验证的字段名称
     * @param regulation 查询规则
     */
    public QuerySpec withMatcher(String propertyPath, Long regulation){
        fieldRules.put(propertyPath, regulation);
        return this;
    }

    /**
     * 添加一条IN匹配规则
     * @param propertyPath 需要验证的字段名称
     * @param inValueList IN数据列表
     */
    public QuerySpec withMatcherIn(String propertyPath, List<Object> inValueList){
        fieldRules.put(propertyPath, IN);
        inValues.put(propertyPath, inValueList);
        return this;
    }

    /**
     * 添加一条BETWEEN匹配规则
     * @param propertyPath 需要验证的字段名称
     * @param x 第一个值
     * @param y 第二个值
     */
    public QuerySpec withMatcherBetween(String propertyPath, Long x, Long y){
        fieldRules.put(propertyPath, BETWEEN);
        betweenValues.put(propertyPath, new Long[]{x, y});
        return this;
    }

    /**
     * 设置忽略的字段
     * @param ignoredPaths 忽略字段
     */
    public QuerySpec withIgnorePaths(String... ignoredPaths){
        this.ignoredPaths = ignoredPaths;
        return this;
    }

    /**
     * 获取规则列表
     */
    private Map<String, Long> getFieldRules(){
        return fieldRules;
    }

    /**
     * 获取忽略字段列表
     */
    private List<String> getIgnoredPaths(){
        return Arrays.asList(ignoredPaths);
    }

    /**
     * 获取in查询方式值列表
     */
    private Map<String, List<Object>> getInValues(){
        return inValues;
    }

    /**
     * 获取区间between查询方式值列表
     */
    private Map<String, Long[]> getBetweenValues(){
        return betweenValues;
    }

    /**
     * 创建Specification<T>复杂查询实例
     * @param probe 实体对象
     * @param querySpec 匹配器
     */
    public static <T> Specification<T> of(T probe, QuerySpec querySpec){

        // 获取用户规则
        Map<String, Long> fieldRules = querySpec.getFieldRules();
        // 获取忽略字段
        List<String> ignoredPaths = querySpec.getIgnoredPaths();
        // 获取in查询方式值列表
        Map<String, List<Object>> inValues = querySpec.getInValues();
        // 获取in查询方式值列表
        Map<String, Long[]> betweenValues = querySpec.getBetweenValues();

        // 创建Specification<T>对象
        Specification<T> specification = new Specification<T>(){

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> preList = new ArrayList<>();
                // 通过反射遍历实体对象
                try {
                    final BeanInfo bi = Introspector.getBeanInfo(probe.getClass());
                    for (final PropertyDescriptor pd : bi.getPropertyDescriptors()) {
                        final Object value = pd.getReadMethod().invoke(probe, (Object[]) null);
                        if(!(value instanceof Class) && value != null && !ignoredPaths.contains(pd.getName())){
                            // 判断是否对数据状态进行非法请求
                            if(querySpec.status.equals(pd.getName()) && StatusEnum.DELETE.getCode().equals(Byte.valueOf(String.valueOf(value)))){
                                throw new ResultException(ResultEnum.STATUS_ERROR);
                            }

                            // 进行查询匹配器配对
                            if(fieldRules.containsKey(pd.getName())){
                                Long regulation = fieldRules.get(pd.getName());
                                // 精确查询
                                if(regulation.equals(QuerySpec.EQUAL)){
                                    preList.add(cb.equal(root.get(pd.getName()).as(value.getClass()), value));
                                }else
                                // 模糊查询
                                if(regulation.equals(QuerySpec.LIKE)){
                                    preList.add(cb.like(root.get(pd.getName()).as(String.class), "%"+ String.valueOf(value) +"%"));
                                }else
                                // 左模糊查询
                                if(regulation.equals(QuerySpec.LEFT_LIKE)){
                                    preList.add(cb.like(root.get(pd.getName()).as(String.class), "%"+ String.valueOf(value)));
                                }else
                                // 右模糊查询
                                if(regulation.equals(QuerySpec.RIGHT_LIKE)){
                                    preList.add(cb.like(root.get(pd.getName()).as(String.class), String.valueOf(value) +"%"));
                                }else
                                // 不等于查询
                                if(regulation.equals(QuerySpec.NOT_EQUAL)){
                                    preList.add(cb.notEqual(root.get(pd.getName()).as(value.getClass()), value));
                                }else
                                // 大于查询
                                if(regulation.equals(QuerySpec.GT)){
                                    preList.add(cb.gt(root.get(pd.getName()).as(Long.class), Long.valueOf(String.valueOf(value))));
                                }else
                                // 大于等于查询
                                if(regulation.equals(QuerySpec.GE)){
                                    preList.add(cb.ge(root.get(pd.getName()).as(Long.class), Long.valueOf(String.valueOf(value))));
                                }else
                                // 小于查询
                                if(regulation.equals(QuerySpec.LT)){
                                    preList.add(cb.lt(root.get(pd.getName()).as(Long.class), Long.valueOf(String.valueOf(value))));
                                }else
                                // 小于等于查询
                                if(regulation.equals(QuerySpec.LE)){
                                    preList.add(cb.le(root.get(pd.getName()).as(Long.class), Long.valueOf(String.valueOf(value))));
                                }else
                                // 多值查询
                                if(regulation.equals(QuerySpec.IN)){
                                    CriteriaBuilder.In<Object> in = cb.in(root.get(pd.getName()));
                                    List<Object> inList = inValues.get(pd.getName());
                                    if(inList != null){
                                        inList.forEach(in::value);
                                        preList.add(in);
                                    }
                                }else
                                // 区间查询
                                if(regulation.equals(QuerySpec.BETWEEN)){
                                    Long[] between = betweenValues.get(pd.getName());
                                    preList.add(cb.between(root.get(pd.getName()), between[0], between[1]));
                                }
                            }else{
                                preList.add(cb.equal(root.get(pd.getName()).as(value.getClass()), value));
                            }
                        }
                    }
                } catch (InvocationTargetException | IntrospectionException | IllegalAccessException e) {
                    throw new IllegalArgumentException("获取实体类数据时出错，请检查实体类Bean格式是否规范！", e);
                }

                Predicate[] pres = new Predicate[preList.size()];
                return query.where(preList.toArray(pres)).getRestriction();
            }
        };

        return specification;
    }
}
