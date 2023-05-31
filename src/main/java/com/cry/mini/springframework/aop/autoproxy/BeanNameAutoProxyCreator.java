package com.cry.mini.springframework.aop.autoproxy;

import com.cry.mini.springframework.aop.AopProxyFactory;
import com.cry.mini.springframework.aop.DefaultAopProxyFactory;
import com.cry.mini.springframework.aop.PointcutAdvisor;
import com.cry.mini.springframework.aop.ProxyFactoryBean;
import com.cry.mini.springframework.beans.factory.BeanFactory;
import com.cry.mini.springframework.beans.factory.config.BeanPostProcessor;
import com.cry.mini.springframework.exception.BeansException;
import com.cry.mini.springframework.util.PatternMatchUtils;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/29 9:57
 */
public class BeanNameAutoProxyCreator implements BeanPostProcessor {
    String pattern;
    //代理对象名称模式，如action*
    private BeanFactory beanFactory;
    private AopProxyFactory aopProxyFactory;
    private String interceptorName;
    private PointcutAdvisor advisor;

    public BeanNameAutoProxyCreator() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    } //核心方法。在bean实例化之后，init-method调用之前执行这个步骤。

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (isMatch(beanName, this.pattern)) {
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();//创建一个ProxyFactoryBean
            proxyFactoryBean.setTarget(bean);
            proxyFactoryBean.setBeanFactory(beanFactory);
            proxyFactoryBean.setAopProxyFactory(aopProxyFactory);
            proxyFactoryBean.setInterceptorName(interceptorName);
            beanFactory.registerBean(beanName,proxyFactoryBean);
            return proxyFactoryBean;
        } else {
            return bean;
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected boolean isMatch(String beanName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, beanName);
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public AopProxyFactory getAopProxyFactory() {
        return aopProxyFactory;
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    public String getInterceptorName() {
        return interceptorName;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    public PointcutAdvisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(PointcutAdvisor advisor) {
        this.advisor = advisor;
    }
}