package com.cry.mini.springframework.context;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 功能描述: 事件发布监听机制
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/08 22:54
 */

public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {
    List<ApplicationListener> listeners = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        for (ApplicationListener listener : listeners) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.listeners.add(listener);
    }
}
