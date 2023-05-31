package com.cry.mini.springframework.beans.factory;

import com.cry.mini.springframework.exception.BeansException;

import java.util.Map;

/**
 * <p>
 * 功能描述: 获取 Bean 的数量，得到所有 Bean 的名字，按照某个类型获取 Bean 列表等等
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/08 16:12
 */
public interface ListableBeanFactory {

    //是否含有指定bean
    boolean containsBeanDefinition(String beanName);

    //获取 Bean 的数量
    int getBeanDefinitionCount();

    //得到所有 Bean 的名字
    String[] getBeanDefinitionNames();

    //按照某个类型获取 Bean 列表
    String[] getBeanNamesForType(Class type);

    //按照某个类型获取 Bean 哈希表
    Map getBeansOfType(Class type) throws BeansException;
}
