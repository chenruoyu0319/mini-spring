package com.cry.mini.springframework.beans.factory.config;

import com.cry.mini.springframework.beans.factory.BeanFactory;

/**
 * <p>
 * 功能描述: 将维护 Bean 之间的依赖关系以及支持 Bean 处理器也看作一个独立的特性
 * 其实就是个单例的BeanFactory
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/08 16:18
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    int getBeanPostProcessorCount();

    void registerDependentBean(String beanName, String dependentBeanName);

    String[] getDependentBeans(String beanName);

    String[] getDependenciesForBean(String beanName);
}