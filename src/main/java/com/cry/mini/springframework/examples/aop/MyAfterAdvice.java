package com.cry.mini.springframework.examples.aop;

import com.cry.mini.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/28 10:53
 */
public class MyAfterAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("----------my interceptor after method call----------");
    }
}
