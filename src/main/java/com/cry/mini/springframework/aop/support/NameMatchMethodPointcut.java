package com.cry.mini.springframework.aop.support;

import com.cry.mini.springframework.aop.MethodMatcher;
import com.cry.mini.springframework.aop.Pointcut;
import com.cry.mini.springframework.util.PatternMatchUtils;

import java.lang.reflect.Method;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/28 12:31
 */
public class NameMatchMethodPointcut implements MethodMatcher, Pointcut {
    private String mappedName = "";

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }

    @Override
    public boolean matches(Method method, Class<?> targetCLass) {
        if (mappedName.equals(method.getName()) || isMatch(method.getName(), mappedName)) {
            return true;
        }
        return false;
    }
    //核心方法，判断方法名是否匹配给定的模式
    protected boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
