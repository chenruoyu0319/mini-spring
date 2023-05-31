package com.cry.mini.springframework.aop.proxy;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/22 16:10
 */
public class Client {
    public static void main(String[] args) {
        Subject subject = new ProxySubject();
        subject.doAction("Test");
    }
}
