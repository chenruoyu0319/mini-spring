package com.cry.mini.springframework.beans.factory.support;

import com.cry.mini.springframework.beans.factory.FactoryBean;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/22 17:10
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    protected Class<?> getTypeForFactoryBean(final FactoryBean<?> factoryBean) {
        return factoryBean.getObjectType();
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        Object object = doGetObjectFromFactoryBean(factory, beanName);
//        try {
//            object = postProcessObjectFromFactoryBean(object, beanName);
//        } catch (BeansException e) {
//            e.printStackTrace();
//        }
        return object;
    }

    //从factory bean中获取创建的代理对象
    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, final String beanName) {
        Object object = null;
        try {
            object = factory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

}
