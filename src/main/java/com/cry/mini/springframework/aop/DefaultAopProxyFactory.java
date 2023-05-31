package com.cry.mini.springframework.aop;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/22 17:25
 */
public class DefaultAopProxyFactory implements AopProxyFactory {
    @Override
    public AopProxy createAopProxy(Object target,PointcutAdvisor advisor) {
        return new JdkDynamicAopProxy(target,advisor);
    }
}
