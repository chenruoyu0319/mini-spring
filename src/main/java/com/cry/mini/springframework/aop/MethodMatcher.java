package com.cry.mini.springframework.aop;

import java.lang.reflect.Method;

/**
 * <p>
 * 功能描述: 方法的匹配算法
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/28 12:29
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetCLass);
}
