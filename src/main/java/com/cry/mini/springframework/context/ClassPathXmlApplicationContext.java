package com.cry.mini.springframework.context;

import com.cry.mini.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.cry.mini.springframework.beans.factory.config.BeanPostProcessor;
import com.cry.mini.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.cry.mini.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.cry.mini.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.cry.mini.springframework.core.ClassPathXmlResource;
import com.cry.mini.springframework.core.Resource;
import com.cry.mini.springframework.exception.BeansException;

import java.util.List;

/**
 * <p>
 * 功能描述: spring上下文启动的入口
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/04/18 17:42
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    DefaultListableBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    /**
     * context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
     *
     * @param fileName 配置文件路径
     */
    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        // read xml configuration
        Resource resource = new ClassPathXmlResource(fileName);
        // 生成BeanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 关联BeanFactory和BeanDefinition
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // parse configuration and create bean definition
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        // 提前加载所有的bean todo: 关闭懒加载的话，这行其实没用，在loadBeanDefinitions已实例化bean
        if (isRefresh) {
            refresh();
        }
    }

    @Override
    public void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        System.out.println("postProcessBeanFactory尚未实现");
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    public void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        try {
            BeanPostProcessor autoProxyCreator = (BeanPostProcessor) this.beanFactory.getBean("autoProxyCreator");
            this.beanFactory.addBeanPostProcessor(autoProxyCreator);
            this.beanFactory.addBeanPostProcessor((BeanPostProcessor) this.beanFactory.getBean("autowiredAnnotationBeanPostProcessor"));
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed..."));
    }

    /**
     * context再对外提供一个getBean，底下就是调用的BeanFactory对应的方法
     *
     * @param beanName beanName
     * @return bean
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public Boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName,obj);
    }

    @Override
    public List getBeanFactoryPostProcessors() {
        return this.beanFactory.getBeanPostProcessors();
    }


}

