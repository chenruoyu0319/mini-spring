package com.cry.mini.springframework.examples.ioc;

import com.cry.mini.springframework.beans.factory.config.BeanDefinition;
import com.cry.mini.springframework.context.ClassPathXmlApplicationContext;
import com.cry.mini.springframework.exception.BeansException;

import java.util.Arrays;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/04/11 0:57
 */
public class IOCTest {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        AService aService = (AService)ctx.getBean("aservice");
        aService.sayHello();
        System.out.println(aService);
        String[] beanNamesForType = ctx.getBeanNamesForType(BeanDefinition.class);
        System.out.println("======根据类型获取beanName======");
        Arrays.stream(beanNamesForType).forEach(System.out::println);
    }
}
