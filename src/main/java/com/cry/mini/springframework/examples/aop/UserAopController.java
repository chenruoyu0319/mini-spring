package com.cry.mini.springframework.examples.aop;

import com.cry.mini.springframework.aop.dynamic.IAction;
import com.cry.mini.springframework.beans.factory.annotation.Autowired;
import com.cry.mini.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/19 17:41
 */
public class UserAopController {

    @Autowired
    IAction action;

    @Autowired
    IAction action2;

    @RequestMapping("/testaop")
    public void doTestAop() {
        action.doAction();
        String str = "test aop1, hello world!";
    }

    @RequestMapping("/testaop2")
    public void doTestAop2() {
        action2.doAction();
        String str = "test aop2, hello world!";
    }

    @RequestMapping("/testaop7")
    public void doTestAop7() {
        action.doAction7();
        String str = "test aop7, hello world!";
    }

}
