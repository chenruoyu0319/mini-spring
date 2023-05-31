package com.cry.mini.springframework.examples.jdbc;

import com.cry.mini.springframework.util.Callback;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/19 10:00
 */
public class TaskTest {

    public static void main(String[] args) {
        Task task = new Task();
        Callback callback = () -> System.out.println("callback...");
        task.executeWithCallback(callback);
    }
}
