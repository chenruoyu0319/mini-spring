package com.cry.mini.springframework.aop;

/**
 * <p>
 * 功能描述: 对方法拦截器的包装
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/27 22:30
 */
public interface Advisor {
    MethodInterceptor getMethodInterceptor();
    void setMethodInterceptor(MethodInterceptor methodInterceptor);
    Advice getAdvice();
}
