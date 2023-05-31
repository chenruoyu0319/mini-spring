package com.cry.mini.springframework.web.servlet.view;

import com.cry.mini.springframework.web.servlet.View;
import com.cry.mini.springframework.web.servlet.ViewResolver;

/**
 * <p>
 * 功能描述: Jsp-View工厂
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/17 15:34
 */
public class InternalResourceViewResolver implements ViewResolver {
    private Class<?> viewClass = null;
    // JstlView
    private String viewClassName = "";
    // /jsp/
    private String prefix = "";
    // .jsp
    private String suffix = "";
    private String contentType;

    public InternalResourceViewResolver() {
        if (getViewClass() == null) {
            setViewClass(JstlView.class);
        }
    }

    public void setViewClassName(String viewClassName) {
        this.viewClassName = viewClassName;
        Class<?> clz = null;
        try {
            clz = Class.forName(viewClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setViewClass(clz);
    }

    protected String getViewClassName() {
        return this.viewClassName;
    }

    public void setViewClass(Class<?> viewClass) {
        this.viewClass = viewClass;
    }

    protected Class<?> getViewClass() {
        return this.viewClass;
    }

    public void setPrefix(String prefix) {
        this.prefix = (prefix != null ? prefix : "");
    }

    protected String getPrefix() {
        return this.prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = (suffix != null ? suffix : "");
    }

    protected String getSuffix() {
        return this.suffix;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    protected String getContentType() {
        return this.contentType;
    }

    @Override
    public View resolveViewName(String viewName) throws Exception {
        return buildView(viewName);
    }

    protected View buildView(String viewName) throws Exception {
        Class<?> viewClass = getViewClass();

        View view = (View) viewClass.newInstance();
        view.setUrl(getPrefix() + viewName + getSuffix());

        String contentType = getContentType();
        view.setContentType(contentType);

        return view;
    }

}
