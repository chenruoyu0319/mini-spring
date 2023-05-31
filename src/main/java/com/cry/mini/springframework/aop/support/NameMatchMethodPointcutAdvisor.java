package com.cry.mini.springframework.aop.support;

import com.cry.mini.springframework.aop.Advice;
import com.cry.mini.springframework.aop.AfterAdvice;
import com.cry.mini.springframework.aop.AfterReturningAdvice;
import com.cry.mini.springframework.aop.AfterReturningAdviceInterceptor;
import com.cry.mini.springframework.aop.BeforeAdvice;
import com.cry.mini.springframework.aop.MethodBeforeAdvice;
import com.cry.mini.springframework.aop.MethodBeforeAdviceInterceptor;
import com.cry.mini.springframework.aop.MethodInterceptor;
import com.cry.mini.springframework.aop.Pointcut;
import com.cry.mini.springframework.aop.PointcutAdvisor;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/28 16:18
 */
public class NameMatchMethodPointcutAdvisor implements PointcutAdvisor {
    private Advice advice = null;
    private MethodInterceptor methodInterceptor;
    private String mappedName;
    private final NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();

    public NameMatchMethodPointcutAdvisor() {
    }

    public NameMatchMethodPointcutAdvisor(Advice advice) {
        this.advice = advice;
    }

    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return this.methodInterceptor;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
        MethodInterceptor mi = null;
        if (advice instanceof BeforeAdvice) {
            mi = new MethodBeforeAdviceInterceptor((MethodBeforeAdvice) advice);
        } else if (advice instanceof AfterAdvice) {
            mi = new AfterReturningAdviceInterceptor((AfterReturningAdvice) advice);
        } else if (advice instanceof MethodInterceptor) {
            mi = (MethodInterceptor) advice;
        }
        setMethodInterceptor(mi);
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
        this.pointcut.setMappedName(this.mappedName);
    }
}
