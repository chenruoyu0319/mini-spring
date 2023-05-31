package com.cry.mini.springframework.beans.factory.config;

import com.cry.mini.springframework.beans.factory.ListableBeanFactory;

/**
 * <p>
 * 功能描述: 用一个 ConfigurableListableBeanFactory 接口把
 * AutowireCapableBeanFactory、ListableBeanFactory 和 ConfigurableBeanFactory 合并在一起。
 *
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/08 16:23
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

}
