package com.linln.component.shiro.remember;

import com.linln.modules.system.domain.Dept;
import org.hibernate.HibernateException;
import org.hibernate.LazyInitializationException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import java.io.Serializable;

/**
 * 记住我部门适配器（用于判断延迟加载超时）
 *
 * @author 小懒虫
 * @date 2019/10/30
 */
public class RememberMeDept extends Dept implements HibernateProxy {

    @Override
    public Object writeReplace() {
        return null;
    }

    @Override
    public LazyInitializer getHibernateLazyInitializer() {

        return new LazyInitializer() {
            @Override
            public void initialize() throws HibernateException {
                throw new LazyInitializationException(RememberMeDept.class.getName());
            }

            @Override
            public Serializable getIdentifier() {
                return null;
            }

            @Override
            public void setIdentifier(Serializable id) {

            }

            @Override
            public String getEntityName() {
                return null;
            }

            @Override
            public Class getPersistentClass() {
                return null;
            }

            @Override
            public boolean isUninitialized() {
                return true;
            }

            @Override
            public Object getImplementation() {
                return null;
            }

            @Override
            public Object getImplementation(SharedSessionContractImplementor session) throws HibernateException {
                return null;
            }

            @Override
            public void setImplementation(Object target) {

            }

            @Override
            public boolean isReadOnlySettingAvailable() {
                return false;
            }

            @Override
            public boolean isReadOnly() {
                return false;
            }

            @Override
            public void setReadOnly(boolean readOnly) {

            }

            @Override
            public SharedSessionContractImplementor getSession() {
                return null;
            }

            @Override
            public void setSession(SharedSessionContractImplementor session) throws HibernateException {

            }

            @Override
            public void unsetSession() {

            }

            @Override
            public void setUnwrap(boolean unwrap) {

            }

            @Override
            public boolean isUnwrap() {
                return false;
            }
        };

    }
}
