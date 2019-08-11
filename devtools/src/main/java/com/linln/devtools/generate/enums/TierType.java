package com.linln.devtools.generate.enums;

/**
 * 业务层结构类型
 *
 * @author 小懒虫
 * @date 2019/4/4
 */
public enum TierType {
    /**
     * 实体类
     */
    DOMAIN,
    /**
     * service接口层
     */
    SERVICE,
    /**
     * service接口实现层
     */
    SERVICE_IMPL,
    /**
     * dao持久层（repository）
     */
    DAO,
    /**
     * 控制器层
     */
    CONTROLLER,
    /**
     * 验证规则
     */
    VALID,
    /**
     * 首页
     */
    INDEX,
    /**
     * 添加/更新页面
     */
    ADD,
    /**
     * 详情页面
     */
    DETAIL;
}
