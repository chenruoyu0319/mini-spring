package com.cry.mini.springframework.beans.factory;

import com.cry.mini.springframework.exception.BeansException;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/04/17 17:40
 */
public interface BeanFactory {

    /**
     * 这是对外的一个方法，让外部程序从容器中获取Bean实例，会逐步演化成核心方法
     *
     * @param beanName beanId
     * @return bean对象
     * @throws BeansException bean异常
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 校验bean
     *
     * @param beanName beanName
     * @return 是否含有bean
     */
    Boolean containsBean(String beanName);

    /**
     * 是否单例
     *
     * @param beanName beanName
     * @return 是否单例
     */
    boolean isSingleton(String beanName);

    /**
     * 是否多例
     *
     * @param beanName beanName
     * @return 是否多例
     */
    boolean isPrototype(String beanName);

    /**
     * 获取bean的type
     *
     * @param beanName beanName
     * @return 是否多例
     */
    Class<?> getType(String beanName);

    void registerBean(String beanName, Object obj);
}
