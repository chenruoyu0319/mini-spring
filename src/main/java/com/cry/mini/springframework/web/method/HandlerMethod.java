package com.cry.mini.springframework.web.method;

import java.lang.reflect.Method;

/**
 * <p>
 * 功能描述: controller对象及其中调用的方法参数
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/11 17:42
 */
public class HandlerMethod {

    private Object bean;
    private Class<?> beanType;
    private Method method;
    private Class<?> returnType;
    private String description;
    private String className;
    private String methodName;

    public HandlerMethod(Method method, Object obj) {
        this.setMethod(method);
        this.setBean(obj);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

}
