package com.cry.mini.springframework.web.content.support;

import com.cry.mini.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.cry.mini.springframework.beans.factory.config.BeanPostProcessor;
import com.cry.mini.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.cry.mini.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.cry.mini.springframework.beans.factory.xml.XmlWebBeanDefinitionReader;
import com.cry.mini.springframework.context.AbstractApplicationContext;
import com.cry.mini.springframework.context.ApplicationEvent;
import com.cry.mini.springframework.context.ApplicationEventPublisher;
import com.cry.mini.springframework.context.ApplicationListener;
import com.cry.mini.springframework.context.SimpleApplicationEventPublisher;
import com.cry.mini.springframework.exception.BeansException;
import com.cry.mini.springframework.web.content.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 适用于 Web 场景的上下文。
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/11 13:26
 */
public class AnnotationConfigWebApplicationContext extends AbstractApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;
    // 相较于上个版本的新迭代内容
    private WebApplicationContext parentApplicationContext;
    // 同ClassPathXmlApplicationContext
    DefaultListableBeanFactory beanFactory;
    // 暂时没用到
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<BeanFactoryPostProcessor>();

    // 暂时没用到
    public AnnotationConfigWebApplicationContext(String fileName) {
        this(fileName, null);
    }

    public AnnotationConfigWebApplicationContext(String fileName, WebApplicationContext parentApplicationContext) {
        this.parentApplicationContext = parentApplicationContext;
        // 在Listener注入的servlet上下文
        this.servletContext = this.parentApplicationContext.getServletContext();
        URL xmlPath = null;
        try {
            // minisMVC-servlet.xml
            xmlPath = this.getServletContext().getResource(fileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        List<String> packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        List<String> controllerNames = scanPackages(packageNames);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // web容器的beanFactory
        this.beanFactory = beanFactory;
        // ioc容器的beanFactory
        this.beanFactory.setParent(this.parentApplicationContext.getBeanFactory());
        // 注册controller层对象到web容器
        XmlWebBeanDefinitionReader reader = new XmlWebBeanDefinitionReader(this.beanFactory);
        // web-解析好的xml转化为BeanDefinition
        reader.loadBeanDefinitions(controllerNames);
        if (true) {
            try {
                refresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> scanPackages(List<String> packages) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packages) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URI uri = null;
        //将以.分隔的包名换成以/分隔的uri
        try {
            // file方法路径不支持.号所有需要路径转换
            uri = this.getClass().getResource("/" +
                    packageName.replaceAll("\\.", "/")).toURI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File dir = new File(uri);
        //处理对应的文件目录
        for (File file : dir.listFiles()) { //目录下的文件或者子目录
            if (file.isDirectory()) { //对子目录递归扫描
                scanPackage(packageName + "." + file.getName());
            } else { //类文件
                // packageName = com.cry.mini.springframework.examples.mvc
                String controllerName = packageName + "."
                        + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        // com.cry.mini.springframework.examples.mvc.HelloWorldBean
        return tempControllerNames;
    }

    public void setParent(WebApplicationContext parentApplicationContext) {
        this.parentApplicationContext = parentApplicationContext;
        this.beanFactory.setParent(this.parentApplicationContext.getBeanFactory());
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {
    }

    @Override
    public void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
        try {
            BeanPostProcessor autoProxyCreator = (BeanPostProcessor) this.beanFactory.getBean("autoProxyCreator");
            this.beanFactory.addBeanPostProcessor(autoProxyCreator);
            this.beanFactory.addBeanPostProcessor((BeanPostProcessor) this.beanFactory.getBean("autowiredAnnotationBeanPostProcessor"));
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public void finishRefresh() {
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName,obj);
    }
}
