package com.cry.mini.springframework.aop;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/28 1:32
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor, BeforeAdvice {
    private final MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        this.advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());
        // todo: 这里可以控制beforeAdvice 能在某种情况下阻止目标方法的调用
        return mi.proceed();
    }
}
