package com.cry.mini.springframework.web.method.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 功能描述: URL注册类
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/11 17:48
 */
public class MappingRegistry {

    // 是保存自定义的@RequestMapping名称(URL的名称)的列表
    private List<String> urlMappingNames = new ArrayList<>();
    // URL名称与controller对象
    private Map<String, Object> mappingObjs = new HashMap<>();
    // URL名称与controller方法
    private Map<String, Method> mappingMethods = new HashMap<>();

    public List<String> getUrlMappingNames() {
        return urlMappingNames;
    }

    public void setUrlMappingNames(List<String> urlMappingNames) {
        this.urlMappingNames = urlMappingNames;
    }

    public Map<String, Object> getMappingObjs() {
        return mappingObjs;
    }

    public void setMappingObjs(Map<String, Object> mappingObjs) {
        this.mappingObjs = mappingObjs;
    }

    public Map<String, Method>  getMappingMethods() {
        return mappingMethods;
    }

    public void setMappingMethods(Map<String, Method>  mappingMethods) {
        this.mappingMethods = mappingMethods;
    }
}