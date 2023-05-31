package com.cry.mini.springframework.aop.proxy;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/22 16:08
 */
public class ProxySubject implements Subject {
    // 代理类内部包含了一个真正的服务类
    Subject realsubject;
    public ProxySubject() {
        this.realsubject = new RealSubject();
    }
    // 代理类给外部程序提供了和真正的服务类同样的接口
    @Override
    public String doAction(String name) {
        System.out.println("proxy control");
        String rtnValue = realsubject.doAction(name);
        return "SUCCESS";
    }
}
