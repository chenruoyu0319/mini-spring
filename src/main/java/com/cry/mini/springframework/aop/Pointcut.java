package com.cry.mini.springframework.aop;

/**
 * <p>
 * 功能描述: 切点，也就是返回一条匹配规则。
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/28 12:30
 */
public interface Pointcut {
    MethodMatcher getMethodMatcher();
}
