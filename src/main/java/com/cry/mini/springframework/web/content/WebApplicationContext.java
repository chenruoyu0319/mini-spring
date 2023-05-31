package com.cry.mini.springframework.web.content;

import com.cry.mini.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/11 13:08
 */
public interface WebApplicationContext extends ApplicationContext {

    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    ServletContext getServletContext();

    void setServletContext(ServletContext servletContext);

}
