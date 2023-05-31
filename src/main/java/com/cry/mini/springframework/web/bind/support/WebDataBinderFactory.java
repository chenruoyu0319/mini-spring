package com.cry.mini.springframework.web.bind.support;

import com.cry.mini.springframework.exception.BeansException;
import com.cry.mini.springframework.web.bind.WebDataBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/15 18:39
 */
public class WebDataBinderFactory {

    public WebDataBinder createBinder(HttpServletRequest request, Object target, String objectName) {
        WebDataBinder wbd= new WebDataBinder(target,objectName);
        initBinder(wbd, request);
        return wbd;
    }

    protected void initBinder(WebDataBinder dataBinder, HttpServletRequest request) {
    }
}