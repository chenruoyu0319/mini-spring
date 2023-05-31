package com.cry.mini.springframework.examples.jdbc;

import com.cry.mini.springframework.util.Callback;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/19 9:51
 */
public class Task {
    public void executeWithCallback(Callback callback) {
        //execute(); //具体的业务逻辑处理
        if (callback != null) {
            callback.call();
        }
    }
}

