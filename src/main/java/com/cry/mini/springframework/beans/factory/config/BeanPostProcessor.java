package com.cry.mini.springframework.beans.factory.config;

import com.cry.mini.springframework.beans.factory.BeanFactory;
import com.cry.mini.springframework.exception.BeansException;

/**
 * <p>
 * 功能描述: Bean处理器Processor,由处理器来解释注解,内部的两个方法分别用于 Bean 初始化之前和之后
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/06 16:17
 */
public interface BeanPostProcessor {
    
    /**
     * Bean 初始化之前
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;
    
    /** 
     * Bean 初始化之后
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

    //在PostProcessor中注册BeanFactory
    void setBeanFactory(BeanFactory beanFactory);
}
