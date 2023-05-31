package com.cry.mini.springframework.web.content.support;

import com.cry.mini.springframework.context.ClassPathXmlApplicationContext;
import com.cry.mini.springframework.web.content.WebApplicationContext;
import com.cry.mini.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;

/**
 * <p>
 * 功能描述: 启动ioc容器, 由上个迭代的AnnotationConfigWebApplicationContext转化而来
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/11 16:09
 */
public class XmlWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;

    public XmlWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}
