package com.cry.mini.springframework.beans.factory.config;

/**
 * @author rychen
 */
public interface SingletonBeanRegistry {

    /** 
     * 单例的注册
     * @param beanName 
     * @param singletonObject
     * @return 
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 单例的获取
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);
    
    /** 
     * 判断单例是否存在
     * @param beanName       
     * @return 
     */
    boolean containsSingleton(String beanName);

    /**
     * 获取所有单例beanName
     * @return
     */
    String[] getSingletonNames();

}
