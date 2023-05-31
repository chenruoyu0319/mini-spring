package com.cry.mini.springframework.beans.factory.config;

import com.cry.mini.springframework.beans.factory.BeanFactory;
import com.cry.mini.springframework.exception.BeansException;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/08 23:02
 */
public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
