package com.cry.mini.springframework.context;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/04/19 15:53
 */

public interface ApplicationEventPublisher {
    
    /** 
     * 发布事件的方法
     * @param event spring事件
     */
    void publishEvent(ApplicationEvent event);

    void addApplicationListener(ApplicationListener listener);
}
