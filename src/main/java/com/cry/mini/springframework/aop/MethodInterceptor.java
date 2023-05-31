package com.cry.mini.springframework.aop;

/**
 * <p>
 * 功能描述: 调用方法上的拦截器，也就是它实现在某个方法上的增强。
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/24 0:21
 */
public interface MethodInterceptor extends Interceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
