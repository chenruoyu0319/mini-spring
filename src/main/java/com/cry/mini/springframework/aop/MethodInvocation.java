package com.cry.mini.springframework.aop;

import java.lang.reflect.Method;

/**
 * <p>
 * 功能描述: 以前通过反射方法调用业务逻辑的那一段代码的包装。
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/24 0:22
 */
public interface MethodInvocation {
    Method getMethod();
    Object[] getArguments();
    Object getThis();
    Object proceed() throws Throwable;
}
