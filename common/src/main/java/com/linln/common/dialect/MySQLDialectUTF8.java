package com.linln.common.dialect;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * 重写数据库方言，设置默认字符集为utf8
 * @author 小懒虫
 * @date 2018/10/15
 */
public class MySQLDialectUTF8 extends MySQL5Dialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
