package com.cry.mini.springframework.aop;

/**
 * <p>
 * 功能描述: 扩展了 Advisor，内部可以返回 Pointcut
 * 也就是说这个 Advisor 有一个特性：能支持切点 Pointcut 了
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/28 12:30
 */
public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
