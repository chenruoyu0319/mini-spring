package com.cry.mini.springframework.aop.dynamic;

import java.io.Serializable;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/22 16:23
 */
public class Action1 implements IAction , Serializable {
    @Override
    public void doAction() {
        System.out.println("really do action1");
    }

    @Override
    public void doAction7() {
        System.out.println("really do action7");
    }
}
