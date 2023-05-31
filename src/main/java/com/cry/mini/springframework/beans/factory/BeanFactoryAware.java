package com.cry.mini.springframework.beans.factory;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/28 9:52
 */
public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory);
}
