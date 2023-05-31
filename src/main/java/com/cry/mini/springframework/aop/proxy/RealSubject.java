package com.cry.mini.springframework.aop.proxy;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/22 16:07
 */
public class RealSubject implements Subject {
    @Override
    public String doAction(String name) {
        System.out.println("real subject do action "+name);
        return "SUCCESS";
    }
}
