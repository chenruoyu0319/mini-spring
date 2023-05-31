package com.cry.mini.springframework.beans.factory.xml;

import com.cry.mini.springframework.beans.factory.config.BeanDefinition;
import com.cry.mini.springframework.beans.factory.support.AbstractBeanFactory;

import java.util.List;

/**
 * <p>
 * 功能描述: web-解析好的xml转化为BeanDefinition
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/04/18 17:16
 */
public class XmlWebBeanDefinitionReader {

    AbstractBeanFactory beanFactory;

    public XmlWebBeanDefinitionReader(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(List<String> controllerNames) {
        // com.cry.mini.springframework.examples.mvc.HelloWorldBean
        for (String controller : controllerNames) {
            String beanID = controller;
            String beanClassName = controller;
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            this.beanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }
}
