package com.cry.mini.springframework.examples.ioc;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/04/20 14:39
 */

public class BaseConService {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BaseConService{" +
                "name='" + name + '\'' +
                '}';
    }
}
