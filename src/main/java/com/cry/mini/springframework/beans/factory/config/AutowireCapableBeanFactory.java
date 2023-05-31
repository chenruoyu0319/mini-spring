package com.cry.mini.springframework.beans.factory.config;

import com.cry.mini.springframework.beans.factory.BeanFactory;
import com.cry.mini.springframework.exception.BeansException;

/**
 * <p>
 * 功能描述: 专为 Autowired 注入的 Bean 准备的BeanFactory
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/06 17:29
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;


}
