package com.cry.mini.springframework.beans.factory.support;


import com.cry.mini.springframework.beans.factory.config.BeanDefinition;


/**
 * <p>
 * 功能描述: 存放BeanDefinition的仓库
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/04/19 16:15
 */
public interface BeanDefinitionRegistry {

	/**
	 * 存放BeanDefinition 对象
	 * @param beanName beanName
	 * @param bd BeanDefinition
	 */
	void registerBeanDefinition(String beanName, BeanDefinition bd);

	/**
	 * 移除BeanDefinition 对象
	 * @param beanName beanName
	 */
	void removeBeanDefinition(String beanName);

	/**
	 * 获取BeanDefinition 对象
	 * @param beanName beanName
	 * @return BeanDefinition
	 */
	BeanDefinition getBeanDefinition(String beanName);

	/**
	 * 判断 BeanDefinition 对象
	 * @param beanName beanName
	 * @return  是否已有BeanDefinition
	 */
	boolean containsBeanDefinition(String beanName);
}
