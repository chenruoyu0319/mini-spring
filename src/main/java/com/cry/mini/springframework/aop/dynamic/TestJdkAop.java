package com.cry.mini.springframework.aop.dynamic;

import java.util.Arrays;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/22 16:32
 */
public class TestJdkAop {

    public static void main(String[] args) {
        TestJdkAop testJdkAop = new TestJdkAop();
        testJdkAop.doTestAop();
    }
    public void doTestAop() {
        // 真实对象
        Action1 action = new Action1();
        System.out.println(Arrays.toString(action.getClass().getInterfaces()));
        DynamicProxy proxy = new DynamicProxy(action);
        // p: 代理对象
        IAction p = (IAction) proxy.getProxy();
        System.out.println("p: " + p.getClass());
        /** 代理过程侵入了代码, 不美观 */
        p.doAction();
        String str = "test aop, hello world!";
        System.out.println(str);
    }
}
