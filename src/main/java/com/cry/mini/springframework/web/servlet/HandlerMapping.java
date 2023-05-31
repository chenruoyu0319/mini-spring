package com.cry.mini.springframework.web.servlet;

import com.cry.mini.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 功能描述: URL 映射到某个实例方法的过程
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/11 17:42
 */
public interface HandlerMapping {

    HandlerMethod getHandler(HttpServletRequest request) throws Exception;
}
