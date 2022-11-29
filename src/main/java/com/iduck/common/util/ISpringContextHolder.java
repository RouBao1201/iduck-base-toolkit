package com.iduck.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * spring上下文工具
 *
 * @author songYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/10/28
 */
@Component
public class ISpringContextHolder implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(ISpringContextHolder.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ISpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 获取spring上下文对象
     *
     * @return spring上下文对象
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 根据bean名称和bean类型获取bean
     *
     * @param name  bean名称
     * @param clazz bean类型
     * @param <T>   bean类型
     * @return bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        if (!getApplicationContext().containsBean(name)) {
            return null;
        }
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 根据bean名称获取bean
     *
     * @param name           bean名称
     * @param ignoreNotFound 是否忽略容器中是否含有bean
     * @return bean
     */
    public static Object getBean(String name, boolean ignoreNotFound) {
        if (!getApplicationContext().containsBean(name) && ignoreNotFound) {
            return null;
        }
        return getBean(name);
    }

    /**
     * 根据bean名称获取bean
     *
     * @param name bean名称
     * @return bean
     */
    public static Object getBean(String name) {
        try {
            return getApplicationContext().getBean(name);
        } catch (NoSuchBeanDefinitionException e) {
            log.error("SpringContextHolder => The bean [beanName:{}] is not found. ErrorMessage:{}", name, e.getMessage());
            throw e;
        }
    }

    /**
     * 根据bean类型获取bean
     *
     * @param beanClass bean类型
     * @param <T>       bean类型
     * @return bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        return getBean(beanClass, false);
    }

    /**
     * 根据bean类型获取所有bean
     *
     * @param beanClass bean类型
     * @param <T>       枚举
     * @return bean
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> beanClass) {
        return getApplicationContext().getBeansOfType(beanClass);
    }

    /**
     * 根据bean类型获取bean
     *
     * @param beanClass      bean类型
     * @param ignoreNotFound 是否忽略容器中是否含有bean
     * @param <T>            bean类型
     * @return bean
     */
    public static <T> T getBean(Class<T> beanClass, boolean ignoreNotFound) {
        try {
            return getApplicationContext().getBean(beanClass);
        } catch (NoSuchBeanDefinitionException e) {
            if (ignoreNotFound) {
                log.error("SpringContextHolder => The bean [beanClass:{}] is not found [ignoreNotFound:true]. ErrorMessage:{}", beanClass, e.getMessage());
            } else {
                throw e;
            }
            return null;
        }
    }

    /**
     * 校验spring上下对象是否为空
     */
    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("SpringContextHolder => ApplicationContext is not injected.");
        }
    }
}
