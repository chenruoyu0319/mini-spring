package com.cry.mini.springframework.web.content;

import com.cry.mini.springframework.web.content.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/11 13:01
 */
public class ContextLoaderListener implements ServletContextListener {

    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
    // parentApplicationContext
    private WebApplicationContext context;

    public ContextLoaderListener() {
    }

    public ContextLoaderListener(WebApplicationContext context) {
        this.context = context;
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

    // 在这段代码中，通过配置文件参数从 web.xml 中得到配置文件路径，如 applicationContext.xml，
    // 然后用这个配置文件创建了 AnnotationConfigWebApplicationContext 这一对象，我们叫 WAC，这就成了新的上下文。
    // 然后调用 servletContext.setAttribute() 方法，按照默认的属性值将 WAC 设置到 servletContext 里。这样，
    // AnnotationConfigWebApplicationContext 和  servletContext 就能够互相引用了，很方便。
    @Override
    public void contextInitialized(ServletContextEvent event) {
        initWebApplicationContext(event.getServletContext());
    }

    // 初始化的过程中初始化 WebApplicationContext， 并把这个上下文放到  servletContext 的  Attribute 某个属性里面。
    private void initWebApplicationContext(ServletContext servletContext) {
        // applicationContext.xml
        String sContextLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        // 初始化ioc容器: parentApplicationContext
        WebApplicationContext wac = new XmlWebApplicationContext(sContextLocation);
        wac.setServletContext(servletContext);
        this.context = wac;
        // parentApplicationContext
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
    }


}
