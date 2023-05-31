package com.cry.mini.springframework.context;

import com.cry.mini.springframework.beans.factory.ListableBeanFactory;
import com.cry.mini.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.cry.mini.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.cry.mini.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.cry.mini.springframework.core.env.Environment;
import com.cry.mini.springframework.core.env.EnvironmentCapable;
import com.cry.mini.springframework.exception.BeansException;

/**
 * <p>
 * 功能描述: 公共接口，所有的上下文都实现自ApplicationContext，支持上下文环境和事件发布。
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/08 22:56
 */

public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory,
        ApplicationEventPublisher {

    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    void setEnvironment(Environment environment);

    @Override
    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh() throws BeansException, IllegalStateException;

    void close();

    boolean isActive();
}
