package com.cry.mini.springframework.aop;

/**
 * <p>
 * 功能描述: 对方法拦截器的包装
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/27 23:29
 */
public class DefaultAdvisor implements Advisor {
    private MethodInterceptor methodInterceptor;

    public DefaultAdvisor() {
    }

    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    @Override
    public Advice getAdvice() {
        return this.methodInterceptor;
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return this.methodInterceptor;
    }
}
