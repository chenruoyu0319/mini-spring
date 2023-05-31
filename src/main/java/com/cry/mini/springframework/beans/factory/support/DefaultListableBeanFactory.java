package com.cry.mini.springframework.beans.factory.support;

import com.cry.mini.springframework.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.cry.mini.springframework.beans.factory.config.BeanDefinition;
import com.cry.mini.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.cry.mini.springframework.exception.BeansException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 功能描述: IoC 引擎
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/08 16:41
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {

    // ioc容器的beanFactory
    ConfigurableListableBeanFactory parentBeanFactory;

    public void setParent(ConfigurableListableBeanFactory beanFactory) {
        this.parentBeanFactory = beanFactory;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object result = super.getBean(beanName);
        if (result == null) {
            result = this.parentBeanFactory.getBean(beanName);
        }
        return result;
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionNames.toArray(new String[beanDefinitionNames.size()]);
    }

    // 还未完善
    @Override
    public String[] getBeanNamesForType(Class type) {
        List result = new ArrayList<>();
        for (String beanName : this.beanDefinitionNames) {
            boolean matchFound = false;
            BeanDefinition mbd = this.getBeanDefinition(beanName);
            Class classToMatch = mbd.getClass();
            // A.isAssignableFrom(B)  判断A是否是B的父类
            if (type.isAssignableFrom(classToMatch)) {
                matchFound = true;
            } else {
                matchFound = false;
            }
            if (matchFound) {
                result.add(beanName);
            }
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    @Override
    public Map getBeansOfType(Class type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);
        Map result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object beanInstance = getBean(beanName);
            result.put(beanName, beanInstance);
        }
        return result;
    }


    // 暂未实现
    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {

    }

    // 暂未实现
    @Override
    public String[] getDependentBeans(String beanName) {
        return new String[0];
    }

    // 暂未实现
    @Override
    public String[] getDependenciesForBean(String beanName) {
        return new String[0];
    }



}
