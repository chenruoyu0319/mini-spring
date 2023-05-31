package com.cry.mini.springframework.examples.ioc;

import com.cry.mini.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/04/20 10:34
 */

public class BaseService {
    @Autowired
    private BaseBaseService bbs;
    private String name;

    public BaseBaseService getBbs() {
        return bbs;
    }

    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BaseService{" +
                "name='" + name + '\'' +
                "bbs='" + bbs + '\'' +
                '}';
    }
}
