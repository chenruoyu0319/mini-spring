package com.cry.mini.springframework.web.method.annotation;

import com.cry.mini.springframework.web.bind.annotation.RequestMapping;
import com.cry.mini.springframework.web.content.WebApplicationContext;
import com.cry.mini.springframework.web.method.HandlerMethod;
import com.cry.mini.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * <p>
 * 功能描述:  URL 映射到某个实例方法的过程
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/11 17:40
 */
public class RequestMappingHandlerMapping implements HandlerMapping {
    WebApplicationContext wac;
    // 存储RequestMapping的value,也就是url
    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext wac) {
        this.wac = wac;
        initMapping();
    }

    // 然后完善 initMapping() ，功能是初始化  URL 映射，找到使用了注解 @RequestMapping 的方法，
    // URL 存放到  urlMappingNames 里，
    // 映射的对象存放到  mappingObjs 里，
    // 映射的方法存放到  mappingMethods 里。
    // 建立URL与调用方法和实例的映射关系，存储在mappingRegistry中
    // 用这个方法取代了过去在DispatcherServlet中的initMapping方法
    protected void initMapping() {
        Class clz = null;
        Object obj = null;
        String[] controllerNames = this.wac.getBeanDefinitionNames();
        for (String controllerName:controllerNames) {
            System.out.println("controllerName: " + controllerName);
        }
        //扫描WAC中存放的所有bean
        for (String controllerName : controllerNames) {
            try {
                clz = Class.forName(controllerName);
                obj = this.wac.getBean(controllerName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Method[] methods = clz.getDeclaredMethods();
            if (methods != null) {
                for (Method method : methods) {
                    //检查所有的方法
                    boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if (isRequestMapping) { //有RequestMapping注解
                        String methodName = method.getName();
                        //建立方法名和URL的映射: /test
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();
                        this.mappingRegistry.getUrlMappingNames().add(urlMapping);
                        this.mappingRegistry.getMappingObjs().put(urlMapping, obj);
                        this.mappingRegistry.getMappingMethods().put(urlMapping, method);
                    }
                }
            }
        }
    }

    //根据访问URL查找对应的调用方法
    //迁移了之前DispatcherServlet中的doGet方法根据访问URL查找对应的调用方法的部分
    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        String sPath = request.getServletPath();
        if (!this.mappingRegistry.getUrlMappingNames().contains(sPath)) {
            return null;
        }
        Method method = this.mappingRegistry.getMappingMethods().get(sPath);
        Object obj = this.mappingRegistry.getMappingObjs().get(sPath);
        HandlerMethod handlerMethod = new HandlerMethod(method, obj);
        return handlerMethod;
    }

}